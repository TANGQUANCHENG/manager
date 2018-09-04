package cn.decentchina.manager.quartz.util;

import cn.decentchina.manager.quartz.entity.QuartzConfig;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.text.ParseException;

/**
 * Schedule工具类
 *
 * @author wangyx
 */
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
        try {
            //创建新的定时任务
            return create(config, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除旧的定时任务，创建新的定时任务
     *
     * @param oldConfig 旧任务配置
     * @param config    新任务配置
     * @param context   spring容器
     * @return true:更新成功;false:更新失败
     */
    public static Boolean modifyScheduler(QuartzConfig oldConfig, QuartzConfig config, ApplicationContext context) {
        if (oldConfig == null || config == null || context == null) {
            return false;
        }
        try {
            String oldJobClassStr = oldConfig.getFullEntity();
            String oldName = oldJobClassStr + oldConfig.getId();
            String oldGroupName = oldConfig.getGroupName();
            //1、清除旧的定时任务
            if (!delete(oldName, oldGroupName)) {
                return false;
            }
            //2、创建新的定时任务
            return create(config, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除任务
     *
     * @param oldName      任务名
     * @param oldGroupName 任务组名
     * @return true:删除成功
     * @throws SchedulerException 定时任务异常
     */
    private static Boolean delete(String oldName, String oldGroupName)
            throws SchedulerException {
        TriggerKey key = new TriggerKey(oldName, oldGroupName);
        Scheduler oldScheduler = schedulerFactory.getScheduler();
        //根据TriggerKey获取trigger是否存在，如果存在则根据key进行删除操作
        Trigger keyTrigger = oldScheduler.getTrigger(key);
        if (keyTrigger != null) {
            oldScheduler.unscheduleJob(key);
        }
        return true;
    }

    /**
     * 提取出的创建定时任务的方法
     *
     * @param config  数据库任务
     * @param context spring容器
     * @return true:创建成功;false:创建失败
     */
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
            e.printStackTrace();
        }
        return false;
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
    private static JobDetail createJobDetail(Class clazz, String name, String groupName, String description) {
        jobDetailFactory.setJobClass(clazz);
        jobDetailFactory.setName(name);
        jobDetailFactory.setGroup(groupName);
        jobDetailFactory.setDescription(description);
        jobDetailFactory.setDurability(true);
        jobDetailFactory.afterPropertiesSet();
        return jobDetailFactory.getObject();
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
        factoryBean.setName(name);
        factoryBean.setJobDetail(job);
        factoryBean.setCronExpression(time);
        factoryBean.setDescription(description);
        factoryBean.setGroup(groupName);
        try {
            factoryBean.afterPropertiesSet();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return factoryBean.getObject();
    }
}
