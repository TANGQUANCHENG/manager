package cn.decentchina.manager.system.service.impl;

import cn.decentchina.manager.system.dao.NavigationDao;
import cn.decentchina.manager.system.dao.RoleNavRelationDao;
import cn.decentchina.manager.system.service.ShiroChainService;
import cn.decentchina.manager.system.vo.NavigationVO;
import cn.decentchina.manager.system.vo.ShiroChainVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 唐全成
 * @date 2018-05-22
 */
@Service
public class ShiroChainServiceImpl implements ShiroChainService {

    @Autowired
    private RoleNavRelationDao roleNavRelationDao;

    @Autowired
    private NavigationDao navigationDao;
    @Override
    public List<ShiroChainVO> queryShiroChain() {
        return roleNavRelationDao.queryShiroChain();
    }

    @Override
    public List<NavigationVO> queryByRole(Integer roleId) {
        return roleNavRelationDao.queryByRole(roleId);
    }

    @Override
    public List<NavigationVO> queryAll() {
        return navigationDao.queryAll();
    }
}
