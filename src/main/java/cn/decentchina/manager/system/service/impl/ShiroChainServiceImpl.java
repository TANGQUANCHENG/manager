package cn.decentchina.manager.system.service.impl;

import cn.decentchina.manager.system.dao.NavigationDao;
import cn.decentchina.manager.system.dao.RoleNavRelationDao;
import cn.decentchina.manager.system.service.ShiroChainService;
import cn.decentchina.manager.system.vo.NavigationVO;
import cn.decentchina.manager.system.vo.ShiroChainVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 唐全成
 * @date 2018-05-22
 */
@Service
public class ShiroChainServiceImpl implements ShiroChainService {

    @Resource
    private RoleNavRelationDao roleNavRelationDao;
    @Resource
    private NavigationDao navigationDao;

    /**
     * 查询权限树
     *
     * @return : java.util.List<cn.decentchina.manager.system.vo.ShiroChainVO>
     */
    @Override
    public List<ShiroChainVO> queryShiroChain() {
        return roleNavRelationDao.queryShiroChain();
    }

    /**
     * 根据角色查询导航
     *
     * @param roleId 角色id
     * @return : java.util.List<cn.decentchina.manager.system.vo.NavigationVO>
     */
    @Override
    public List<NavigationVO> queryByRole(Integer roleId) {
        return roleNavRelationDao.queryByRole(roleId);
    }

    /**
     * 查询全部
     *
     * @return : java.util.List<cn.decentchina.manager.system.vo.NavigationVO>
     */
    @Override
    public List<NavigationVO> queryAll() {
        return navigationDao.queryAll();
    }
}
