package cn.decentchina.manager.quartz.util;

import cn.decentchina.manager.quartz.entity.QuartzConfig;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;


/**
 * Schedule工具类
 *
 * @author wangyx
 */
@SuppressWarnings("unused")
public class SchedulerUtil {
    /**
     * 定时任务Scheduler的工厂类，Quartz提供
     */
    private static StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
    /**
     * CronTrigger的工厂类
     */
    private static CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
    /**
     * JobDetail的工厂类
     */
    private static JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
    /**
     * 自动注入Spring Bean的工厂类
     */
    private static AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
    /**
     * 定时任务Scheduler的工厂类，Spring Framework提供
     */
    private static SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();

    static {
        //加载指定路径的配置
        schedulerFactoryBean.setConfigLocation(new ClassPathResource("quartz.properties"));
    }

    /**
     * 创建定时任务，根据参数，创建对应的定时任务，并使之生效
     *
     * @param config  数据库任务配置
     * @param context spring容器
     * @return true:创建成功;false:创建失败
     */
    public static boolean createScheduler(QuartzConfig config, ApplicationContext context) {
        //创建新的定时任务
        return create(config, context);
    }

    /**
     * 删除旧的定时任务，创建新的定时任务
     *
     * @param config  任务配置
     * @param context spring容器
     * @return true:更新成功;false:更新失败
     */
    public static Boolean modifyScheduler(QuartzConfig config, ApplicationContext context) {
        if (config == null || context == null) {
            return false;
        }
        //1、清除旧的定时任务
        if (deleteJob(config, context)) {
            //2、创建新的定时任务
            return create(config, context);
        }
        return false;
    }

    /**
     * 提取的删除任务的方法
     *
     * @param oldConfig          旧配置
     * @param applicationContext 上下文
     * @return true
     */
    public static Boolean deleteJob(QuartzConfig oldConfig, ApplicationContext applicationContext) {
        try {
            jobFactory.setApplicationContext(applicationContext);
            schedulerFactoryBean.setJobFactory(jobFactory);
            Scheduler oldScheduler = schedulerFactoryBean.getScheduler();
            oldScheduler.pauseTrigger(TriggerKey.triggerKey(oldConfig.getFullEntity() + oldConfig.getId(), oldConfig.getGroupName()));
            oldScheduler.unscheduleJob(TriggerKey.triggerKey(oldConfig.getFullEntity() + oldConfig.getId(), oldConfig.getGroupName()));
            oldScheduler.pauseJob(JobKey.jobKey(oldConfig.getFullEntity() + oldConfig.getId(), oldConfig.getGroupName()));
            oldScheduler.deleteJob(JobKey.jobKey(oldConfig.getFullEntity() + oldConfig.getId(), oldConfig.getGroupName()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 提取出的创建定时任务的方法
     *
     * @param config  数据库任务
     * @param context spring容器
     * @return true:创建成功;false:创建失败
     */
    @SuppressWarnings("unchecked")
    private static Boolean create(QuartzConfig config, ApplicationContext context) {
        try {
            //创建新的定时任务
            String jobClassStr = config.getFullEntity();
            Class clazz = Class.forName(jobClassStr);
            String name = jobClassStr + config.getId();
            String groupName = config.getGroupName();
            String description = config.toString();
            String time = config.getCronTime();

            JobDetail jobDetail = createJobDetail(clazz, name, groupName, description);
            Trigger trigger = createCronTrigger(jobDetail, time, name, groupName, description);

            jobFactory.setApplicationContext(context);

            schedulerFactoryBean.setJobFactory(jobFactory);
            schedulerFactoryBean.setJobDetails(jobDetail);
            schedulerFactoryBean.setTriggers(trigger);
            schedulerFactoryBean.afterPropertiesSet();
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 根据指定的参数，创建JobDetail
     *
     * @param clazz       任务对应类
     * @param name        任务名
     * @param groupName   任务组名
     * @param description 任务描述
     * @return JobDetail
     */
    @SuppressWarnings("unchecked")
    private static JobDetail createJobDetail(Class clazz, String name, String groupName, String description) {
        return JobBuilder.newJob(clazz)
                .withIdentity(JobKey.jobKey(name, groupName)).withDescription(description).storeDurably(true)
                .build();
    }

    /**
     * 根据参数，创建对应的CronTrigger对象
     *
     * @param job         任务
     * @param time        cron表达式
     * @param name        任务名
     * @param groupName   任务组名
     * @param description 任务描述
     * @return CronTrigger
     */
    private static CronTrigger createCronTrigger(JobDetail job, String time, String name, String groupName,
                                                 String description) {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                .cronSchedule(time)
                .withMisfireHandlingInstructionDoNothing();

        return TriggerBuilder.newTrigger()
                .withIdentity(TriggerKey.triggerKey(name, groupName)).forJob(job).withDescription(description)
                .withSchedule(scheduleBuilder)
                .build();
    }
}
