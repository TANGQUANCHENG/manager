package cn.decentchina.manager.system.vo;

import cn.decentchina.manager.system.entity.Admin;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author 唐全成
 * @date 2018-05-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
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
