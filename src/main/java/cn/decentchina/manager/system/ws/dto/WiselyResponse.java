package cn.decentchina.manager.system.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author 唐全成
 * @date 2018-04-23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WiselyResponse {

    private String title;

    private String commodityName;

    private String batchNo;

    private Long operationTime;

    private String sign;
    /**
     * 消息类型(0卡券回收,1一键售后,2骑士卡售后订单)
     */
    private Integer msgType;
    /**
     * 一键售后监控数量
     */
    private Integer customServiceNum;

}
