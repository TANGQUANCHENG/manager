package cn.decentchina.manager.advice;

import cn.decentchina.manager.common.enums.ResponseCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import cn.decentchina.manager.common.dto.MessageBean;
import cn.decentchina.manager.common.dto.SimpleMessage;
/**
 * 全局响应信息统一封装管理
 *
 * @author 唐全成cover王元鑫
 */
@Slf4j
@SuppressWarnings("NullableProblems")
@ControllerAdvice
public class AppResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    private static final String EXCLUDE = "list";
    private static final String BIND="bind";
    private static final String EXCLUDE2="navigation/role";
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletServerHttpRequest= (ServletServerHttpRequest) request;
            if (StringUtils.endsWith(servletServerHttpRequest.getURI().getPath(), EXCLUDE)
                    ||StringUtils.indexOf(servletServerHttpRequest.getURI().getPath(),BIND)>0
                    ||StringUtils.indexOf(servletServerHttpRequest.getURI().getPath(),EXCLUDE2)>0) {
                return body;
            }

        }
        if (body instanceof SimpleMessage) {
            log.info("[{}]接口响应[{}]", request.getURI(), body);
            return body;
        }
        MessageBean result = new MessageBean();
        result.setErrorCode(ResponseCodeEnum.OK.getCode());
        result.setErrorMsg("OK");
        result.setData(body);
        log.info("[{}]接口响应[{}]", request.getURI(), StringUtils.left(body.toString(), 200));
        body = result;
        return body;
    }
}