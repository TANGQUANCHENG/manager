package cn.decentchina.manager.system.controller;

import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.system.entity.Navigation;
import cn.decentchina.manager.system.service.BindService;
import cn.decentchina.manager.system.service.NavigationService;
import cn.decentchina.manager.system.vo.NavigationVO;
import cn.decentchina.manager.system.vo.TreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author 唐全成
 * @date 2018-05-18
 */
@RestController
@RequestMapping("navigation")
public class NavigationController {


    @Autowired
    private NavigationService navigationService;


    @Autowired
    private BindService bindService;

    /**
     * 跳转到页面
     *
     * @return
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
     * @return
     */
    @RequestMapping("/list")
    public TreeVO queryList() {
        List<NavigationVO> navigationVOS = navigationService.queryAll();
        return new TreeVO(navigationVOS.size(), navigationVOS);
    }

    /**
     * 查询功能菜单分页数据
     *
     * @return
     */
    @RequestMapping("/role/{id}")
    public TreeVO queryByRole(@PathVariable Integer id) {
        return bindService.queryByRole(id);
}

    /**
     * 新增功能菜单
     *
     * @param navigation
     * @return
     */
    @RequestMapping("/add")
    public SimpleMessage addNavigation(Navigation navigation) {
        return navigationService.addNavigation(navigation);
    }

    /**
     * 修改功能菜单
     *
     * @param navigation
     * @return
     */
    @RequestMapping("/update")
    public SimpleMessage updateNavigation(Navigation navigation) {
        return navigationService.updateNavigation(navigation);
    }

    /**
     * 删除功能菜单
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public SimpleMessage deleteNavigation(@PathVariable Integer id) {
        return navigationService.deleteNavigation(id);
    }

    /**
     * 请求所有的以及菜单
     * @return
     */
    @RequestMapping("/queryParents")
    public List<Navigation> queryParents() {
        return navigationService.queryFirstLevel();
    }


    /**
     * 修改功能状态
     *
     * @param id
     * @return
     */
    @RequestMapping("/updateStatus/{id}")
    public SimpleMessage updateStatus(@PathVariable Integer id, Boolean available) {
        return navigationService.updateStatus(id,available);
    }
}
