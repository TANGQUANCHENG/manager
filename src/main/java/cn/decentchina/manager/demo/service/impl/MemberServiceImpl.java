package cn.decentchina.manager.demo.service.impl;

import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.common.enums.ErrorCodeEnum;
import cn.decentchina.manager.common.util.UploadUtil;
import cn.decentchina.manager.config.CommonConfig;
import cn.decentchina.manager.config.Constant;
import cn.decentchina.manager.demo.dao.MemberDao;
import cn.decentchina.manager.demo.dto.MemberQueryDTO;
import cn.decentchina.manager.demo.entity.Member;
import cn.decentchina.manager.demo.service.MemberService;
import cn.decentchina.manager.demo.vo.MemberVO;
import cn.decentchina.manager.system.util.PoiUtil;
import cn.decentchina.manager.system.vo.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 唐全成
 * @date 2018-08-29
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Resource
    private MemberDao memberDao;
    @Resource
    private CommonConfig commonConfig;

    /**
     * 新增
     *
     * @param member  成员
     * @param imgFile 图片文件
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     */
    @Override
    public SimpleMessage insertMember(Member member, MultipartFile imgFile) {
        String path = this.getClass().getResource("/").getPath();
        String result = UploadUtil.uploadFile(imgFile, path + commonConfig.getUploadPath());
        if (StringUtils.equals(Constant.FAIL, result)) {
            return new SimpleMessage(ErrorCodeEnum.ERROR, "上传失败");
        }
        member.setAvatar(StringUtils.replace(result, path + "static", ""));
        if (memberDao.insertMember(member) < 1) {
            return new SimpleMessage(ErrorCodeEnum.ERROR);
        }
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

    /**
     * 修改
     *
     * @param member 成员
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     */
    @Override
    public SimpleMessage updateMember(Member member) {
        if (memberDao.updateMember(member) < 1) {
            return new SimpleMessage(ErrorCodeEnum.ERROR);
        }
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

    /**
     * 删除
     *
     * @param id 成员id
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     */
    @Override
    public SimpleMessage deleteMember(Integer id) {
        if (memberDao.deleteMember(id) < 1) {
            return new SimpleMessage(ErrorCodeEnum.ERROR);
        }
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

    /**
     * 查询列表
     *
     * @param page 分页信息
     * @param dto  查询条件
     * @return : cn.decentchina.manager.system.vo.Page<cn.decentchina.manager.demo.vo.MemberVO>
     */
    @Override
    public Page<MemberVO> queryList(Page page, MemberQueryDTO dto) {
        PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        return new Page<>(memberDao.queryList(dto));
    }

    /**
     * 查询详情
     *
     * @param id 会员id
     * @return : java.util.List<java.lang.String>
     */
    @Override
    public List<String> queryDetail(Integer id) {
        // 模拟数据库详情（日志）列表信息查询
        List<String> logs = new ArrayList<>();
        logs.add("2018-08-08 12:00:00 admin delete ");
        logs.add("2018-08-08 12:01:00 admin add ");
        logs.add("2018-08-08 12:02:00 admin update ");
        logs.add("2018-08-08 12:03:00 admin delete ");
        return logs;
    }

    /**
     * 导入成员
     *
     * @param file excel文件
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws IOException 异常
     */
    @Override
    public SimpleMessage fileImport(MultipartFile file) throws IOException {
        List<String[]> arr = PoiUtil.readExcel(file);
        for (String[] strings : arr) {
            Member m = new Member();
            m.setName(strings[0]);
            m.setAge(strings[1]);
            memberDao.insertMember(m);
        }
        return new SimpleMessage(ErrorCodeEnum.OK);
    }
}
