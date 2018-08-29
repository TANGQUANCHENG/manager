package cn.decentchina.manager.system.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import cn.decentchina.manager.system.cache.RedisCacheManager;
import cn.decentchina.manager.system.cache.RedisSessionDAO;
import cn.decentchina.manager.system.filter.CustomRolesAuthorizationFilter;
import cn.decentchina.manager.system.realm.MyShiroRealm;
import cn.decentchina.manager.system.service.ShiroChainService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * shiro配置
 *
 * @author wangyx
 */
@Slf4j
@Configuration
public class ShiroConfigs {


    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ShiroChainService shiroChainService;
    @Autowired
    private CustomerConfig customerConfig;

    @Bean(name = "shiroFilter")
    public BaseShiroFactoryBean shiroFilter(@Qualifier(value = "securityManager") SecurityManager securityManager) {
        //根据配置加载是否启用redis
        Boolean redis = customerConfig.getRedis();
        BaseShiroFactoryBean shiroFilterFactoryBean;
        if (redis != null && redis) {
            shiroFilterFactoryBean = new CustomerRedisShiroFilterFactoryBean(shiroChainService, redisTemplate);
        } else {
            shiroFilterFactoryBean = new CustomerShiroFilterFactoryBean(shiroChainService);
        }
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put(Constants.CUSTOMER_SHIRO_FILTER, customRolesAuthorizationFilter());
        shiroFilterFactoryBean.setFilters(filters);
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //加载默认配置.
        Map<String, String> filterChainDefinitionMap = new HashMap<>(16);
        defaultFilterChain(filterChainDefinitionMap);
        //加载自定义配置
        loadCustomerChain(filterChainDefinitionMap);
        //登录地址
        if (StringUtils.isBlank(customerConfig.getLoginUrl())) {
            shiroFilterFactoryBean.setLoginUrl(Constants.DEFAULT_LOGIN_URL);
        } else {
            shiroFilterFactoryBean.setLoginUrl(customerConfig.getLoginUrl());
        }
        //登录成功后要跳转的链接
        if (StringUtils.isBlank(customerConfig.getSuccessUrl())) {
            shiroFilterFactoryBean.setSuccessUrl(Constants.DEFAULT_INDEX);
        } else {
            shiroFilterFactoryBean.setSuccessUrl(customerConfig.getSuccessUrl());
        }
        //未授权界面;
        if (StringUtils.isBlank(customerConfig.getUnauthorizedUrl())) {
            shiroFilterFactoryBean.setUnauthorizedUrl(Constants.DEFAULT_403);
        } else {
            shiroFilterFactoryBean.setUnauthorizedUrl(customerConfig.getUnauthorizedUrl());
        }
        //加载静态权限链
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        //加载动态权限链
        shiroFilterFactoryBean.setFilterChainDefinitions("");
        return shiroFilterFactoryBean;
    }

    /**
     * 默认配置链
     *
     * @return
     */
    public static void defaultFilterChain(Map<String, String> filterChainDefinitionMap) {

        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/shiro/**", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/signIn", "anon");
        filterChainDefinitionMap.put("/getPublicKey", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/style/**", "anon");
        filterChainDefinitionMap.put("/asset/**", "anon");
        filterChainDefinitionMap.put("/plugin/**", "anon");
        filterChainDefinitionMap.put("/component/**", "anon");
        filterChainDefinitionMap.put("/modules/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
    }


    /**
     * 读取application.yml shiro-customer
     * 记载自定义角色配置连
     *
     * @param filterChainDefinitionMap
     */
    void loadCustomerChain(Map<String, String> filterChainDefinitionMap) {
        //加载自定义角色配置链
        HashMap<String, String> rolesChain = customerConfig.getChains();
        if (rolesChain != null && !rolesChain.isEmpty()) {
            Iterator iterator = rolesChain.entrySet().iterator();
            Map<String, String> lawfulMap = new HashMap<>(16);
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                lawfulMap.put(convertKey(entry.getKey()), entry.getValue().toString());
            }
            filterChainDefinitionMap.putAll(lawfulMap);
            log.info("customer shiro-roles-chain loaded:{}", lawfulMap.toString());
        }
    }

    /**
     * 转化key
     *
     * @param o
     * @return
     */
    private String convertKey(Object o) {
        //将点（.）转化为斜杠(/)
        String replace = StringUtils.replace(o.toString(), "-", "/");
        //-1转化为*
        return "/" + StringUtils.replace(replace, "-1", "*");
    }

    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        return myShiroRealm;
    }


    @Bean(name = "securityManager")
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        securityManager.setSessionManager(getSessionManage());
        //加载自定义配置（是否启用redis）
        Boolean redis = customerConfig.getRedis();
        if (redis != null && redis) {
            securityManager.setCacheManager(redisCacheManager());
        }
        return securityManager;
    }

    @Bean(name = Constants.CUSTOMER_SHIRO_FILTER)
    public CustomRolesAuthorizationFilter customRolesAuthorizationFilter() {
        return new CustomRolesAuthorizationFilter();
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }


    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO sessionDAO = new RedisSessionDAO();
        sessionDAO.redisTemplate = redisTemplate;
        return sessionDAO;
    }

    @Bean
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager manager = new RedisCacheManager();
        manager.setRedisTemplate(redisTemplate);
        return manager;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    @Bean(name = "sessionManager")
    public SessionManager getSessionManage() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionValidationScheduler(getExecutorServiceSessionValidationScheduler());
        Boolean redis = customerConfig.getRedis();
        if (redis != null && redis) {
            sessionManager.setSessionIdCookieEnabled(true);
            sessionManager.setSessionIdCookie(getSessionIdCookie());
            sessionManager.setCacheManager(redisCacheManager());
            sessionManager.setSessionDAO(redisSessionDAO());
        }
        //shiro过期配置，未配置则为1800000（30分钟）
        Long sessionTimeOut = customerConfig.getSessionTimeOut();
        if (sessionTimeOut != null) {
            sessionManager.setGlobalSessionTimeout(sessionTimeOut);
        }
        // -----可以添加session 创建、删除的监听器
        return sessionManager;
    }

    @Bean(name = "sessionValidationScheduler")
    public ExecutorServiceSessionValidationScheduler getExecutorServiceSessionValidationScheduler() {
        ExecutorServiceSessionValidationScheduler scheduler = new ExecutorServiceSessionValidationScheduler();
        scheduler.setInterval(900000);
        return scheduler;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }

    @Bean(name = "sessionIdCookie")
    public SimpleCookie getSessionIdCookie() {
        SimpleCookie cookie = new SimpleCookie("mysid");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(-1);
        return cookie;
    }
}
