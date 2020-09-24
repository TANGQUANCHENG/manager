package cn.decentchina.manager.system.service.impl;

import cn.decentchina.manager.system.dao.NavigationDao;
import cn.decentchina.manager.system.dao.RoleNavRelationDao;
import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.system.entity.Navigation;
import cn.decentchina.manager.common.enums.ErrorCodeEnum;
import cn.decentchina.manager.system.service.AdminService;
import cn.decentchina.manager.system.service.FilterChainDefinitionsService;
import cn.decentchina.manager.system.service.NavigationService;
import cn.decentchina.manager.system.vo.AdminVO;
import cn.decentchina.manager.system.vo.NavigationVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 唐全成
 * @date 2018-05-18
 */
@Service
public class NavigationServiceImpl implements NavigationService {

    @Resource
    private NavigationDao navigationDao;
    @Resource
    private RoleNavRelationDao roleNavRelationDao;
    @Resource
    private AdminService adminService;
    @Resource
    private FilterChainDefinitionsService filterChainDefinitionsService;

    /**
     * 查询全部导航
     *
     * @return : java.util.List<cn.decentchina.manager.system.vo.NavigationVO>
     */
    @Override
    public List<NavigationVO> queryAll() {
        List<NavigationVO> navigations = navigationDao.queryAll();
        navigations.forEach(n -> n.setSelected(false));
        return navigations;
    }

    /**
     * 新增导航菜单
     *
     * @param navigation 导航菜单
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    @Override
    public SimpleMessage addNavigation(Navigation navigation) throws Exception {

        AdminVO currentAdmin = adminService.getCurrentAdmin();
        if (!currentAdmin.getSuperAdmin()) {
            return new SimpleMessage(ErrorCodeEnum.ERROR, "该操作允许超级管理员执行");
        }

        if (StringUtils.isBlank(navigation.getFunctionName())) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "功能名称不能为空");
        }
        navigation.setAvailable(true);
        if (navigationDao.addNavigation(navigation) < 1) {
            return new SimpleMessage(ErrorCodeEnum.ERROR);
        }
        filterChainDefinitionsService.reloadFilterChains();
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

    /**
     * 修改导航菜单
     *
     * @param navigation 导航菜单
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    @Override
    public SimpleMessage updateNavigation(Navigation navigation) throws Exception {
        AdminVO currentAdmin = adminService.getCurrentAdmin();
        if (!currentAdmin.getSuperAdmin()) {
            return new SimpleMessage(ErrorCodeEnum.ERROR, "该操作允许超级管理员执行");
        }

        if (StringUtils.isBlank(navigation.getFunctionName())) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "功能名称不能为空");
        }
        if (navigationDao.updateNavigation(navigation) < 1) {
            return new SimpleMessage(ErrorCodeEnum.ERROR);
        }
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

    /**
     * 删除导航菜单
     *
     * @param id 导航菜单id
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    @Override
    public SimpleMessage deleteNavigation(Integer id) throws Exception {

        AdminVO currentAdmin = adminService.getCurrentAdmin();
        if (!currentAdmin.getSuperAdmin()) {
            return new SimpleMessage(ErrorCodeEnum.ERROR, "该操作允许超级管理员执行");
        }

        if (navigationDao.deleteNavigation(id) < 1) {
            return new SimpleMessage(ErrorCodeEnum.ERROR);
        }
        filterChainDefinitionsService.reloadFilterChains();
        roleNavRelationDao.deleteByNavId(id);
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

    /**
     * 查询一级菜单
     *
     * @return : java.util.List<cn.decentchina.manager.system.entity.Navigation>
     */
    @Override
    public List<Navigation> queryFirstLevel() {
        return navigationDao.queryParents();
    }

    /**
     * 修改菜单状态
     *
     * @param id     菜单id
     * @param status 菜单状态
     * @return : java.util.List<cn.decentchina.manager.system.entity.Navigation>
     * @throws Exception 异常
     */
    @Override
    public SimpleMessage updateStatus(Integer id, Boolean status) throws Exception {

        AdminVO currentAdmin = adminService.getCurrentAdmin();
        if (!currentAdmin.getSuperAdmin()) {
            return new SimpleMessage(ErrorCodeEnum.ERROR, "该操作允许超级管理员执行");
        }

        NavigationVO navigationVO = navigationDao.queryId(id);

        if (navigationVO == null) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "该功能不存在");
        }
        if (navigationVO.getParentId() == null) {
            return new SimpleMessage(ErrorCodeEnum.ERROR, "一级菜单不允许修改状态");
        }
        if (status.equals(navigationVO.getAvailable())) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "状态相同无需修改");
        }
        navigationDao.updateStatus(id, status);
        filterChainDefinitionsService.reloadFilterChains();
        return new SimpleMessage(ErrorCodeEnum.OK);
    }
}
