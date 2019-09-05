package cn.decentchina.manager.common.dto;

import lombok.Data;

/**
 * @author 唐全成
 * @date 2019-09-04
 */
@Data
public class UsrInfo {
    private String app_id;
    private String time_stamp;
    private String sign;
    private String goods_no;
    private Integer brand_id;
    private Integer commodity_id;
}
