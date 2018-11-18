package cn.decentchina.manager.config;

import cn.decentchina.manager.quartz.entity.QuartzConfig;
import cn.decentchina.manager.quartz.service.QuartzConfigService;
import cn.decentchina.manager.quartz.util.SchedulerUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author wangyuanxin
 */
@Slf4j
@Configuration
@ConditionalOnExpression("'${quartz.enabled}'=='true'")
public class SchedulerConfig {
    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private QuartzConfigService quartzConfigService;

    @Bean
    public StdSchedulerFactory stdSchedulerFactory() {
        StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
        //获取JobConfig集合,
        int normalJobStatus = 1;
        List<QuartzConfig> configs = quartzConfigService.queryByStatus(normalJobStatus);
        for (QuartzConfig config : configs) {
            try {
                Boolean flag = SchedulerUtil.createScheduler(config, applicationContext);
                log.info("[{}]执行结果：[{}]", config, (flag ? "成功" : "失败"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return stdSchedulerFactory;
    }
}
