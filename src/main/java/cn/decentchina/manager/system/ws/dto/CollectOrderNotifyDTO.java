package cn.decentchina.manager.system.ws.dto;

import lombok.Data;

/**
 * @author 唐全成
 * @date 2018-07-05
 */
@Data
public class CollectOrderNotifyDTO {

    /**
     * 标题
     */
    private String title;
    /**
     * 批次号
     */
    private String batchNo;
    /**
     * 时间戳
     */
    private Long timestamp;
    /**
     * 签名
     */
    private String sign;
}
