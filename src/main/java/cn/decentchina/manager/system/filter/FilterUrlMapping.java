package cn.decentchina.manager.system.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author 唐全成
 * @date 2019-06-18
 */
@Component
public class FilterUrlMapping {

    @Autowired
    ApplicationContext applicationContext;

    public Set<String> allUrlMappings() {
        Set<String> result = new HashSet();
        RequestMappingHandlerMapping rmhp = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = rmhp.getHandlerMethods();
        for (RequestMappingInfo info : map.keySet()) {
            //getMatchingPatterns优化
            result.add(info.getPatternsCondition().toString().replace("[", "").replace("]", ""));
        }
        return result;
    }

    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new XssFilter());
        Set<String> allSaveUrlPattern = allUrlMappings();

        if(CollectionUtils.isEmpty(allSaveUrlPattern)){
            return filterRegistration;
        }
        String pattern = "/*.*save.*";
        Set<String> urlPatterns = new LinkedHashSet();
        for (String saveUrlPattern : allSaveUrlPattern) {
            if (Pattern.matches(pattern, saveUrlPattern)) {
                urlPatterns.add(saveUrlPattern);
            }
        }
        filterRegistration.setUrlPatterns(urlPatterns);

        filterRegistration.setName("XssFilter");
        return filterRegistration;
    }

}
