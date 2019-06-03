package cn.decentchina.manager.demo.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 唐全成
 * @date 2019-03-30
 */
@Data
public class AqyCode {

    private Integer id;

    private String code;

    private LocalDateTime processingTime;

    private Integer status;

    private Integer remark;
}
