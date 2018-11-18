package cn.decentchina.manager.quartz.job;

import cn.decentchina.manager.quartz.thread.DemoThread;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author 唐全成
 * @date 2018-09-04
 */
@DisallowConcurrentExecution
@Slf4j
@Component
public class DemoJob implements Job {
    /**
     * 线程池
     */
    private static final ListeningExecutorService EXECUTOR_SERVICE =
            MoreExecutors.listeningDecorator(new ScheduledThreadPoolExecutor(10,
                    new BasicThreadFactory.Builder().namingPattern("DemoThread-pool-%d").daemon(true).build()));

    @Override
    public void execute(JobExecutionContext context) {
        Arrays.asList("Tom","Mark","Lily","Lucy").forEach(object -> EXECUTOR_SERVICE.execute(new DemoThread(object)));
    }
}
