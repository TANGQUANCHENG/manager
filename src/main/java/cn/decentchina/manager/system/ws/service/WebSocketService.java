package cn.decentchina.manager.system.ws.service;

import cn.decentchina.manager.system.ws.dto.WiselyResponse;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 唐全成
 * @date 2018-07-04
 */
@Service
public class WebSocketService {
    @Resource
    private SimpMessagingTemplate template;

    /**
     * 广播
     * 发给所有在线用户
     *
     * @param msg
     */
    public void sendMsg(WiselyResponse msg) {
        template.convertAndSend("/index", msg);
    }

    /**
     * 指向用户发送
     *
     * @param user
     * @param msg
     */
    public void sendToUser(String user, WiselyResponse msg) {
        template.convertAndSendToUser(user, "/notify", msg);
    }

    /**
     * 每10s向前端发送消息
     */
    @Scheduled(cron = "0/10 * * * * ? ")
    public void testMsg() {
        WiselyResponse msg=WiselyResponse.builder().batchNo(String.valueOf(System.currentTimeMillis()))
                .msgType(0).title("服务器消息发送").operationTime(System.currentTimeMillis()).commodityName("测试消息").build();
        sendMsg(msg);
    }

}
