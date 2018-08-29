package cn.decentchina.manager.demo.service.impl;

import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.common.enums.ErrorCodeEnum;
import cn.decentchina.manager.demo.dao.MemberDao;
import cn.decentchina.manager.demo.dto.MemberQueryDTO;
import cn.decentchina.manager.demo.entity.Member;
import cn.decentchina.manager.demo.service.MemberService;
import cn.decentchina.manager.demo.vo.MemberVO;
import cn.decentchina.manager.system.vo.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 唐全成
 * @date 2018-08-29
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public SimpleMessage insertMember(Member member) {
        if (memberDao.insertMember(member) < 1) {
            return new SimpleMessage(ErrorCodeEnum.ERROR);
        }
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
}
