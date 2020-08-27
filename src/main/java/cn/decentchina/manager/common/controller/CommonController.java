package cn.decentchina.manager.common.controller;

import cn.decentchina.manager.common.dto.OrderNotifyDTO;
import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.common.dto.UsrInfo;
import cn.decentchina.manager.common.enums.ErrorCodeEnum;
import cn.decentchina.manager.common.util.JsonUtil;
import cn.decentchina.manager.common.util.StringHelpers;
import cn.decentchina.manager.system.util.HttpClient;
import cn.decentchina.manager.system.util.HttpUtil;
import cn.decentchina.manager.system.util.Md5;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author 唐全成
 * @date 2018-08-31
 */
@Slf4j
@RequestMapping("/shiro")
@RestController
public class CommonController {
    @Resource
    private DefaultKaptcha defaultKaptcha;

    private static final String DOMAIN = "http://testwx.365taoquan.cn/";
    /**
     * 响应成功码
     */
    private static final int SUCCESS_CODE = 200;

    /**
     * 图形验证码
     *
     * @param httpServletRequest  请求
     * @param httpServletResponse 响应
     * @throws Exception 异常
     */
    @RequestMapping("/defaultKaptcha")
    public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        byte[] captchaChallengeAsJpeg;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到session中
            String createText = defaultKaptcha.createText();
            httpServletRequest.getSession().setAttribute("verifyCode", createText);
            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream =
                httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    /**
     * 图形验证码测试页面
     *
     * @return : org.springframework.web.servlet.ModelAndView
     */
    @RequestMapping("/testKaptcha")
    public ModelAndView testKaptcha() {
        return new ModelAndView("testKaptcha");
    }

    /**
     * 校验验证码
     *
     * @param httpServletRequest 请求
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     */
    @RequestMapping("/imgVerifyControllerDefaultKaptcha")
    public SimpleMessage imgVerifyControllerDefaultKaptcha(HttpServletRequest httpServletRequest) {
        String captchaId = (String) httpServletRequest.getSession().getAttribute("verifyCode");
        String parameter = httpServletRequest.getParameter("verifyCode");
        System.out.println("Session  verifyCode " + captchaId + " form verifyCode " + parameter);
        if (!captchaId.equals(parameter)) {
            return new SimpleMessage(ErrorCodeEnum.ERROR, "错误的验证码");
        } else {
            return new SimpleMessage(ErrorCodeEnum.OK);
        }
    }


    public static void main(String[] args) throws IOException {

//        JEditorPane jEditorPane = new JEditorPane(new URL("http://www.baidu.com/"));
//
//        jEditorPane.setSize(3000, 3000);
//
//        BufferedImage bufferedImage = new BufferedImage(jEditorPane.getWidth(), jEditorPane.getHeight(), BufferedImage.TYPE_INT_ARGB);
//
//        SwingUtilities.paintComponent(bufferedImage.createGraphics(), jEditorPane, new JPanel(), 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
//
//        ImageIO.write(bufferedImage, "png", new File("output.png"));

//        String a="dCCWjc1bXlsoLrViakOJsWHBGEzVDi/x65rOD14YPyIxpC6qbtwL+BvMbqk0zL+/Lphtqug7TWKVKY9EPF2FDGIpFVchcIDEB93tvwWQmJLQAZU7ua0ZBCFObvCnOMGteSjC90EuN8fgYTzVkFNgKnJQPwtmTW2FlA3zbjeA+jM=";
//
//        String decrypt = AesUtil.decrypt(a, "tMD2nXwcb5R3bzrC", "I9WetNeSpJDCqOse");
//        System.out.println(decrypt);

//        String str = "code=200&data=rU0pN3gXY+cFjDt0vhtfIBFx5wOhvInF3ekFMHXYlbwpb154tpUVv1ZXrHn/jselGT55gmL07cHd+dlIM1/NcgoUFIBRNFhW54CKKFNzRiSJwxek8RmcY9auPZn4Sqr21j28oozfQ5zrgVYG+XhG80Dg5Y7c3TfAmN0CIG8+0LOyMsSvqHq+ejCEBDnuzNnBWVMCPRGRXowLAzIZm9CVtLqs0GPokTanKmmubuPm2CMfZ2nWaIZnQIeUxgotvEFrsQSlJZdg8Oi5GtklH/5T+XgshvsEv5anAbwuGJCfqjhmt8ZIikSpu+buzle5o/4yvSyjGFUNetOH1HptEirtKDPFFmXxK4Dka0obc7Q1vCjmKQZAEKeQnjMcxrkmmANtELTGQsKEf1UA3GwVfL0yvyCiu5iXZop9ulaek3Fz7s2QSZuNj5zNSvvlh6zvacZDVGxjLVg80ZNT2j3SCzv1hhfpuciyW7rfLkD9/dZsxrKWaaCTwNxDEgc6UExQsn4xqkRGlZfhKDBR8Gwm+C1j/mjdEazLy5XXtzJLv6c7JHlqEPyKOdJAai6cVTWDWQb1AdI9pxkVVJDmWCeif68LpjXXNOHJTeKluMIYEIYSQVsyZ7W4LCFemEJTiibefsXcJLelPInBdRrO4mttBc/vNsjpAk/AFHN8GKO7PZoQXe0=&message=查询成功&time_stamp=1567569589522";
//        str += "&tradeKey=snBN6cj4i3P5NPyeetdjZiSBR7K5HGXR";
//        System.out.println(str);
//        String aCase = Md5.crypt(str).toUpperCase();
//        System.out.println(aCase);
        String bb="{\"goods_no\":\"1001\",\"stock_num\":50}";


    }


