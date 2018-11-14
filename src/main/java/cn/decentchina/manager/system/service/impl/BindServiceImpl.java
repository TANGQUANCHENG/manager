package cn.decentchina.manager.system.service.impl;

import cn.decentchina.manager.system.dao.RoleNavRelationDao;
import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.system.entity.RoleNavRelation;
import cn.decentchina.manager.common.enums.ErrorCodeEnum;
import cn.decentchina.manager.system.service.AdminService;
import cn.decentchina.manager.system.service.BindService;
import cn.decentchina.manager.system.service.FilterChainDefinitionsService;
import cn.decentchina.manager.system.vo.AdminVO;
import cn.decentchina.manager.system.vo.NavigationVO;
import cn.decentchina.manager.system.vo.TreeVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 唐全成
 * @date 2018-05-19
 */
@Slf4j
@Service
public class BindServiceImpl implements BindService {
    @Resource
    private SqlSessionFactory sessionFactory;

    @Resource
    private RoleNavRelationDao roleNavRelationDao;
    @Resource
    private FilterChainDefinitionsService filterChainDefinitionsService;
    @Resource
    private AdminService adminService;

    private static final String DUPLICATE_ENTRY = "Duplicate entry";

    /**
     * 绑定
     *
     * @param navs  资源id
     * @param roles 角色id
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    @Override
    public SimpleMessage batchBind(Integer[] navs, Integer[] roles) throws Exception {

        AdminVO currentAdmin = adminService.getCurrentAdmin();
        if (!currentAdmin.getSuperAdmin()) {
            return new SimpleMessage(ErrorCodeEnum.ERROR, "该操作允许超级管理员执行");
        }

        SqlSession session = sessionFactory.openSession(ExecutorType.BATCH, false);
        try {
            RoleNavRelationDao dao = session.getMapper(RoleNavRelationDao.class);
            for (Integer i : roles) {
                roleNavRelationDao.deleteByRole(i);
            }
            List<RoleNavRelation> roleNavRelations = buildRelations(navs, roles);
            for (RoleNavRelation r : roleNavRelations) {
                dao.addBinding(r);
            }
            session.commit();
            session.clearCache();
        } catch (Exception e) {
            /*
             * 数据库设定唯一索引（navigationId，roleId）
             * 若该异常为违反唯一索引的异常则忽略
             */
            if (StringUtils.indexOf(e.getLocalizedMessage(), DUPLICATE_ENTRY) == -1) {
                log.error("batchBind error:", e);
                session.rollback();
                return new SimpleMessage(ErrorCodeEnum.ERROR);
            } else {
                filterChainDefinitionsService.reloadFilterChains();
                return new SimpleMessage(ErrorCodeEnum.OK);
            }
        } finally {
            session.close();
        }
        filterChainDefinitionsService.reloadFilterChains();
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

    /**
     * 解除
     *
     * @param relationIds 关联id
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    @Override
    public SimpleMessage relieveBind(Integer[] relationIds) throws Exception {

        AdminVO currentAdmin = adminService.getCurrentAdmin();
        if (!currentAdmin.getSuperAdmin()) {
            return new SimpleMessage(ErrorCodeEnum.ERROR, "该操作允许超级管理员执行");
        }

        if (relationIds == null) {
            return new SimpleMessage(ErrorCodeEnum.NO, "数据异常");
        }
        SqlSession session = sessionFactory.openSession(ExecutorType.BATCH, false);
        try {
            RoleNavRelationDao dao = session.getMapper(RoleNavRelationDao.class);
            for (Integer id : relationIds) {
                dao.delete(id);
            }
            session.commit();
            session.clearCache();
        } catch (Exception e) {
            session.rollback();
            return new SimpleMessage(ErrorCodeEnum.ERROR);
        } finally {
            session.close();
        }
        filterChainDefinitionsService.reloadFilterChains();
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

    /**
     * 根据角色查询菜单
     *
     * @param roleId 角色id
     * @return : cn.decentchina.manager.system.vo.TreeVO
     */
    @Override
    public TreeVO queryByRole(Integer roleId) {
        List<NavigationVO> navigationVOS = roleNavRelationDao.queryByRole(roleId);
        return new TreeVO(navigationVOS.size(), navigationVOS);
    }

    /**
     * 构建关联关系
     *
     * @param navs  资源id
     * @param roles 角色id
     * @return : java.util.List<cn.decentchina.manager.system.entity.RoleNavRelation>
     */
    private List<RoleNavRelation> buildRelations(Integer[] navs, Integer[] roles) {
        List<RoleNavRelation> list = new ArrayList<>(32);
        for (Integer i : navs) {
            for (Integer j : roles) {
                RoleNavRelation roleNavRelation = new RoleNavRelation(j, i, LocalDateTime.now());
                list.add(roleNavRelation);
            }
        }
        return list;
    }
}
