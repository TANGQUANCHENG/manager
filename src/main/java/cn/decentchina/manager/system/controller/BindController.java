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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author 唐全成
 * @date 2018-05-19
 */
@RestController
@RequestMapping("bind")
public class BindController extends AppExceptionHandler{

    @Autowired
    private NavigationService navigationService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BindService bindService;

    /**
     * 跳转页面
     *
     * @return
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
     * @return
     */
    @RequestMapping("/allNav")
    public TreeVO queryAllNavigation() {
        List<NavigationVO> navigationVOS = navigationService.queryAll();
        return new TreeVO(navigationVOS.size(), navigationVOS);
    }

    /**
     * 请求所有的角色
     *
     * @return
     */
    @RequestMapping("/allRole")
    public List<RoleVO> queryAllRole() {
        return roleService.queryAllRole(null);
    }

    /**
     * 新增角色与资源的关联关系
     *
     * @param dto
     */
    @RequestMapping("/addDutyNavBinding")
    public SimpleMessage addDutyNavBinding(RoleNavBindingDTO dto) throws Exception {
        return bindService.batchBind(dto.getNavs(), dto.getRoles());
    }

    /**
     * 解除绑定关系
     *
     * @param relationIds
     * @return
     */
    @RequestMapping("/relieveBind")
    public SimpleMessage relieveBind(Integer[] relationIds) throws Exception {
        return bindService.relieveBind(relationIds);
    }


    @RequestMapping("/toBind/{roleId}")
    public ModelAndView toBindByRole(@PathVariable Integer roleId) {
        ModelAndView md = new ModelAndView("admin/editRoleNav");
        md.addObject("roleId", roleId);
        return md;
    }

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
