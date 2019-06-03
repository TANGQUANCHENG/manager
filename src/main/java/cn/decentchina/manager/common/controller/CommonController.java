package cn.decentchina.manager.common.controller;

import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.common.enums.ErrorCodeEnum;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.RenderedImage;
/**
 * @author 唐全成
 * @date 2018-08-31
 */
@RequestMapping("/common")
@RestController
public class CommonController {
    @Resource
    private DefaultKaptcha defaultKaptcha;

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

        JEditorPane jEditorPane=new JEditorPane(new URL("http://www.baidu.com/"));

        jEditorPane.setSize(3000,3000);

        BufferedImage bufferedImage=new BufferedImage(jEditorPane.getWidth(),jEditorPane.getHeight(),BufferedImage.TYPE_INT_ARGB);

        SwingUtilities.paintComponent(bufferedImage.createGraphics(),jEditorPane,new JPanel(),0,0,bufferedImage.getWidth(),bufferedImage.getHeight());

        ImageIO.write(bufferedImage,"png",new File("output.png"));
    }

}
