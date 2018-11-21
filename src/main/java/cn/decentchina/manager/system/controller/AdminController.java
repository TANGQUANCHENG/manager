package cn.decentchina.manager.system.controller;

import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.system.entity.Admin;
import cn.decentchina.manager.common.enums.ErrorCodeEnum;
import cn.decentchina.manager.system.service.AdminService;
import cn.decentchina.manager.system.service.RoleService;
import cn.decentchina.manager.system.util.ValidateUtils;
import cn.decentchina.manager.system.vo.AdminVO;
import cn.decentchina.manager.system.vo.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @author 唐全成
 * @date 2018-05-18
 */
@RestController
@RequestMapping("admin")
public class AdminController {

    @Resource
    private AdminService adminService;
    @Resource
    private RoleService roleService;

    /**
     * 跳转到页面
     *
     * @return : org.springframework.web.servlet.ModelAndView
     */
    @RequestMapping("")
    public ModelAndView toPage() throws Exception {
        ModelAndView md = new ModelAndView("admin/list");
        md.addObject("roles", roleService.queryAll());
        return md;
    }

    /**
     * 查询管理员分页数据
     *
     * @param page       分页信息
     * @param searchText 查询条件
     * @return : cn.decentchina.manager.system.vo.Page<cn.decentchina.manager.system.vo.AdminVO>
     */
    @RequestMapping("/list")
    public Page<AdminVO> queryList(Page page, String searchText) {
        return adminService.queryAdminListPage(page, searchText);
    }

    /**
     * 新增管理员
     *
     * @param admin 管理员
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    @RequestMapping("/add")
    public SimpleMessage addAdmin(Admin admin) throws Exception {
        return adminService.addAdmin(admin);
    }

    /**
     * 修改管理员
     *
     * @param admin 管理员
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    @RequestMapping("/update")
    public SimpleMessage updateAdmin(Admin admin) throws Exception {
        return adminService.updateAdmin(admin);
    }

    /**
     * 删除管理员
     *
     * @param id 管理员id
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    @RequestMapping("/delete/{id}")
    public SimpleMessage deleteAdmin(@PathVariable Integer id) throws Exception {
        return adminService.deleteAdmin(id);
    }

    /**
     * 修改管理员状态
     *
     * @param admin 管理员
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    @RequestMapping("/updateStatus")
    public SimpleMessage updateAdminStatus(Admin admin) throws Exception {
        return adminService.updateStatus(admin);
    }

    /**
     * 修改密码
     *
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    @RequestMapping("/updatePassword")
    public SimpleMessage updatePassword(String oldPwd, String newPwd) throws Exception {
        if (StringUtils.isAnyBlank(oldPwd, newPwd)) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "原密码或新密码不能为空");
        }
        if (!ValidateUtils.isLegalPassword(newPwd)) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "新密码至少为8位数字、大小写字母、特殊符号组合");
        }
        return adminService.updatePassword(oldPwd, newPwd);
    }

    /**
     * 重置密码
     *
     * @param id 会员id
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    @RequestMapping("/reset/{id}")
    public SimpleMessage resetPassword(@PathVariable Integer id) throws Exception {
        return adminService.resetPassword(id);
    }
}
