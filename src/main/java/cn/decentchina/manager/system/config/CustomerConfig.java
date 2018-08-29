package cn.decentchina.manager.system.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author 唐全成
 * @date 2018-06-06
 */
@Data
@Component
@ConfigurationProperties(prefix = "shiro-customer")
public class CustomerConfig {
    /**
     * 自定义权限配置链
     */
    private HashMap<String,String> chains;
    /**
     * 是否启用redis
     */
    private Boolean redis;
    /**
     * 登录地址
     */
    private String loginUrl;
    /**
     * 登录成功地址
     */
    private String successUrl;
    /**
     * 未授权页面地址
     */
    private String unauthorizedUrl;
    /**
     * 登录失效时间
     */
    private Long sessionTimeOut;

    @Override
    public String toString() {
        return "CustomerConfig{" +
                "chains=" + chains +
                ", redis=" + redis +
                ", loginUrl='" + loginUrl + '\'' +
                ", successUrl='" + successUrl + '\'' +
                ", unauthorizedUrl='" + unauthorizedUrl + '\'' +
                ", sessionTimeOut=" + sessionTimeOut +
                '}';
    }
}
