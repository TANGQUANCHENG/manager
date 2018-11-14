package cn.decentchina.manager.quartz.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 定时器任务表映射
 * <p>
 * * create table tbl_quartz_config
 * (
 * id int auto_increment comment '自增唯一主键'
 * primary key,
 * job_name varchar(30) not null comment '任务名称',
 * full_entity varchar(100) null comment '任务对应实体类',
 * group_name varchar(30) null comment '任务组名',
 * cron_time varchar(20) null comment 'cron表达式',
 * job_status tinyint(1) default '0' null comment '任务状态,0关闭,1正常',
 * gmt_create datetime not null comment '创建时间',
 * gmt_modified datetime null comment '修改时间',
 * constraint tbl_quartz_config_id_uindex
 * unique (id)
 * )
 * engine=InnoDB
 * ;
 * 示例:
 * INSERT INTO shouka.tbl_quartz_config (id, job_name, full_entity, group_name, cron_time, job_status, gmt_create,
 * gmt_modified) VALUES (1, '卡密查询', 'cn.decentchina.shoukatask.jobs.QueryOrderJob', 'queryOrder', '0/30 * * * * ?',
 * 0, '2018-03-15 20:22:48', '2018-03-15 20:22:48');
 *
 * @author wangyx
 */
@Data
public class QuartzConfig {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * job名称
     */
    private String jobName;
    /**
     * 全类名
     */
    private String fullEntity;
    /**
     * 组名称
     */
    private String groupName;
    /**
     * cron表达式
     */
    private String cronTime;
    /**
     * 状态 0为关闭 1为开启
     */
    private Integer jobStatus;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime modifyTime;

    public QuartzConfig() {
    }

    public QuartzConfig(String jobName, String fullEntity, String groupName, String cronTime, Integer jobStatus) {
        this.jobName = jobName;
        this.fullEntity = fullEntity;
        this.groupName = groupName;
        this.cronTime = cronTime;
        this.jobStatus = jobStatus;
    }
}
