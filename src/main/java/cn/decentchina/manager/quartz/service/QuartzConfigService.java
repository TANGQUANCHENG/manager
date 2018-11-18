package cn.decentchina.manager.quartz.service;


import cn.decentchina.manager.common.dto.SimpleMessage;
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
     * @return : java.util.List<cn.decentchina.manager.quartz.entity.QuartzConfig>
     */
    List<QuartzConfig> queryByStatus(Integer status);

    /**
     * 修改定时任务
     *
     * @param config 修改信息
     * @param modify 是否修改cron表达式
     * @return SimpleMessage
     */
    SimpleMessage modifyQuartz(QuartzConfig config, Boolean modify);
}
