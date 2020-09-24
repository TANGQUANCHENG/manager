package cn.decentchina.manager.demo.dao;

import cn.decentchina.manager.demo.entity.AqyCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author 唐全成
 * @date 2019-03-30
 */
@Repository
@Mapper
public interface AqyCodeDao {

    /**
     * 修改
     * @param id
     * @param remark
     * @return
     */
    @Update("update tbl_aqy_code set remark=#{remark}, status=1 , processing_time =now() where id=#{id}")
    int update(@Param("id") Integer id, @Param("remark") String remark);

    /**
     * 查询未处理信息
     * @param amount
     * @return
     */
    @Select(" select id,code,status from tbl_aqy_code where status=0 limit #{amount}")
    AqyCode queryUnDealed(@Param("amount") Integer amount);

    /**
     * 修改
     * @param id
     * @return
     */
    @Update("update tbl_aqy_code set status=2 where id=#{id}")
    int pause(@Param("id") Integer id);
}
