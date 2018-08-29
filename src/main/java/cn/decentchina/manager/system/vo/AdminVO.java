package cn.decentchina.manager.system.vo;

import cn.decentchina.manager.system.entity.Admin;
import lombok.Data;

/**
 * @author 唐全成
 * @date 2018-05-18
 */
@Data
public class AdminVO extends Admin {
    /**
     * 角色名称
     */
    private String role;

    /**
     * 角色编码
     */
    private String roleCode;
}
