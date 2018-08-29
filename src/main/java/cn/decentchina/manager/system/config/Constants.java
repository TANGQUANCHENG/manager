package cn.decentchina.manager.system.config;

/**
 * @author 唐全成
 * @date 2018-05-22
 */
public class Constants {

    /**
     * 自定义shiro过滤器名称
     */
    public static final String CUSTOMER_SHIRO_FILTER="roleOrFilter";
    /**
     * 默认登录地址
     */
    public static final String DEFAULT_LOGIN_URL="/login";
    /**
     * 默认首页
     */
    public static final String DEFAULT_INDEX="/index";
    /**
     * 默认未授权页面
     */
    public static final String DEFAULT_403="/403";
    /**
     * 超级管理员角色
     */
    public static final String SUPER_ADMIN="superAdmin";

    public static final String SHIRO_REDIS_KEY="CustomerShiroFilterFactoryBean";

    public static final String CUSTOME_REALM="MyShiroRealm";
    /**
     * 公钥
     */
    public static final String PUBLIC_KEY="public_key";
    /**
     * 私钥
     */
    public static final String PRIVATE_KEY="private_key";
    /**
     * 随机字符串
     */
    public static final String RANDOM_STR="random_str";
    /**
     * 通用编码格式
     */
    public static final String CHARSET="UTF-8";
    /**
     * 默认密码
     */
    public static final String DEFAULT_PWD="dx123456";
}
