package cn.decentchina.manager.system.entity;

import lombok.Data;

/**
 *
 * 管理员
 *
 * @author 唐全成
 * @date 2018-05-18
 */
@Data
public class Admin {
    /**
     * pk
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 角色
     */
    private Integer roleId;
    /**
     * 电话号码
     */
    private String phoneNo;
    /**
     * 登陆密码
     */
    private String loginPwd;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 是否是超级管理员(0否1是)
     */
    private Boolean superAdmin;
}
