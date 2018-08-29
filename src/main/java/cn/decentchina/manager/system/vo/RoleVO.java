package cn.decentchina.manager.system.vo;

import cn.decentchina.manager.system.entity.Role;
import lombok.Data;

/**
 * @author 唐全成
 * @date 2018-05-18
 */
@Data
public class RoleVO extends Role {

    /**
     * 创建人姓名
     */
    private String creatorName;
}
