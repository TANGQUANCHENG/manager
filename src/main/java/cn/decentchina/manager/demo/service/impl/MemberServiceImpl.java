package cn.decentchina.manager.demo.service.impl;

import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.common.enums.ErrorCodeEnum;
import cn.decentchina.manager.config.ApplicationContextProvider;
import cn.decentchina.manager.demo.dao.MemberDao;
import cn.decentchina.manager.demo.dto.MemberQueryDTO;
import cn.decentchina.manager.demo.entity.Member;
import cn.decentchina.manager.demo.service.MemberService;
import cn.decentchina.manager.demo.vo.MemberVO;
import cn.decentchina.manager.quartz.dao.QuartzConfigDao;
import cn.decentchina.manager.quartz.entity.QuartzConfig;
import cn.decentchina.manager.quartz.job.LockMemberJob;
import cn.decentchina.manager.quartz.util.CronUtil;
import cn.decentchina.manager.quartz.util.SchedulerUtil;
import cn.decentchina.manager.system.vo.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 唐全成
 * @date 2018-08-29
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;
    @Autowired
    private QuartzConfigDao quartzConfigDao;
    @Override
    public SimpleMessage insertMember(Member member) {
        if (memberDao.insertMember(member) < 1) {
            return new SimpleMessage(ErrorCodeEnum.ERROR);
        }
        QuartzConfig config = new QuartzConfig("lock member",
                LockMemberJob.class.getName(),
                "MEMBER",
                CronUtil.getCron(new Date(System.currentTimeMillis() + 1000 * 60 * 10)),
                0);
        quartzConfigDao.insertConfig(config);
        SchedulerUtil.createScheduler(config, ApplicationContextProvider.getApplicationContext());
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

    @Override
    public SimpleMessage updateMember(Member member) {
        if (memberDao.updateMember(member) < 1) {
            return new SimpleMessage(ErrorCodeEnum.ERROR);
        }
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

    @Override
    public SimpleMessage deleteMember(Integer id) {
        if (memberDao.deleteMember(id) < 1) {
            return new SimpleMessage(ErrorCodeEnum.ERROR);
        }
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

    @Override
    public Page<MemberVO> queryList(Page page, MemberQueryDTO dto) {
        PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        return new Page<>(memberDao.queryList(dto));
    }

    @Override
    public List<String> queryDetail(Integer id) {
        //todo 模拟数据库详情（日志）列表信息查询
        List<String> logs = new ArrayList<>();
        logs.add("2018-08-08 12:00:00 admin delete ");
        logs.add("2018-08-08 12:01:00 admin add ");
        logs.add("2018-08-08 12:02:00 admin update ");
        logs.add("2018-08-08 12:03:00 admin delete ");
        return logs;
    }


}
