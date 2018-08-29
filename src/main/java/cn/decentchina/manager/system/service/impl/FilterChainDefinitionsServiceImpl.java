package cn.decentchina.manager.system.service.impl;

import cn.decentchina.manager.system.config.BaseShiroFactoryBean;
import cn.decentchina.manager.system.realm.MyShiroRealm;
import cn.decentchina.manager.system.service.FilterChainDefinitionsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Map;


/**
 * 动态加载权限
 * @author 唐全成
 */
@Slf4j
@Component
public class FilterChainDefinitionsServiceImpl implements FilterChainDefinitionsService {

    @Resource
    private BaseShiroFactoryBean permissionFactory;

    /**
     * 重新载入权限
     */
    @Override
    public void reloadFilterChains() {

        //强制同步，控制线程安全
        synchronized (permissionFactory) {
            AbstractShiroFilter shiroFilter = null;
            try {
                shiroFilter = (AbstractShiroFilter) permissionFactory.getObject();
                PathMatchingFilterChainResolver resolver = (PathMatchingFilterChainResolver) shiroFilter
                        .getFilterChainResolver();
                // 过滤管理器
                DefaultFilterChainManager manager = (DefaultFilterChainManager) resolver.getFilterChainManager();
                //获取新加载出来的权限
                Map<String, String> chains = permissionFactory.loadChainFromDatabase();
                // 清除权限配置
                manager.getFilterChains().clear();
                permissionFactory.getFilterChainDefinitionMap().clear();
                //设置新的权限
                permissionFactory.setFilterChainDefinitionMap(chains);
                //生成新的权限
                Iterator<Map.Entry<String, String>> entries = chains.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry<String, String> entry = entries.next();
                    if(StringUtils.isNotBlank(entry.getKey())){
                        manager.createChain(entry.getKey(), entry.getValue());
                    }
                }
                //重新加载已经配置过的权限（用户的角色、授权地址等）
                reloadAuthorizing();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取自定义realm并调用其重新加载权限的方法
     */
    public static void reloadAuthorizing(){
        RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        MyShiroRealm realm = (MyShiroRealm)rsm.getRealms().iterator().next();
        realm.clearAuthz();
    }
}