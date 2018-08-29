package cn.decentchina.manager.common.dto;

import cn.decentchina.manager.common.enums.ErrorCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 接口简易返回信息
 *
 * @author wangyx
 */
@Data
public class SimpleMessage implements Serializable {
    private static final long serialVersionUID = -2957516153008725933L;
    private Integer errorCode;
    private String errorMsg;

    public SimpleMessage() {
    }

    public SimpleMessage(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public SimpleMessage(ErrorCodeEnum errorCode) {
        this.errorCode = errorCode.getCode();
        this.errorMsg = errorCode.getMessage();
    }

    public SimpleMessage(ErrorCodeEnum errorCode, String errorMsg) {
        this.errorCode = errorCode.getCode();
        this.errorMsg = errorMsg;
    }
}
