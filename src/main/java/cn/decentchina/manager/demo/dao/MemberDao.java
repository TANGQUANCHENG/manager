package cn.decentchina.manager.demo.dao;

import cn.decentchina.manager.demo.dao.provider.MemberDaoProvider;
import cn.decentchina.manager.demo.dto.MemberQueryDTO;
import cn.decentchina.manager.demo.entity.Member;
import cn.decentchina.manager.demo.vo.MemberVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 唐全成
 * @date 2018-08-29
 */
@Mapper
@Repository
public interface MemberDao {

    /**
     * 新增
     *
     * @param member
     * @return
     */
    @Insert("insert into tbl_member (" +
            "member_name ," +
            "age ," +
            "gender," +
            "avatar,"+
            "gmt_create) " +
            "values( " +
            "#{member.name}," +
            "#{member.age}," +
            "#{member.gender}," +
            "#{member.avatar}," +
            "now())")
    int insertMember(@Param("member") Member member);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Delete("delete from tbl_member where id=#{id}")
    int deleteMember(@Param("id") Integer id);

    /**
     * 修改
     *
     * @param member
     * @return
     */
    @Update("update tbl_member set " +
            " member_name=#{member.name} ," +
            " age=#{member.age}," +
            " gender=#{member.gender} ," +
            " gmt_modified=now() " +
            " where id=#{member.id}")
    int updateMember(@Param("member") Member member);

    /**
     * 信息分页查询
     *
     * @param memberQueryDTO 搜索条件
     * @return
     */
    @SelectProvider(type = MemberDaoProvider.class, method = "queryMemberList")
    List<MemberVO> queryList(@Param("dto") MemberQueryDTO memberQueryDTO);

    /**
     * 用于选中下载
     *
     * @param ids
     * @return
     */
    @Select("  select id," +
            " member_name as name," +
            " age," +
            " gender," +
            " gmt_create as createTime," +
            " gmt_modified as modifyTime" +
            " from tbl_member " +
            " where id in (${ids}) ")
    List<MemberVO> queryByIds(@Param("ids") String ids);

    /**
     * 查询全部
     * @return
     */
    @Select("  select id," +
            " member_name as name," +
            " age," +
            " gender," +
            " gmt_create as createTime," +
            " gmt_modified as modifyTime" +
            " from tbl_member " )
    List<Member> queryAll();
}
