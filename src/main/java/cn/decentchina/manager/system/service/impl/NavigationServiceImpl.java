package cn.decentchina.manager.system.service.impl;

import cn.decentchina.manager.system.dao.NavigationDao;
import cn.decentchina.manager.system.dao.RoleNavRelationDao;
import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.system.entity.Navigation;
import cn.decentchina.manager.common.enums.ErrorCodeEnum;
import cn.decentchina.manager.system.service.FilterChainDefinitionsService;
import cn.decentchina.manager.system.service.NavigationService;
import cn.decentchina.manager.system.vo.NavigationVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 唐全成
 * @date 2018-05-18
 */
@Service
public class NavigationServiceImpl implements NavigationService {


    @Autowired
    private NavigationDao navigationDao;
    @Autowired
    private RoleNavRelationDao roleNavRelationDao;

    @Autowired
    private FilterChainDefinitionsService filterChainDefinitionsService;

    @Override
    public List<NavigationVO> queryAll() {
        List<NavigationVO> navigationVOS = navigationDao.queryAll();
        navigationVOS.forEach(n -> n.setSelected(false));
        return navigationVOS;
    }

    @Override
    public SimpleMessage addNavigation(Navigation navigation) {

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

    @Override
    public SimpleMessage updateNavigation(Navigation navigation) {
        if (StringUtils.isBlank(navigation.getFunctionName())) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "功能名称不能为空");
        }
        if (navigationDao.updateNavigation(navigation) < 1) {
            return new SimpleMessage(ErrorCodeEnum.ERROR);
        }
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

    @Override
    public SimpleMessage deleteNavigation(Integer id) {
        if (navigationDao.deleteNavigation(id) < 1) {
            return new SimpleMessage(ErrorCodeEnum.ERROR);
        }
        filterChainDefinitionsService.reloadFilterChains();
        roleNavRelationDao.deleteByNavId(id);
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

    @Override
    public List<Navigation> queryFirstLevel() {
        return navigationDao.queryParents();
    }

    @Override
    public SimpleMessage updateStatus(Integer id, Boolean status) {
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
