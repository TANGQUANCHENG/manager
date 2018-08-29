package cn.decentchina.manager.demo.dto;

import cn.decentchina.manager.demo.entity.Member;
import lombok.Data;

import java.util.Date;

/**
 * @author 唐全成
 * @date 2018-08-29
 */
@Data
public class MemberQueryDTO {

    private String name;

    private Member.GenderEnum gender;

    private Integer minAge;

    private Integer maxAge;

    private Date queryStartTime;

    private Date queryEndTime;
}
