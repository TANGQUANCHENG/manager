package cn.decentchina.manager.demo.service;

import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.demo.dto.MemberQueryDTO;
import cn.decentchina.manager.demo.entity.Member;
import cn.decentchina.manager.demo.vo.MemberVO;
import cn.decentchina.manager.system.vo.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author 唐全成
 * @date 2018-08-29
 */
public interface MemberService {
    /**
     * 新增
     *
     * @param member  成员
     * @param imgFile 图片文件
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     */
    SimpleMessage insertMember(Member member, MultipartFile imgFile);

    /**
     * 修改
     *
     * @param member 成员
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     */
    SimpleMessage updateMember(Member member);

    /**
     * 删除
     *
     * @param id 成员id
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     */
    SimpleMessage deleteMember(Integer id);

    /**
     * 查询列表
     *
     * @param page 分页信息
     * @param dto  查询条件
     * @return : cn.decentchina.manager.system.vo.Page<cn.decentchina.manager.demo.vo.MemberVO>
     */
    Page<MemberVO> queryList(Page page, MemberQueryDTO dto);

    /**
     * 查询详情
     *
     * @param id 会员id
     * @return : java.util.List<java.lang.String>
     */
    List<String> queryDetail(Integer id);

    /**
     * 导入成员
     *
     * @param file excel文件
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws IOException 异常
     */
    SimpleMessage fileImport(MultipartFile file) throws IOException;
}