    @RequestMapping("/order1")
    public ModelAndView order(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String appId = "8913";
        int payType = 0;
        String goodsNo = "15001001";
        int count = 2;
        Long timeStamp = System.currentTimeMillis();
        String notifyUrl = "http://abc.com";
        String batchNo = StringUtils.substring(UUID.randomUUID().toString(), 0, 20);
        String param = "app_id=" + appId + "&batch_no=" + batchNo + "&count=" + count + "&goods_no=" + goodsNo
                + "&notify_url=" + URLEncoder.encode(notifyUrl, "utf-8") + "&pay_type=" + payType + "&time_stamp=" + timeStamp;

        String sign = Md5.crypt(param + "&tradeKey=snBN6cj4i3P5NPyeetdjZiSBR7K5HGXR").toUpperCase();
        response.sendRedirect("http://172.16.0.165:8946/api/pay?" + param + "&sign=" + sign);
        return null;
    }


    /**
     * 打开产品选择页面
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/commodityList")
    public ModelAndView commodityList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return new ModelAndView("demo/commodityList");
    }

    /**
     * 订单结果
     *
     * @return
     */
    @RequestMapping("/orderResult")
    public ModelAndView orderResult(String app_id, String batch_no, String time_stamp, String sign) {
        return new ModelAndView("demo/orderResult");
    }

    /**
     * 订单结果
     *
     * @return
     */
    @RequestMapping("/orderNotify")
    public String orderNotify(OrderNotifyDTO dto) throws UnsupportedEncodingException {
        log.info("接收到订单通知：{}", dto);
        if (StringUtils.isNotBlank(dto.getData())) {
//            String decrypt = AesUtil.decrypt(dto.getData(), "tMD2nXwcb5R3bzrC", "I9WetNeSpJDCqOse");
//            log.info("接收到订单通知，data：{}", decrypt);
        }
        return "success";
    }

    /**
     * 订单状态结果
     *
     * @return
     */
    @RequestMapping("/orderChangeNotify")
    public String orderChangeNotify(OrderNotifyDTO dto) throws UnsupportedEncodingException {
        log.info("接收到订单状态改变通知：{}", dto);
        if (StringUtils.isNotBlank(dto.getData())) {
//            String decrypt = AesUtil.decrypt(dto.getData(), "tMD2nXwcb5R3bzrC", "I9WetNeSpJDCqOse");
//            log.info("接收到订单通知，data：{}", decrypt);
        }
        return "success";
    }

