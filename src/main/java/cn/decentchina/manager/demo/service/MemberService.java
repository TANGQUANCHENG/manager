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
     * @param member
     * @param imgFile
     * @return
     */
    SimpleMessage insertMember(Member member, MultipartFile imgFile);

    /**
     * 修改
     * @param member
     * @return
     */
    SimpleMessage updateMember(Member member);

    /**
     * 删除
     * @param id
     * @return
     */
    SimpleMessage deleteMember(Integer id);

    /**
     * 查询列表数据
     *
     * @param page
     * @param dto
     * @return
     */
    Page<MemberVO> queryList(Page page, MemberQueryDTO dto);

    /**
     * 查看详情
     * @param id
     * @return
     */
    List<String> queryDetail(Integer id);

    /**
     * 导入Excel
     * @param file
     * @throws IOException
     * @return
     */
    SimpleMessage fileImport(MultipartFile file) throws IOException;
}
