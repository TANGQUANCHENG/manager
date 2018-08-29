package cn.decentchina.manager.system.config;

import cn.decentchina.manager.system.service.ShiroChainService;
import cn.decentchina.manager.system.vo.ShiroChainVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * description:
 *
 * @author 唐全成
 * @date 2018/3/19
 */
@Slf4j
public class CustomerShiroFilterFactoryBean extends BaseShiroFactoryBean {

    private ShiroChainService shiroChainService;

    @Autowired
    private CustomerConfig customerConfig;

    public CustomerShiroFilterFactoryBean(ShiroChainService shiroChainService) {
        this.shiroChainService = shiroChainService;
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
    public Map<String, String> loadChainFromDatabase(){
        Map<String, String> filterChainDefinitionMap = this.getFilterChainDefinitionMap();
        Map<String, String> sortedMap=new LinkedHashMap<>(16);
        sortedMap.putAll(filterChainDefinitionMap);
        ShiroConfigs.defaultFilterChain(sortedMap);
        List<ShiroChainVO> shiroChainVOS = shiroChainService.queryShiroChain();
        shiroChainVOS.forEach(chain->
                sortedMap.put(chain.getUrl(),
                        Constants.CUSTOMER_SHIRO_FILTER+"["+chain.getRoles()+","+ Constants.SUPER_ADMIN+"]"));
        sortedMap.put("/**", "authc");
        return sortedMap;
    }
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
}
