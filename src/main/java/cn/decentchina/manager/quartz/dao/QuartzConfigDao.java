package cn.decentchina.manager.quartz.dao;

import cn.decentchina.manager.quartz.entity.QuartzConfig;
import org.apache.ibatis.annotations.*;
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
     * @param status z状态
     * @return : java.util.List<cn.decentchina.manager.quartz.entity.QuartzConfig>
     */
    @Select("select id, job_name as jobName, full_entity as fullEntity, group_name as groupName, cron_time as cronTime, " +
            "job_status as jobStatus, gmt_create as createTime, gmt_modified as modifyTime from tbl_quartz_config " +
            "where job_status = #{status}")
    List<QuartzConfig> queryByStatus(@Param("status") Integer status);

    /**
     * 新增数据
     *
     * @param quartzConfig 定时任务配置
     * @return : int 影响行数
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

    /**
     * 根据id查询定时任务
     *
     * @param id 任务id
     * @return 任务信息
     */
    @Select("select id, job_name as jobName, full_entity as fullEntity, group_name as groupName, cron_time as cronTime, " +
            "job_status as jobStatus, gmt_create as createTime, gmt_modified as modifyTime from tbl_quartz_config " +
            "where id = #{id}")
    QuartzConfig queryById(@Param("id") Integer id);

    /**
     * 修改定时任务
     *
     * @param config 修改信息
     * @return 修改行数
     */
    @Update("<script> " +
            "update tbl_quartz_config " +
            " <set>\n" +
            "            gmt_modified = sysdate(),\n" +
            "            <if test=\"config.jobStatus != null \">\n" +
            "                job_status = #{config.jobStatus},\n" +
            "            </if>\n" +
            "            <if test=\"config.cronTime != null \">\n" +
            "                cron_time = #{config.cronTime},\n" +
            "            </if>\n" +
            "        </set> " +
            " where id = #{config.id} " +
            "</script> ")
    int modifyQuartz(@Param("config") QuartzConfig config);
}