    @RequestMapping("/order")
    public SimpleMessage order(String goodsNo, Integer count) throws IOException {

        String appId = "8913";
        int payType = 0;
        if (StringUtils.isBlank(goodsNo)) {
            goodsNo = "11001004";
        }
        if (count == null) {
            count = 2;
        }
        Long timeStamp = System.currentTimeMillis();
        String notifyUrl = DOMAIN + "shiro/orderNotify";
        String orderChangeNotifyUrl = DOMAIN + "shiro/orderChangeNotify";
        String redirectUrl = DOMAIN + "shiro/orderResult";
        String batchNo = StringUtils.substring(UUID.randomUUID().toString(), 0, 20);
        String param = "app_id=" + appId + "&batch_no=" + batchNo + "&count=" + count + "&goods_no=" + goodsNo
                + "&notify_url=" + notifyUrl + "&order_change_notify_url=" + orderChangeNotifyUrl
                + "&pay_type=" + payType + "&redirect_url=" + redirectUrl + "&time_stamp=" + timeStamp;
        log.info("签名原串：[{}]", param);
        String sign = Md5.crypt(param + "&tradeKey=snBN6cj4i3P5NPyeetdjZiSBR7K5HGXR").toUpperCase();
        Map<String, String> data = new HashMap<>(16);
        data.put("app_id", appId);
        data.put("batch_no", batchNo);
        data.put("count", String.valueOf(count));
        data.put("goods_no", goodsNo);
        data.put("notify_url", notifyUrl);
        data.put("order_change_notify_url", orderChangeNotifyUrl);
        data.put("pay_type", String.valueOf(payType));
        data.put("redirect_url", redirectUrl);
        data.put("time_stamp", String.valueOf(timeStamp));
        data.put("sign", sign);
        String result = HttpUtil.sendPost("https://wx2.ejiaofei.com/api/pay", data, "UTF-8");
        log.info("下单结果：{}", result);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(result);
        int errorCode = jsonNode.get("code").intValue();
        if (errorCode != SUCCESS_CODE) {
            log.error("下单不成功：,{}", result);
        }
        String cashierUrl = jsonNode.get("cashier_url").toString();
        return new SimpleMessage(SUCCESS_CODE, cashierUrl);
    }


    /**
     * 构建参数
     *
     * @return
     */
    private List<NameValuePair> getAddTypeNoticeParams(UsrInfo usrInfo) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("app_id", usrInfo.getApp_id()));
//        params.add(new BasicNameValuePair("brand_id", usrInfo.getBrand_id().toString()));
//        params.add(new BasicNameValuePair("commodity_id", usrInfo.getCommodity_id().toString()));
//        params.add(new BasicNameValuePair("goods_no", usrInfo.getGoods_no()));
        params.add(new BasicNameValuePair("time_stamp", usrInfo.getTime_stamp()));
        params.add(new BasicNameValuePair("sign", Md5.crypt(buildSign(usrInfo, "snBN6cj4i3P5NPyeetdjZiSBR7K5HGXR",
                true, null)).toUpperCase()));
        return params;
    }

    public static <T> String buildSign(T t, String key, boolean humpToUnderline, List<String> ignoreKeys) {
        if (Objects.isNull(t) || StringUtils.isBlank(key)) {
            return "";
        }
        StringBuilder sb = new StringBuilder(100);
        try {
            Map<String, String> map = BeanUtils.describe(t);
            TreeMap<String, String> treeMap = new TreeMap<>(map);
            String classType = "class";
            String sign = "sign";
            boolean flag = Objects.nonNull(ignoreKeys) && !ignoreKeys.isEmpty();
            treeMap.forEach((k, v) -> {
                if (!StringUtils.equals(classType, k) && !StringUtils.equals(sign, k) && StringUtils.isNotBlank(v)) {
                    if (!flag || !ignoreKeys.contains(k)) {
                        sb.append(humpToUnderline ? StringHelpers.humpToUnderline(k) : k);
                        sb.append("=");
                        sb.append(v);
                        sb.append("&");
                    }
                }
            });
            sb.append("tradeKey");
            sb.append("=");
            sb.append(key);
        } catch (Exception e) {
            log.error("buildSign Object [{}] and sign [{}] error", JsonUtil.java2Json(t), key, e);
            return "";
        }
        return sb.toString();
    }

}
