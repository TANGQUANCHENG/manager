package cn.decentchina.manager.quartz.thread;

import cn.decentchina.manager.demo.entity.Member;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 唐全成
 * @date 2018-09-04
 */
@Slf4j
public class MemberThread  implements Runnable {

    private Member member;

    public MemberThread(Member order) {
        this.member = order;
    }

    @Override
    public void run() {
        log.info("MEMBER:{}",member);
    }
}
