package cn.decentchina.manager.system.vo;

import cn.decentchina.manager.system.entity.Navigation;
import lombok.Data;

/**
 * @author 唐全成
 * @date 2018-05-18
 */
@Data
public class NavigationVO extends Navigation {

    /**
     * 父菜单id(easyui treegrid 数据参数要求)
     */
    private Integer _parentId;

    private Integer navId;

    private Boolean selected ;
}
