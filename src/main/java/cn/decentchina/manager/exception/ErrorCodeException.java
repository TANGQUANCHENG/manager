package cn.decentchina.manager.exception;

import cn.decentchina.manager.common.enums.ErrorCodeEnum;
import lombok.ToString;

/**
 * @author 唐全成
 */
@ToString
public class ErrorCodeException extends RuntimeException {

    private static final long serialVersionUID = -7638041501183925225L;

    private Integer code;

    public ErrorCodeException(ErrorCodeEnum errorCode, String msg) {
        super(msg);
        this.code = errorCode.getCode();
    }

    public ErrorCodeException(ErrorCodeEnum errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
