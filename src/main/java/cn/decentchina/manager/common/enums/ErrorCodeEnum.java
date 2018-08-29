package cn.decentchina.manager.common.enums;

/**
 * 全局http错误编码
 *
 * @author 唐全成
 */
public enum ErrorCodeEnum {
    /**
     * 错误码
     */
    ERROR(9999, "系统异常"),
    HTTP_CONNECTION_OVERTIME(9998, "连接超时"),
    FREQUENTLY_REQUEST(9003, "操作频繁"),
    INVALID_RSA_KEY(9002, "超时失效"),
    INVALID_PARAMS(9001, "非法参数"),
    SIGN_ERROR(9000, "签名错误"),
    FIND_CURRENT_ADMIN_FAILURE(9005,"获取当前登录用户失败"),
    INVALID_STATUS(9004, "状态不符"),
    LACK_TICKET(8001, "卡券不足"),
    ORDER_ERROR(8002, "订单处理异常"),
    ORDER_NOT_EXIST(8002, "订单不存在"),

    OK(200, "请求通过"),
    NO(201, "请求不通过"),

    UNSUPPORTED_PRODUCT(1001, "不支持的产品"),
    INVALID_PRICE(1000, "未配置价格"),

    SUBMIT_NEED_CERTIFICATION(2006, "提交成功,请进行实名认证"),

    BLACK_LIST(3001,"账号黑名单"),
    UN_CERTIFICATED(3002,"转账账号未通过实名认证"),

    NEED_RE_UPLOAD(3003, "图片上传异常,请重新上传");

    private Integer code;

    private String message;

    ErrorCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
