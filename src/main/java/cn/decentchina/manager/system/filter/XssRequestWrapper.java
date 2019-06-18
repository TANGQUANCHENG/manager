package cn.decentchina.manager.system.filter;

import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author 唐全成
 * @date 2019-06-18
 */
public class XssRequestWrapper extends HttpServletRequestWrapper {
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public XssRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String name) {
        //获取所有参数值的集合
        String[] results = this.getParameterMap().get(name);
        if (results != null && results.length > 0) {
            int length = results.length;
            for (int i = 0; i < length; i++) {
                //过滤参数值
                results[i] = HtmlUtils.htmlEscape(results[i]);
            }
            return results;
        }
        return null;
    }
}
