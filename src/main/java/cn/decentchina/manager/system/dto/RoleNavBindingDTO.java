package cn.decentchina.manager.system.dto;

import lombok.Data;

/**
 *
 * 功能与角色绑定传输参数
 *
 * @author 唐全成
 * @date 2018-05-19
 */
@Data
public class RoleNavBindingDTO {

    /**
     * 选择的资源列表
     */
    private Integer[] navs;
    /**
     * 选择的角色列表
     */
    private Integer[] roles;
}
