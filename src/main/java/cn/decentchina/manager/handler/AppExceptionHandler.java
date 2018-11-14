package cn.decentchina.manager.handler;

import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.exception.ErrorCodeException;
import cn.decentchina.manager.common.enums.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 管理平台全局异常处理
 *
 * @author 唐全成
 */
@Slf4j
@ControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler
    @ResponseBody
    public SimpleMessage handleAndReturnData(HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response, Exception ex) {
        SimpleMessage message = new SimpleMessage();
        //noinspection Duplicates
        if (ex instanceof ErrorCodeException) {
            ErrorCodeException e = (ErrorCodeException) ex;
            log.error("[{}]接口异常[{}]", request.getRequestURI(), e.getMessage());
            message.setErrorCode(e.getCode());
            message.setErrorMsg(e.getMessage());
            return message;
        }
        if (ex instanceof NullPointerException) {
            NullPointerException e = (NullPointerException) ex;
            log.error("[{}]接口异常-npe[{}]", request.getRequestURI(), e.getMessage());
            message.setErrorCode(ErrorCodeEnum.ERROR.getCode());
            message.setErrorMsg(e.getMessage());
            return message;
        }
        log.error("[{}]系统异常", request.getRequestURI(), ex);
        message.setErrorCode(ErrorCodeEnum.ERROR.getCode());
        message.setErrorMsg(ex.getMessage());
        return message;
    }
}