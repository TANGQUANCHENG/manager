package cn.decentchina.manager.system.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author 唐全成
 * @date 2018-05-18
 */
@Data
public class RoleNavRelation {

    /**
     * pk
     */
    private Integer id;
    /**
     * 角色
     */
    private Integer roleId;
    /**
     * 导航
     */
    private Integer navigationId;
    /**
     * 创建时间
     */
    private Date createTime;

    public RoleNavRelation(Integer roleId, Integer navigationId, Date createTime) {
        this.roleId = roleId;
        this.navigationId = navigationId;
        this.createTime = createTime;
    }

    public RoleNavRelation() {
    }
}
