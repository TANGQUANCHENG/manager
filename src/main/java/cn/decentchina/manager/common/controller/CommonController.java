package cn.decentchina.manager.common.controller;

import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.common.enums.ErrorCodeEnum;
import cn.decentchina.manager.common.util.JsonUtil;
import cn.decentchina.manager.common.util.StringHelpers;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
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
