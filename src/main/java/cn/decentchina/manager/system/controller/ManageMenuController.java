package cn.decentchina.manager.system.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 唐全成
 * @date 2018-05-19
 */
@RestController
@RequestMapping("manageMenu")
public class ManageMenuController {

    /**
     * 跳转到页面（角色的权限）
     *
     * @param id 权限id
     * @return : org.springframework.web.servlet.ModelAndView
     */
    @RequestMapping("/{id}")
    public ModelAndView toRoleManage(@PathVariable Integer id) {
        ModelAndView md = new ModelAndView("admin/roleManage");
        md.addObject("roleId", id);
        return md;
    }
}
