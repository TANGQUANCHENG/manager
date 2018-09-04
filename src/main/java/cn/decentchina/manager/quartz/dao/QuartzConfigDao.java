package cn.decentchina.manager.quartz.dao;

import cn.decentchina.manager.quartz.entity.QuartzConfig;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 定时器配置sql管理
 *
 * @author wangyx
 */
@Mapper
@Repository
public interface QuartzConfigDao {
    /**
     * 查询定时器配置
     *
     * @param status 状态
     * @return list
     */
    @Select("select id,job_name as jobName,full_entity as fullEntity,group_name as groupName,cron_time as cronTime," +
            "job_status as jobStatus,gmt_create as createTime,gmt_modified as modifyTime from tbl_quartz_config " +
            "where job_status = #{status}")
    List<QuartzConfig> queryByStatus(@Param("status") Integer status);

    /**
     * 新增数据
     *
     * @param quartzConfig
     * @return
     */
    @Insert("insert into tbl_quartz_config (" +
            "job_name," +
            "full_entity," +
            "group_name," +
            "cron_time," +
            "job_status," +
            "gmt_create" +
            ") values(" +
            "#{quartzConfig.jobName}," +
            "#{quartzConfig.fullEntity}," +
            "#{quartzConfig.groupName}," +
            "#{quartzConfig.cronTime}," +
            "#{quartzConfig.jobStatus}," +
            "now())")
    int insertConfig(@Param("quartzConfig") QuartzConfig quartzConfig);
}
