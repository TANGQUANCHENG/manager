package cn.decentchina.manager.demo.dto;

import cn.decentchina.manager.demo.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author 唐全成
 * @date 2018-08-29
 */
@Data
public class MemberQueryDTO {

    private String name;

    private GenderEnum gender;

    private Integer minAge;

    private Integer maxAge;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime queryStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime queryEndTime;
}
