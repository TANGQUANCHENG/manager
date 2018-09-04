package cn.decentchina.manager.quartz.service;



import cn.decentchina.manager.quartz.entity.QuartzConfig;

import java.util.List;

/**
 * 定时器配置管理
 *
 * @author wangyx
 */
public interface QuartzConfigService {
    /**
     * 查询定时器配置
     *
     * @param status 状态
     * @return list
     */
    List<QuartzConfig> queryByStatus(Integer status);
}
