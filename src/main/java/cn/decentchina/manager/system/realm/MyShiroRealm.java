package cn.decentchina.manager.system.realm;

import cn.decentchina.manager.system.cache.RedisSessionDAO;
import cn.decentchina.manager.system.config.Constants;
import cn.decentchina.manager.system.config.CustomerConfig;
import cn.decentchina.manager.system.service.AdminService;
import cn.decentchina.manager.system.service.ShiroChainService;
import cn.decentchina.manager.system.vo.AdminVO;
import cn.decentchina.manager.system.vo.NavigationVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * description: 自定义域
 *
 * @author 唐全成
 * @date 2018/3/7
 */
@Slf4j
@Component
public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private AdminService adminService;
    @Resource
    private ShiroChainService shiroChainService;
    @Resource
    private RedisSessionDAO redisSessionDAO;
    @Resource
    private CustomerConfig customerConfig;

    /**
     * 用户第一次请求被shiro管理的请求时调用
     *
     * @param principals 用户信息
     * @return : org.apache.shiro.authz.AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roles = new HashSet<>(16);
        Set<String> permissions = new HashSet<>(16);
        try {
            //获取当前登录用户
            AdminVO currentAdmin = adminService.getCurrentAdmin();
            //判断是否为超级管理员
            if (currentAdmin.getSuperAdmin() != null && currentAdmin.getSuperAdmin()) {
                roles.add(Constants.SUPER_ADMIN);
                List<NavigationVO> navigationVOS = shiroChainService.queryAll();
                navigationVOS.forEach(n -> {
                    if (StringUtils.isNotBlank(n.getUrl())) {
                        permissions.add(n.getUrl());
                    }
                });
            } else {
                roles.add(currentAdmin.getRoleCode());
                List<NavigationVO> list = shiroChainService.queryByRole(currentAdmin.getRoleId());
                list.forEach(b -> {
                    if (StringUtils.isNotBlank(b.getUrl())) {
                        permissions.add(b.getUrl());
                    }
                });
            }
        } catch (Exception e) {
            log.error("获取当前登录用户失败！", e);
        }
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    /**
     * 登录时调用 身份认证
     *
     * @param authcToken token
     * @return : org.apache.shiro.authc.AuthenticationInfo
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
            throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(token.getUsername(), token.getPassword(), this.getName());
        this.setSession("currentUser", token.getUsername());
        return authcInfo;
    }

    /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     * 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
     *
     * @param key   键
     * @param value 值
     */
    private void setSession(Object key, Object value) {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            if (null != session) {
                session.setAttribute(key, value);
            }
        }
    }

    /**
     * 清空权限配置（用户再次请求后会执行doGetAuthorizationInfo）
     */
    public void clearAuthz() {
        Boolean redis = customerConfig.getRedis();
        if (redis != null && redis) {
            //获取在线用户
            Collection<String> activeSessions = redisSessionDAO.getActiveAdmin();
            //清空在线用户的权限配置
            activeSessions.forEach(session -> doClearCache(new SimplePrincipalCollection(session, Constants.CUSTOMER_REALM)));
        }
    }
}
