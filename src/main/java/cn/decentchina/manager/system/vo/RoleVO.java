package cn.decentchina.manager.system.vo;

import cn.decentchina.manager.system.entity.Role;
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
public class RoleVO extends Role {

    /**
     * 创建人姓名
     */
    private String creatorName;
}
