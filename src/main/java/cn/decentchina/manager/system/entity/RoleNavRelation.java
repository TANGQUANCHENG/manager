package cn.decentchina.manager.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 唐全成
 * @date 2018-05-18
 */
@SuppressWarnings("unused")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    public RoleNavRelation(Integer roleId, Integer navigationId, LocalDateTime createTime) {
        this.roleId = roleId;
        this.navigationId = navigationId;
        this.createTime = createTime;
    }

    public RoleNavRelation() {
    }
}
