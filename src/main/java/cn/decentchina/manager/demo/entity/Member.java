package cn.decentchina.manager.demo.entity;

import cn.decentchina.manager.demo.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 注：该类为demo演示类，可删除
 *
 * @author 唐全成
 * @date 2018-08-29
 */
@Data
public class Member {

    /**
     * pk
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private String age;
    /**
     * 性别
     */
    private GenderEnum gender;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime modifyTime;

    /**
     * 头像
     */
    private String avatar;

}
