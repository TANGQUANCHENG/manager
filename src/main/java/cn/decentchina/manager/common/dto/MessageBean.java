package cn.decentchina.manager.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 接口返回信息
 *
 * @author 唐全成
 */
@Data
public class MessageBean implements Serializable {
    private static final long serialVersionUID = 7192766535561421181L;
    private String errorMsg;
    private Object data;
    private Integer errorCode;
}