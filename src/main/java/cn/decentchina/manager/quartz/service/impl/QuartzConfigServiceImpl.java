package cn.decentchina.manager.quartz.service.impl;

import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.common.enums.CommonStatusEnum;
import cn.decentchina.manager.common.enums.ErrorCodeEnum;
import cn.decentchina.manager.exception.ErrorCodeException;
import cn.decentchina.manager.quartz.dao.QuartzConfigDao;
import cn.decentchina.manager.quartz.entity.QuartzConfig;
import cn.decentchina.manager.quartz.service.QuartzConfigService;
import cn.decentchina.manager.quartz.util.SchedulerUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * 定时器配置管理
 *
 * @author wangyx
 */
@Service
public class QuartzConfigServiceImpl implements QuartzConfigService {
    @Resource
    private QuartzConfigDao quartzConfigDao;
    @Resource
    private ApplicationContext applicationContext;

    /**
     * 查询定时器配置
     *
     * @param status 状态
     * @return : java.util.List<cn.decentchina.manager.quartz.entity.QuartzConfig>
     */
    @Override
    public List<QuartzConfig> queryByStatus(Integer status) {
        return quartzConfigDao.queryByStatus(status);
    }

    /**
     * 修改定时任务
     *
     * @param config 修改信息
     * @param modify 是否修改cron表达式
     * @return SimpleMessage
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public SimpleMessage modifyQuartz(QuartzConfig config, Boolean modify) {
        QuartzConfig dbConfig = quartzConfigDao.queryById(config.getId());
        if (dbConfig == null) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "任务不存在");
        }
        if (dbConfig.getJobStatus().equals(config.getJobStatus())) {
            return new SimpleMessage(ErrorCodeEnum.NO, "任务已" +
                    (dbConfig.getJobStatus().equals(CommonStatusEnum.ON.getStatus()) ? "开启" : "关闭"));
        }
        // 修改数据库状态
        int row = quartzConfigDao.modifyQuartz(config);
        if (row != 1) {
            return new SimpleMessage(ErrorCodeEnum.NO, "任务修改失败");
        }
        dbConfig.setJobStatus(config.getJobStatus());
        if (Optional.ofNullable(modify).isPresent()) {
            if (StringUtils.isNotBlank(config.getCronTime())) {
                dbConfig.setCronTime(config.getCronTime());
            }
            //修改定时任务
            if (!SchedulerUtil.modifyScheduler(dbConfig, applicationContext)) {
                throw new ErrorCodeException(ErrorCodeEnum.ERROR, "未能修改成功!");
            }
        } else if (dbConfig.getJobStatus().equals(CommonStatusEnum.ON.getStatus())) {

            // 开启新定时任务
            if (!SchedulerUtil.createScheduler(dbConfig, applicationContext)) {
                throw new ErrorCodeException(ErrorCodeEnum.ERROR, "未能开启成功!");
            }
        } else {
            // 关闭 = 删除就任务
            if (!SchedulerUtil.deleteJob(dbConfig, applicationContext)) {
                throw new ErrorCodeException(ErrorCodeEnum.ERROR, "未能关闭成功!");
            }
        }
        return new SimpleMessage(ErrorCodeEnum.OK);
    }
}
