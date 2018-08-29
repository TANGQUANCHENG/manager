package cn.decentchina.manager.system.entity;

import lombok.Data;

import java.util.Date;

/**
 *
 * 系统角色
 *
 * @author 唐全成
 * @date 2018-05-18
 */
@Data
public class Role {

    /**
     * pk
     */
    private Integer id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色代码
     */
    private String code;
    /**
     * 创建人
     */
    private Integer creatorId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 是否可用
     */
    private Boolean available;
    /**
     * 说明
     */
    private String comment;
}
