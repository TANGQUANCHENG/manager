package cn.decentchina.manager.system.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;

import java.util.Map;

/**
 * @author 唐全成
 * @date 2018-06-07
 */
public abstract class BaseShiroFactoryBean extends ShiroFilterFactoryBean {
    /**
     * 重新加载权限
     *
     * @return : java.util.Map<java.lang.String,java.lang.String>
     */
    public abstract Map<String, String> loadChainFromDatabase();
}
