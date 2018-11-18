package cn.decentchina.manager.demo.vo;

import cn.decentchina.manager.demo.entity.Member;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author 唐全成
 * @date 2018-08-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberVO extends Member {
    /**
     * 查询开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    /**
     * 查询结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}
