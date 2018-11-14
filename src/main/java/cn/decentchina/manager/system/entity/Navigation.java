package cn.decentchina.manager.system.entity;

import cn.decentchina.manager.system.enums.FunctionTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
    /**
     * 状态
     */
    private Boolean available;
}
