package cn.decentchina.manager.exception;

/**
 * 自定义操作异常
 *
 * @author 唐全成
 */
public final class OperateFailException extends RuntimeException {

    private static final long serialVersionUID = 5158257097465375836L;

    public OperateFailException(String message) {
        super(message);
    }

    public OperateFailException(String message, Exception e) {
        super(message, e);
    }
}
