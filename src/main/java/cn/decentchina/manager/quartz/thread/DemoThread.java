package cn.decentchina.manager.quartz.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 唐全成
 * @date 2018-09-04
 */
@Slf4j
public class DemoThread  implements Runnable {
    private Object order;

    public DemoThread(Object order) {
        this.order = order;
    }

    @Override
    public void run() {
        log.info("处理：{}",order);
        /*
        ...
        线程业务
        ...
         */
    }
}
