package cn.decentchina.manager.common.enums;

/**
 * 通用状态枚举
 *
 * @author wangyx
 */
public enum CommonStatusEnum {
    /**
     * 正常/上架/启用
     */
    ON(1),
    /**
     * 关闭/下架/禁用
     */
    OFF(0);
    private Integer status;

    CommonStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
