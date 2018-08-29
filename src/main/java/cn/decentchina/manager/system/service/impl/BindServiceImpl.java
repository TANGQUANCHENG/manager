package cn.decentchina.manager.system.service.impl;

import cn.decentchina.manager.system.dao.RoleNavRelationDao;
import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.system.entity.RoleNavRelation;
import cn.decentchina.manager.common.enums.ErrorCodeEnum;
import cn.decentchina.manager.system.service.BindService;
import cn.decentchina.manager.system.service.FilterChainDefinitionsService;
import cn.decentchina.manager.system.vo.NavigationVO;
import cn.decentchina.manager.system.vo.TreeVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private RoleNavRelationDao roleNavRelationDao;

    @Autowired
    private FilterChainDefinitionsService filterChainDefinitionsService;

    @Override
    public SimpleMessage batchBind(Integer[] navs, Integer[] roles) {
        SqlSession session = sessionFactory.openSession(ExecutorType.BATCH, false);
        try {
            RoleNavRelationDao dao = session.getMapper(RoleNavRelationDao.class);
            for (Integer i:roles) {
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
            if (StringUtils.indexOf(e.getLocalizedMessage(), "Duplicate entry") == -1) {
                log.error("batchBind error:",e);
                session.rollback();
                return new SimpleMessage(ErrorCodeEnum.ERROR);
            }else {
                filterChainDefinitionsService.reloadFilterChains();
                return new SimpleMessage(ErrorCodeEnum.OK);
            }
        } finally {
            session.close();
        }
        filterChainDefinitionsService.reloadFilterChains();
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

    @Override
    public SimpleMessage relieveBind(Integer[] relationIds) {
        if(relationIds==null){
            return new SimpleMessage(ErrorCodeEnum.NO,"数据异常");
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

    @Override
    public TreeVO queryByRole(Integer roleId) {
        List<NavigationVO> navigationVOS = roleNavRelationDao.queryByRole(roleId);
        return new TreeVO(navigationVOS.size(),navigationVOS);
    }

    /**
     * 构建关联关系
     * @param navs
     * @param roles
     * @return
     */
    private List<RoleNavRelation> buildRelations(Integer[] navs, Integer[] roles){
        List<RoleNavRelation> list=new ArrayList<>(32);
        for (Integer i:navs) {
            for (Integer j:roles) {
                RoleNavRelation roleNavRelation=new RoleNavRelation(j,i,new Date());
                list.add(roleNavRelation);
            }
        }
        return list;
    }
}
