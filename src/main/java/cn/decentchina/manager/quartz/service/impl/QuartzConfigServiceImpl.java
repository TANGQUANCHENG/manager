package cn.decentchina.manager.quartz.service.impl;

import cn.decentchina.manager.quartz.dao.QuartzConfigDao;
import cn.decentchina.manager.quartz.entity.QuartzConfig;
import cn.decentchina.manager.quartz.service.QuartzConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定时器配置管理
 *
 * @author wangyx
 */
@Service
public class QuartzConfigServiceImpl implements QuartzConfigService {
    @Autowired
    private QuartzConfigDao quartzConfigDao;

    /**
     * 查询定时器配置
     *
     * @param status 状态
     * @return list
     */
    @Override
    public List<QuartzConfig> queryByStatus(Integer status) {
        return quartzConfigDao.queryByStatus(status);
    }
}
