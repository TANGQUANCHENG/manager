package cn.decentchina.manager.common.dto;

import cn.decentchina.manager.common.enums.ResponseCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 接口简易返回信息
 *
 * @author 唐全成
 */
@Data
public class SimpleMessage implements Serializable {
    private static final long serialVersionUID = -2957516153008725933L;
    /**
     * 接口返回码
     */
    private Integer errorCode;
    /**
     * 返回消息
     */
    private String errorMsg;

    public SimpleMessage() {
    }

    public SimpleMessage(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public SimpleMessage(ResponseCodeEnum errorCode) {
        this.errorCode = errorCode.getCode();
        this.errorMsg = errorCode.getMessage();
    }

    public SimpleMessage(ResponseCodeEnum errorCode, String errorMsg) {
        this.errorCode = errorCode.getCode();
        this.errorMsg = errorMsg;
    }
}
