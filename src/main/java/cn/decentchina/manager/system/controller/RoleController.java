package cn.decentchina.manager.system.controller;

import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.handler.AppExceptionHandler;
import cn.decentchina.manager.system.entity.Role;
import cn.decentchina.manager.system.service.RoleService;
import cn.decentchina.manager.system.vo.RoleVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 唐全成
 * @date 2018-05-18
 */
@RestController
@RequestMapping("role")
public class RoleController {

    @Resource
    private RoleService roleService;

    /**
     * 跳转到页面
     *
     * @return : org.springframework.web.servlet.ModelAndView
     */
    @RequestMapping("")
    public ModelAndView toPage() {
        return new ModelAndView("admin/role");
    }

    /**
     * 查询管理员分页数据
     *
     * @param searchText 查询条件
     * @return : java.util.List<cn.decentchina.manager.system.vo.RoleVO>
     */
    @RequestMapping("/list")
    public List<RoleVO> queryList(String searchText) {
        return roleService.queryAllRole(searchText);
    }

    /**
     * 新增管理员
     *
     * @param role 管理员
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    @RequestMapping("/add")
    public SimpleMessage addRole(Role role) throws Exception {
        return roleService.addRole(role);
    }

    /**
     * 修改管理员
     *
     * @param role 管理员
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    @RequestMapping("/update")
    public SimpleMessage updateRole(Role role) throws Exception {
        return roleService.updateRole(role);
    }

    /**
     * 删除管理员
     *
     * @param id 管理员id
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    @RequestMapping("/delete/{id}")
    public SimpleMessage deleteRole(@PathVariable Integer id) throws Exception {
        return roleService.deleteRole(id);
    }

    /**
     * 修改角色状态
     *
     * @param id        角色id
     * @param available 是否有效
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     */
    @RequestMapping("/updateStatus/{id}")
    public SimpleMessage updateStatus(@PathVariable Integer id, Boolean available) {
        return roleService.updateStatus(id, available);
    }
}
