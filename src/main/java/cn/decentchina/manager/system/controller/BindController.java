package cn.decentchina.manager.system.controller;

import cn.decentchina.manager.handler.AppExceptionHandler;
import cn.decentchina.manager.system.dto.RoleNavBindingDTO;
import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.system.service.BindService;
import cn.decentchina.manager.system.service.NavigationService;
import cn.decentchina.manager.system.service.RoleService;
import cn.decentchina.manager.system.vo.NavigationVO;
import cn.decentchina.manager.system.vo.RoleVO;
import cn.decentchina.manager.system.vo.TreeVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 唐全成
 * @date 2018-05-19
 */
@RestController
@RequestMapping("bind")
public class BindController {

    @Resource
    private NavigationService navigationService;
    @Resource
    private RoleService roleService;
    @Resource
    private BindService bindService;

    /**
     * 跳转到页面
     *
     * @param roleId 权限id
     * @return : org.springframework.web.servlet.ModelAndView
     */
    @RequestMapping("")
    public ModelAndView toPage(Integer roleId) {
        ModelAndView modelAndView = new ModelAndView("admin/bind");
        if (roleId != null) {
            modelAndView.addObject("roleId", roleId);
            modelAndView.addObject("treeVO", bindService.queryByRole(roleId));
        }
        return modelAndView;
    }

    /**
     * 请求所有的导航
     *
     * @return : cn.decentchina.manager.system.vo.TreeVO
     * @author : zhongzq
     */
    @RequestMapping("/allNav")
    public TreeVO queryAllNavigation() {
        List<NavigationVO> navigationVOS = navigationService.queryAll();
        return new TreeVO(navigationVOS.size(), navigationVOS);
    }

    /**
     * 请求所有的角色
     *
     * @return : java.util.List<cn.decentchina.manager.system.vo.RoleVO>
     */
    @RequestMapping("/allRole")
    public List<RoleVO> queryAllRole() {
        return roleService.queryAllRole(null);
    }

    /**
     * 新增角色与资源的关联关系
     *
     * @param dto 角色与资源的关联关系
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    @RequestMapping("/addDutyNavBinding")
    public SimpleMessage addDutyNavBinding(RoleNavBindingDTO dto) throws Exception {
        return bindService.batchBind(dto.getNavs(), dto.getRoles());
    }

    /**
     * 解除绑定关系
     *
     * @param relationIds 绑定关系id
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    @RequestMapping("/relieveBind")
    public SimpleMessage relieveBind(Integer[] relationIds) throws Exception {
        return bindService.relieveBind(relationIds);
    }


    /**
     * 绑定权限
     *
     * @param roleId 权限id
     * @return : org.springframework.web.servlet.ModelAndView
     */
    @RequestMapping("/toBind/{roleId}")
    public ModelAndView toBindByRole(@PathVariable Integer roleId) {
        ModelAndView md = new ModelAndView("admin/editRoleNav");
        md.addObject("roleId", roleId);
        return md;
    }

    /**
     * 获取权限数据
     *
     * @param roleId 权限id
     * @return : cn.decentchina.manager.system.vo.TreeVO
     */
    @RequestMapping("/getData/{roleId}")
    public TreeVO getDataByRole(@PathVariable Integer roleId) {
        //查询全部菜单
        List<NavigationVO> navigationVOS = navigationService.queryAll();
        //根据角色id查询
        TreeVO treeVO = bindService.queryByRole(roleId);
        List<NavigationVO> rows = treeVO.getRows();

        if (rows != null && !rows.isEmpty()) {
            navigationVOS.forEach(navigationVO -> rows.forEach(n -> {
                if (navigationVO.getId().equals(n.getId())) {
                    navigationVO.setSelected(true);
                }
            }));
        }
        return new TreeVO(navigationVOS.size(), navigationVOS);
    }

}
