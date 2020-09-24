package cn.decentchina.manager.system.config;

import cn.decentchina.manager.system.service.ShiroChainService;
import cn.decentchina.manager.system.util.MapToJson;
import cn.decentchina.manager.system.vo.ShiroChainVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:
 *
 * @author 唐全成
 * @date 2018/3/19
 */
@Slf4j
public class CustomerRedisShiroFilterFactoryBean extends BaseShiroFactoryBean {

    private ShiroChainService shiroChainService;

    private RedisTemplate<String, Object> redisTemplate;

    public CustomerRedisShiroFilterFactoryBean(ShiroChainService shiroChainService, RedisTemplate<String, Object> redisTemplate) {
        this.shiroChainService = shiroChainService;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Class getObjectType() {
        return MySpringShiroFilter.class;
    }

    @Override
    public void setFilterChainDefinitions(String definitions) {

        Map<String, String> filterChainDefinitionMap = loadChainFromDatabase();
        this.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }

    @Override
    public Map<String, String> loadChainFromDatabase() {

        Map<String, String> filterChainDefinitionMap = this.getFilterChainDefinitionMap();
        ShiroConfigs.defaultFilterChain(filterChainDefinitionMap);
        List<ShiroChainVO> shiroChains = shiroChainService.queryShiroChain();
        shiroChains.forEach(chain ->
                filterChainDefinitionMap.put(chain.getUrl(),
                        "roleOrFilter[" + chain.getRoles() + "," + Constants.SUPER_ADMIN + "]"));
        filterChainDefinitionMap.put("/**", "authc");
        return filterChainDefinitionMap;
    }

    @SuppressWarnings("Duplicates")
    @Override
    protected AbstractShiroFilter createInstance() throws Exception {

        SecurityManager securityManager = getSecurityManager();
        if (securityManager == null) {
            String msg = "SecurityManager property must be set.";
            throw new BeanInitializationException(msg);
        }

        if (!(securityManager instanceof WebSecurityManager)) {
            String msg = "The security manager does not implement the WebSecurityManager interface.";
            throw new BeanInitializationException(msg);
        }
        FilterChainManager manager = createFilterChainManager();

        PathMatchingFilterChainResolver chainResolver = new PathMatchingFilterChainResolver();
        chainResolver.setFilterChainManager(manager);

        return new MySpringShiroFilter((WebSecurityManager) securityManager, chainResolver);
    }

    private static final class MySpringShiroFilter extends AbstractShiroFilter {

        protected MySpringShiroFilter(WebSecurityManager webSecurityManager, FilterChainResolver resolver) {
            super();

            if (webSecurityManager == null) {
                throw new IllegalArgumentException("WebSecurityManager property cannot be null.");
            }
            setSecurityManager(webSecurityManager);
            if (resolver != null) {
                setFilterChainResolver(resolver);
            }
        }
    }

    /**
     * 重写方法：从缓存中获取权限资源
     *
     * @return : java.util.Map<java.lang.String,java.lang.String>
     */
    @Override
    public Map<String, String> getFilterChainDefinitionMap() {
        String json = (String) redisTemplate.opsForValue().get(Constants.SHIRO_REDIS_KEY);
        Map<String, String> stringStringMap = MapToJson.toMap(json);
        return stringStringMap == null ? new HashMap<>(16) : stringStringMap;
    }

    /**
     * 重写方法：将权限资源放入缓存
     *
     * @param filterChainDefinitionMap 权限资源
     */
    @Override
    public void setFilterChainDefinitionMap(Map<String, String> filterChainDefinitionMap) {
        String s = MapToJson.toJson(filterChainDefinitionMap);
        if (StringUtils.isBlank(s)) {
            return;
        }
        redisTemplate.opsForValue().set(Constants.SHIRO_REDIS_KEY, s);
    }
}
