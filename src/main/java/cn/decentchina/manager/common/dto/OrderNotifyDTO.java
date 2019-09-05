package cn.decentchina.manager.common.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 唐全成
 * @date 2019-09-02
 */
@Data
public class OrderNotifyDTO {

    private Integer code;

    private  String app_id;

    private String batch_no;

    private String platform_batch_no;

    private String brand_name;

    private String commodity_name;

    private String goods_no;

    private String transaction_id;

    private Integer count;

    private BigDecimal total_amount;

    private String data;

    private String message;

    private String time_stamp;

    private String sign;
}
