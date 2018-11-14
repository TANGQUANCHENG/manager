package cn.decentchina.manager.quartz.controller;

import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.quartz.entity.QuartzConfig;
import cn.decentchina.manager.quartz.service.QuartzConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 定时任务管理
 *
 * @author wangyx
 */
@Slf4j
@RestController
public class QuartzController {
    @Resource
    private QuartzConfigService quartzConfigService;

    /**
     * 管理平台通知修改定时任务
     *
     * @param id        任务id
     * @param jobStatus 任务状态
     * @param modify    是否修改cron表达式
     */
    @SuppressWarnings("JavadocReference")
    @RequestMapping("modifyQuartz")
    public SimpleMessage modifyQuartz(QuartzConfig config, Boolean modify) {
        return quartzConfigService.modifyQuartz(config, modify);
    }
}
