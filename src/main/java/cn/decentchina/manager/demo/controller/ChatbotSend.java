package cn.decentchina.manager.demo.controller;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author 唐全成
 * @date 2018-10-11
 */
@Slf4j
public class ChatbotSend {

    public static void main(String[] args) throws Exception {

        String url = "https://oapi.dingtalk.com/robot/send?access_token=afb10b075054fec44cdc8886b87b819f48c01d7be79cbadc50fae1b2a16eeb96";

        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");

        String textMsg = "{\n" +
                "     \"msgtype\": \"markdown\",\n" +
                "     \"markdown\": {\"title\":\"365淘券统计\",\n" +
                "\"text\":\"####  当前时间：2019-05-13 12:30\\n\\n > ![screenshot](https://wx5.ejiaofei.com/member/testDrawImg?_=" + System.currentTimeMillis() + ")\\n  > ###### 10点30分发布  \"\n" +
                "     },\n" +
                "    \"at\": {\n" +
                "        \"atMobiles\": [\n" +
                "            \"1825718XXXX\"\n" +
                "        ], \n" +
                "        \"isAtAll\": false\n" +
                "    }\n" +
                " }";
        StringEntity se = new StringEntity(textMsg, "utf-8");
        httppost.setEntity(se);
        HttpResponse response;
        try {
            response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println(result);
            }
            log.info("当前有三分钟未审核的订单,通知钉钉机器人");
        } catch (IOException e) {
            log.error("通知钉钉机器人失败。[{}]", e);
        }
    }
}
