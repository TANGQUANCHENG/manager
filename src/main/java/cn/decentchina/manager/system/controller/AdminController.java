package cn.decentchina.manager.system.controller;

import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.system.entity.Admin;
import cn.decentchina.manager.common.enums.ErrorCodeEnum;
import cn.decentchina.manager.system.service.AdminService;
import cn.decentchina.manager.system.service.RoleService;
import cn.decentchina.manager.system.vo.AdminVO;
import cn.decentchina.manager.system.vo.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 唐全成
 * @date 2018-05-18
 */
@RestController
@RequestMapping("admin")
public class AdminController {


    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    /**
     * 跳转到页面
     * @return
     */
    @RequestMapping("")
    public ModelAndView toPage() throws Exception {
        ModelAndView md=new ModelAndView("admin/list");
        md.addObject("roles",roleService.queryAll());
        return md;
    }

    /**
     * 查询管理员分页数据
     *
     * @param page
     * @param searchText
     * @return
     */
    @RequestMapping("/list")
    public Page<AdminVO> queryList(Page page, String searchText){
        return adminService.queryAdminListPage(page,searchText);
    }

    /**
     * 新增管理员
     * @param admin
     * @return
     */
    @RequestMapping("/add")
    public SimpleMessage addAdmin(Admin admin){
        return adminService.addAdmin(admin);
    }

    /**
     * 修改管理员
     *
     * @param admin
     * @return
     */
    @RequestMapping("/update")
    public SimpleMessage updateAdmin(Admin admin){
        return adminService.updateAdmin(admin);
    }

    /**
     * 删除管理员
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public SimpleMessage deleteAdmin(@PathVariable Integer id) throws Exception {
        return adminService.deleteAdmin(id);
    }

    /**
     * 修改管理员状态
     *
     * @param admin
     * @return
     */
    @RequestMapping("/updateStatus")
    public SimpleMessage updateAdminStatus(Admin admin){
        return adminService.updateStatus(admin);
    }

    @RequestMapping("/updatePassword")
    public SimpleMessage updatePassword(String oldPwd, String newPwd) throws Exception {
        if(StringUtils.isAnyBlank(oldPwd,newPwd)){
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS,"原密码或新密码不能为空");
        }
        return adminService.updatePassword(oldPwd, newPwd);
    }
}
