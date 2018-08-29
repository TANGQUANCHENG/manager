package cn.decentchina.manager.system.controller;

import cn.decentchina.manager.common.dto.SimpleMessage;
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
     * @return
     */
    @RequestMapping("")
    public ModelAndView toPage(){
        ModelAndView md=new ModelAndView("admin/role");
        return md;
    }

    /**
     * 查询管理员分页数据
     *
     * @return
     */
    @RequestMapping("/list")
    public List<RoleVO> queryList(String searchText){
        return roleService.queryAllRole(searchText);
    }

    /**
     * 新增管理员
     * @param role
     * @return
     */
    @RequestMapping("/add")
    public SimpleMessage addRole(Role role){
        return roleService.addRole(role);
    }

    /**
     * 修改管理员
     *
     * @param role
     * @return
     */
    @RequestMapping("/update")
    public SimpleMessage updateRole(Role role){
        return roleService.updateRole(role);
    }

    /**
     * 删除管理员
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public SimpleMessage deleteRole(@PathVariable Integer id){
        return roleService.deleteRole(id);
    }

    /**
     * 修改角色状态
     * @param id
     * @param available
     * @return
     */
    @RequestMapping("/updateStatus/{id}")
    public SimpleMessage updateStatus(@PathVariable Integer id , Boolean available){
        return roleService.updateStatus(id, available);
    }

}
