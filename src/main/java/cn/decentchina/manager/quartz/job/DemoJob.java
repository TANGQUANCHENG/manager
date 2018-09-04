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
    private ListeningExecutorService executorService =
            MoreExecutors.listeningDecorator(new ScheduledThreadPoolExecutor(10,
                    new BasicThreadFactory.Builder().namingPattern("DemoThread-pool-%d").daemon(true).build()));

    @Override
    public void execute(JobExecutionContext context) {
        List<Object> orderList = new ArrayList<>();
        orderList.add("Tom");
        orderList.add("Mark");
        orderList.add("Lily");
        orderList.add("Lucy");
        for (Object order : orderList) {
            executorService.submit(new DemoThread(order));
        }
        // 等待所有线程运行结束后关闭
        executorService.shutdown();
    }
}
