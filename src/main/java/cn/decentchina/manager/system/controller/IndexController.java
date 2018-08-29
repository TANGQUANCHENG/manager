package cn.decentchina.manager.system.controller;

//import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.system.config.Constants;
import cn.decentchina.manager.system.config.CustomerConfig;
import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.system.entity.Admin;
import cn.decentchina.manager.common.enums.ErrorCodeEnum;
import cn.decentchina.manager.system.service.AdminService;
import cn.decentchina.manager.system.service.BindService;
import cn.decentchina.manager.system.service.NavigationService;
import cn.decentchina.manager.system.service.PublicKeyService;
import cn.decentchina.manager.system.vo.NavigationVO;
import cn.decentchina.manager.system.vo.TreeVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.List;

/**
 * @author 唐全成
 * @date 2018-05-18
 */
@Slf4j
@RequestMapping
@RestController
public class IndexController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private NavigationService navigationService;
    @Autowired
    private BindService bindService;
    @Autowired
    private PublicKeyService publicKeyService;

    @Autowired
    private CustomerConfig customerConfig;
    /**
     * 项目首页
     * @return
     */
    @RequestMapping("")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView md = new ModelAndView("index");
        try {
            Admin currentAdmin = adminService.getCurrentAdmin();
            List<NavigationVO> menu;
            if (currentAdmin.getSuperAdmin()!=null&&currentAdmin.getSuperAdmin()) {
                menu = navigationService.queryAll();
            } else {
                TreeVO treeVO = bindService.queryByRole(currentAdmin.getRoleId());
                menu = treeVO.getRows();
            }
            md.addObject("menus", menu);
        } catch (Exception e) {
            log.error("系统异常：[{}]", e.getMessage());
            e.printStackTrace();
        }
        return md;
    }

    /**
     * 工作台（介绍）
     * @return
     */
    @RequestMapping("/introduce")
    public ModelAndView introduce() {
        return new ModelAndView("introduce");
    }

    /**
     * 登录页面
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request) {
        String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
        request.getSession().setAttribute("basePath",basePath);
        return new ModelAndView("login");
    }

    /**
     * 请求登录
     * @param admin
     * @return
     */
    @RequestMapping("/signIn")
    public SimpleMessage signIn(Admin admin, HttpServletRequest httpServletRequest) {

        try {
            if (StringUtils.isBlank(admin.getPhoneNo())) {
                return new SimpleMessage(ErrorCodeEnum.NO, "请输入登录账号");
            }
            HttpSession session = httpServletRequest.getSession();
            RSAPrivateKey privateKey = (RSAPrivateKey) session.getAttribute(Constants.PRIVATE_KEY);
            String randomStr = (String) session.getAttribute(Constants.RANDOM_STR);
            //从session中删除
            session.removeAttribute(Constants.PRIVATE_KEY);
            session.removeAttribute(Constants.RANDOM_STR);
            return adminService.login(admin.getPhoneNo(), admin.getLoginPwd(), privateKey, randomStr, httpServletRequest);
        } catch (Exception e) {
            log.error("登录账号[{}]登录失败,时间为[{}],ip地址为[{}]", admin.getPhoneNo(), System.currentTimeMillis(),
                    httpServletRequest.getRemoteAddr(), e);
            return new SimpleMessage(ErrorCodeEnum.NO, "登录失败，请稍后再试");
        }
    }

    /**
     * 退出
     * @return
     */
    @RequestMapping("/logout")
    public SimpleMessage logout() {
        SecurityUtils.getSubject().logout();
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

    /**
     * 未授权
     * @return
     */
    @RequestMapping("/403")
    public ModelAndView unauthorized() {
        return new ModelAndView("403");
    }

    /**
     * 获取公钥
     *
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("/getPublicKey")
    public HashMap<String, String> getPublicKey(HttpServletRequest httpServletRequest) {
        return publicKeyService.getPublicKey(httpServletRequest);
    }

}
