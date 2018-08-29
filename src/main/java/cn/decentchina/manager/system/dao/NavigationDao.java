package cn.decentchina.manager.system.dao;

import cn.decentchina.manager.system.entity.Navigation;
import cn.decentchina.manager.system.vo.NavigationVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 唐全成
 * @date 2018-05-18
 */
@Mapper
@Repository
public interface NavigationDao {

    /**
     * 新增
     *
     * @param a
     * @return
     */
    @Insert("insert into tbl_shiro_navigation(" +
            "function_name," +
            "parent_id," +
            "icon," +
            "sort," +
            "url," +
            "type," +
            "comment," +
            "is_available," +
            "gmt_create" +
            ")" +
            "values(" +
            "#{a.functionName}," +
            "#{a.parentId}," +
            "#{a.icon}," +
            "#{a.sort}," +
            "#{a.url}," +
            "#{a.type}," +
            "#{a.comment}," +
            "#{a.available}," +
            "now()" +
            ")")
    int addNavigation(@Param("a") Navigation a);

    /**
     * 查询单个资源信息
     *
     * @param id
     * @return
     */
    @Select("SELECT " +
            "T.ID," +
            "T.function_name AS functionName," +
            "T.parent_id AS _parentId," +
            "T.parent_id AS parentId," +
            "T.gmt_create AS createTime," +
            "T.sort," +
            "T.type," +
            "T.URL," +
            "T.comment," +
            "T.gmt_create AS updateTime," +
            "T.icon, " +
            "T.is_available as available " +
            "FROM TBL_SHIRO_NAVIGATION T " +
            "WHERE t.ID=#{id} ")
    NavigationVO queryId(@Param("id") Integer id);

    /**
     * 查询菜单栏的所有父节点
     *
     * @return
     */
    @Select("SELECT " +
            "T.ID," +
            "T.function_name AS functionName," +
            "T.parent_id AS parentId," +
            "T.sort," +
            "T.icon, " +
            "T.is_available as available " +
            "FROM TBL_SHIRO_NAVIGATION T " +
            "WHERE t.parent_id is null ORDER BY t.SORT,t.ID")
    List<Navigation> queryParents();

    /**
     * 查询菜单栏的所有子节点
     *
     * @param fatherId
     * @return
     */
    @Select("SELECT " +
            "T.ID," +
            "T.function_name AS functionName," +
            "T.parent_id AS _parentId," +
            "T.gmt_create AS createTime," +
            "T.sort," +
            "T.type," +
            "T.URL," +
            "T.comment," +
            "T.update_time AS updateTime," +
            "T.icon, " +
            "T.is_available as available  " +
            "FROM TBL_SHIRO_NAVIGATION T " +
            "WHERE t.parent_id=#{fatherId} ORDER BY t.SORT,t.ID")
    List<NavigationVO> querySubList(@Param("fatherId") Integer fatherId);


    /**
     * 修改资源管理数据
     *
     * @param a
     * @return
     */
    @Update("UPDATE TBL_SHIRO_NAVIGATION T " +
            "SET " +
            "T.FUNCTION_NAME=#{a.functionName}," +
            "T.SORT=#{a.sort}," +
            "T.URL=#{a.url,jdbcType=VARCHAR}," +
            "T.gmt_modified=now()," +
            "T.comment=#{a.comment,jdbcType=VARCHAR}," +
            "T.ICON=#{a.icon,jdbcType=VARCHAR} " +
            " WHERE T.ID=#{a.id} ")
    int updateNavigation(@Param("a") Navigation a);

    /**
     * 修改资源状态
     *
     * @param navId
     * @param available
     * @return
     */
    @Update("UPDATE TBL_SHIRO_NAVIGATION T SET T.gmt_modified=now(),T.is_available=#{available} WHERE T.ID=#{navId} and parent_id is not null ")
    int updateStatus(@Param("navId") Integer navId, @Param("available") Boolean available);


    /**
     * 删除选中的菜单
     *
     * @param id
     * @return
     */
    @Delete("DELETE FROM TBL_SHIRO_NAVIGATION  WHERE ID=#{id}  OR parent_id=#{id}")
    int deleteNavigation(@Param("id") Integer id);


    /**
     * 查询所有功能
     *
     * @return
     */
    @Select("SELECT " +
            "T.ID," +
            "T.function_name AS functionName," +
            "T.parent_id AS _parentId," +
            "T.parent_id AS parentId," +
            "T.gmt_create AS createTime," +
            "T.sort," +
            "T.type," +
            "T.URL," +
            "T.comment," +
            "T.gmt_modified AS updateTime," +
            "T.icon, " +
            "T.is_available as available " +
            "FROM TBL_SHIRO_NAVIGATION T  order by t.sort")
    List<NavigationVO> queryAll();
    /**
     * 查询菜单
     *
     * @return
     */
    @Select("SELECT " +
            "T.ID," +
            "T.function_name AS functionName," +
            "T.parent_id AS _parentId," +
            "T.gmt_create AS createTime," +
            "T.sort," +
            "T.type," +
            "T.URL," +
            "T.comment," +
            "T.gmt_modified AS updateTime," +
            "T.icon, " +
            "T.is_available as available " +
            "FROM TBL_SHIRO_NAVIGATION T where T.type='MENU' order by t.sort")
    List<NavigationVO> queryMenus();


}
