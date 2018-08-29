package cn.decentchina.manager.system.entity;

import cn.decentchina.manager.system.enums.FunctionTypeEnum;
import lombok.Data;

import java.util.Date;

/**
 *
 * 系统导航菜单
 *
 * @author 唐全成
 * @date 2018-05-18
 */
@Data
public class Navigation {

    private Integer id;
    /**
     * 功能名
     */
    private String functionName;

    /**
     * 导航类型
     */
    private FunctionTypeEnum type;

    /**
     * 父节点
     */
    private Integer parentId;
    /**
     * icon
     */
    private String icon;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 菜单访问的地址
     */
    private String url;
    /**
     * 备注
     */
    private String comment;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 状态
     */
    private Boolean available;
}
