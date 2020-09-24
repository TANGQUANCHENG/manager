package cn.decentchina.manager.system.controller;

import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.handler.AppExceptionHandler;
import cn.decentchina.manager.system.entity.Navigation;
import cn.decentchina.manager.system.service.BindService;
import cn.decentchina.manager.system.service.NavigationService;
import cn.decentchina.manager.system.vo.NavigationVO;
import cn.decentchina.manager.system.vo.TreeVO;
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
@RequestMapping("navigation")
public class NavigationController {

    @Resource
    private NavigationService navigationService;
    @Resource
    private BindService bindService;

    /**
     * 跳转到页面
     *
     * @return : org.springframework.web.servlet.ModelAndView
     */
    @RequestMapping("")
    public ModelAndView toPage() {
        ModelAndView md = new ModelAndView("admin/navigation");
        md.addObject("firstLevel", navigationService.queryFirstLevel());
        return md;
    }

    /**
     * 查询功能菜单分页数据
     *
     * @return : cn.decentchina.manager.system.vo.TreeVO
     */
    @RequestMapping("/list")
    public TreeVO queryList() {
        List<NavigationVO> navigations = navigationService.queryAll();
        return new TreeVO(navigations.size(), navigations);
    }

    /**
     * 查询功能菜单分页数据
     *
     * @param id 权限id
     * @return : cn.decentchina.manager.system.vo.TreeVO
     */
    @RequestMapping("/role/{id}")
    public TreeVO queryByRole(@PathVariable Integer id) {
        return bindService.queryByRole(id);
    }

    /**
     * 新增功能菜单
     *
     * @param navigation 功能菜单
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    @RequestMapping("/add")
    public SimpleMessage addNavigation(Navigation navigation) throws Exception {
        return navigationService.addNavigation(navigation);
    }

    /**
     * 修改功能菜单
     *
     * @param navigation 功能菜单
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    @RequestMapping("/update")
    public SimpleMessage updateNavigation(Navigation navigation) throws Exception {
        return navigationService.updateNavigation(navigation);
    }

    /**
     * 删除功能菜单
     *
     * @param id 功能菜单id
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    @RequestMapping("/delete/{id}")
    public SimpleMessage deleteNavigation(@PathVariable Integer id) throws Exception {
        return navigationService.deleteNavigation(id);
    }

    /**
     * 请求所有的以及菜单
     *
     * @return : java.util.List<cn.decentchina.manager.system.entity.Navigation>
     */
    @RequestMapping("/queryParents")
    public List<Navigation> queryParents() {
        return navigationService.queryFirstLevel();
    }


    /**
     * 修改功能状态
     *
     * @param id 功能菜单id
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    @RequestMapping("/updateStatus/{id}")
    public SimpleMessage updateStatus(@PathVariable Integer id, Boolean available) throws Exception {
        return navigationService.updateStatus(id, available);
    }
}
