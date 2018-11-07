package cn.decentchina.manager.system.config;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.ShiroHttpServletResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

/**
 * 去除登录页面地址后的JSESSIONID
 *
 * @author 唐全成
 * @date 2018-08-30
 */
public class MyShiroHttpServletResponse extends ShiroHttpServletResponse {
    public MyShiroHttpServletResponse(HttpServletResponse wrapped, ServletContext context, ShiroHttpServletRequest request) {
        super(wrapped, context, request);
    }

    @Override
    protected String toEncoded(String url, String sessionId) {
        if ((url == null) || (sessionId == null)) {
            return (url);
        }

        String path = url;
        String query = "";
        String anchor = "";
        int question = url.indexOf('?');
        if (question >= 0) {
            path = url.substring(0, question);
            query = url.substring(question);
        }
        int pound = path.indexOf('#');
        if (pound >= 0) {
            anchor = path.substring(pound);
            path = path.substring(0, pound);
        }

        return (path + anchor +
                query);
    }
}