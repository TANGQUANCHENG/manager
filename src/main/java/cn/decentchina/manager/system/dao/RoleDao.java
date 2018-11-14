package cn.decentchina.manager.system.dao;

import cn.decentchina.manager.system.dao.provider.RoleDaoProvider;
import cn.decentchina.manager.system.entity.Admin;
import cn.decentchina.manager.system.entity.Role;
import cn.decentchina.manager.system.vo.RoleVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 唐全成
 * @date 2018-05-18
 */
@Mapper
@Repository
public interface RoleDao {

    /**
     * 插入角色
     * 插入后获取此角色的id，以用于为此角色绑定“用户管理功能”
     *
     * @param role 角色
     * @return int 影响行数
     */
    @Insert("insert into TBL_shiro_role(" +
            "name," +
            "code," +
            "creator_id," +
            "is_available," +
            "comment," +
            "gmt_create" +
            ")values(" +
            "#{role.name}," +
            "#{role.code}," +
            "#{role.creatorId}," +
            "#{role.available}," +
            "#{role.comment}," +
            "now())")
    @SelectKey(before = false, keyProperty = "role.id", resultType = Integer.class, statement =
            "SELECT LAST_INSERT_ID()")
    int addRole(@Param("role") Role role);

    /**
     * 查询所有角色
     *
     * @param searchText 查询条件
     * @return : java.util.List<cn.decentchina.manager.system.vo.RoleVO>
     */
    @SelectProvider(type = RoleDaoProvider.class, method = "queryAllRole")
    List<RoleVO> queryAllRole(@Param("searchText") String searchText);

    /**
     * 查询全部角色
     *
     * @return : java.util.List<cn.decentchina.manager.system.entity.Role>
     */
    @Select("select id ,name from tbl_shiro_role ")
    List<Role> queryAll();

    /**
     * 修改角色
     *
     * @param role 角色
     * @return int 影响行数
     */
    @Update("UPDATE TBL_shiro_role  SET" +
            " name=#{role.name}," +
            " code=#{role.code}," +
            " comment=#{role.comment}," +
            " gmt_modified=now()" +
            " WHERE id=#{role.id} "
    )
    int updateRole(@Param("role") Role role);


    /**
     * 修改角色状态
     *
     * @param role 角色
     * @return int 影响行数
     */
    @Update("UPDATE TBL_shiro_role  SET" +
            " is_available=#{role.available}," +
            " gmt_modified=now()" +
            " WHERE id=#{role.id} "
    )
    int updateAvailable(@Param("role") Role role);

    /**
     * 删除角色
     *
     * @param id 角色id
     * @return int 影响行数
     */
    @Delete("delete from TBL_shiro_role where id=#{id}")
    int deleteRole(@Param("id") int id);

    /**
     * 查询该角色下是否有管理员
     *
     * @param id 角色id
     * @return : java.util.List<cn.decentchina.manager.system.entity.Admin>
     */
    @Select("SELECT id ,`name` from tbl_shiro_admin where role_id=#{id}")
    List<Admin> hasAdmin(@Param("id") Integer id);

    /**
     * 根据角色编码查询
     *
     * @param code 角色编码
     * @return : cn.decentchina.manager.system.entity.Role
     */
    @Select("select id from tbl_shiro_role where code=#{code}")
    Role queryByCode(@Param("code") String code);
}
