package cn.decentchina.manager.demo.entity;

import cn.decentchina.manager.demo.enums.GenderEnum;
import lombok.Data;

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
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 头像
     */
    private String avatar;

}
