package cn.decentchina.manager.system.dao;

import cn.decentchina.manager.system.entity.RoleNavRelation;
import cn.decentchina.manager.system.vo.NavigationVO;
import cn.decentchina.manager.system.vo.ShiroChainVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 唐全成
 * @date 2018-05-18
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
@Mapper
@Repository
public interface RoleNavRelationDao {

    /**
     * 新增关联关系
     *
     * @param binding 关联关系
     * @return int 影响行数
     */
    @Insert("insert into tbl_shiro_role_nav(" +
            "role_id," +
            "navigation_id," +
            "gmt_create" +
            ")values(" +
            "#{binding.roleId}," +
            "#{binding.navigationId}," +
            "now()" +
            ")")
    int addBinding(@Param("binding") RoleNavRelation binding);

    /**
     * 根据角色id查询与之关联的列表
     *
     * @param roleId 角色id
     * @return : java.util.List<cn.decentchina.manager.system.entity.RoleNavRelation>
     */
    @Select("SELECT " +
            "T.ID," +
            "T.role_id AS roleId," +
            "T.navigation_id AS navigationId," +
            "FROM tbl_shiro_role_nav T " +
            "WHERE " +
            "T.role_id=#{roleId} order by navigation_id ")
    List<RoleNavRelation> queryByRoleId(@Param("roleId") Integer roleId);

    /**
     * 获取某角色与某资源之间的关系
     *
     * @param navId  导航菜单id
     * @param roleId 角色id
     * @return : cn.decentchina.manager.system.entity.RoleNavRelation
     */
    @Select("SELECT T.ID " +
            " FROM tbl_shiro_role_nav T WHERE T.navigation_id=#{navId} and T.role_id=#{roleId}")
    RoleNavRelation queryByNavIdAndDutyId(@Param("navId") int navId, @Param("roleId") int roleId);

    /**
     * 根据id删除关联关系
     *
     * @param id 关系id
     * @return int 影响行数
     */
    @Delete("DELETE FROM tbl_shiro_role_nav WHERE ID=#{id} ")
    int delete(@Param("id") int id);


    /**
     * 根据资源和角色删除关联关系
     *
     * @param navId  导航菜单id
     * @param roleId 角色id
     * @return int 影响行数
     */
    @Delete("DELETE FROM tbl_shiro_role_nav  WHERE navigation_id=#{navId} and admin_role_id=#{roleId}")
    int deleteByNavIdAndDutyId(@Param("navId") int navId, @Param("roleId") int roleId);

    /**
     * 根据角色删除绑定关系
     *
     * @param roleId 角色id
     * @return int 影响行数
     */
    @Delete(" delete from tbl_shiro_role_nav where role_id=#{roleId}")
    int deleteByRole(@Param("roleId") Integer roleId);

    /**
     * 根据资源id删除关联关系
     *
     * @param navId 导航菜单id
     * @return int 影响行数
     */
    @Delete("delete from tbl_shiro_role_nav " +
            " where navigation_id in (" +
            " select id" +
            " from TBL_shiro_navigation" +
            " where id = #{navId}" +
            " or parent_id = #{navId})")
    int deleteByNavId(@Param("navId") int navId);

    /**
     * 根据角色查询其菜单树
     *
     * @param roleId 角色id
     * @return : java.util.List<cn.decentchina.manager.system.vo.NavigationVO>
     */
    @Select("SELECT " +
            " r.id as navId, " +
            " n.id as id, " +
            " n.url as url, " +
            " n.function_name AS functionName, " +
            " n.parent_id AS _parentId, " +
            " n.icon," +
            " n.parent_id AS parentId " +
            "FROM " +
            " tbl_shiro_navigation n, " +
            " tbl_shiro_role_nav r " +
            "WHERE " +
            " n.id = r.navigation_id " +
            "AND r.role_id = #{roleId} and n.is_available=1  order by n.sort")
    List<NavigationVO> queryByRole(@Param("roleId") Integer roleId);

    /**
     * 查询权限配置链
     *
     * @return : java.util.List<cn.decentchina.manager.system.vo.ShiroChainVO>
     */
    @Select(" SELECT" +
            " n.url as url," +
            " group_concat(r.`code`) as roles" +
            " FROM" +
            " tbl_shiro_role_nav rn" +
            " LEFT JOIN tbl_shiro_navigation n ON rn.navigation_id = n.id" +
            " LEFT JOIN tbl_shiro_role r ON rn.role_id = r.id" +
            " WHERE" +
            " n.parent_id IS NOT NULL and n.is_available=1 " +
            " GROUP BY" +
            " n.url")
    List<ShiroChainVO> queryShiroChain();
}
