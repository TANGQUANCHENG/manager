package cn.decentchina.manager.demo.controller;

import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.common.enums.ErrorCodeEnum;
import cn.decentchina.manager.common.util.UploadUtil;
import cn.decentchina.manager.demo.dto.MemberQueryDTO;
import cn.decentchina.manager.demo.entity.Member;
import cn.decentchina.manager.demo.enums.GenderEnum;
import cn.decentchina.manager.demo.service.CellService;
import cn.decentchina.manager.demo.service.MemberService;
import cn.decentchina.manager.demo.vo.DataCell;
import cn.decentchina.manager.demo.vo.MemberVO;
import cn.decentchina.manager.handler.AppExceptionHandler;
import cn.decentchina.manager.system.vo.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 唐全成
 * @date 2018-08-29
 */
@RestController
@RequestMapping("member")
public class MemberController  {
    @Resource
    private MemberService memberService;
    @Resource
    private CellService cellService;

    /**
     * 跳转页面(类注解为@Controller时应使用此方式)
     *
     * @return : org.springframework.web.servlet.ModelAndView
     */
    @RequestMapping("")
    public ModelAndView member() {
        ModelAndView md = new ModelAndView("member/page");
        md.addObject("genderEnum", GenderEnum.getMap());
        return md;
    }

    /**
     * 跳转页面(类注解为@Controller时生效)
     *
     * @param model 域对象
     * @return : java.lang.String
     */
    @RequestMapping(path = {"/", "/page.html"})
    public String member(Model model) {
        model.addAttribute("genderEnum", GenderEnum.getMap());
        return "member/page";
    }

    /**
     * 查询列表
     *
     * @param page     分页信息
     * @param queryDTO 查询条件
     * @return : cn.decentchina.manager.system.vo.Page<cn.decentchina.manager.demo.vo.MemberVO>
     */
    @RequestMapping("/list")
    public Page<MemberVO> queryPage(Page page, MemberQueryDTO queryDTO) throws Exception {
        return memberService.queryList(page, queryDTO);
    }

    /**
     * 新增
     *
     * @param member  成员
     * @param imgFile 图片文件
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     */
    @RequestMapping("/add")
    public SimpleMessage addMember(Member member, @RequestParam("imgFile") MultipartFile imgFile) {
        if (StringUtils.isBlank(member.getName())) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "会员名称不能为空");
        }
        if (member.getAge() == null) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "会员年龄不能为空");
        }
        if (member.getGender() == null) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "会员性别不能为空");
        }

        String fileName = imgFile.getOriginalFilename();
        if (!UploadUtil.checkSuffix(fileName)) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "上传格式不符");
        }
        if (!UploadUtil.checkSize(imgFile)) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "上传大小不符");
        }

        return memberService.insertMember(member, imgFile);
    }

    /**
     * 修改
     *
     * @param member 成员
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     */
    @RequestMapping("/update")
    public SimpleMessage updateMember(Member member) {
        if (StringUtils.isBlank(member.getName())) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "会员名称不能为空");
        }
        if (member.getAge() == null) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "会员年龄不能为空");
        }
        if (member.getGender() == null) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "会员性别不能为空");
        }
        return memberService.updateMember(member);
    }

    /**
     * 删除
     *
     * @param id 成员id
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     */
    @RequestMapping("/delete/{id}")
    public SimpleMessage deleteMember(@PathVariable Integer id) {
        return memberService.deleteMember(id);
    }

    /**
     * 查询详情
     *
     * @param id 会员id
     * @return : java.util.List<java.lang.String>
     */
    @RequestMapping("/queryDetail/{id}")
    public List<String> queryDetail(@PathVariable Integer id) {
        return memberService.queryDetail(id);
    }


    /**
     * 导入成员
     *
     * @param file excel文件
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws IOException 异常
     */
    @RequestMapping("/import")
    public SimpleMessage importMembers(@RequestParam("file") MultipartFile file) throws IOException {
        if (file == null) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "请选择文件");
        }
        return memberService.fileImport(file);
    }

    /**
     * 以图片形式返回信息
     *
     * @param response 响应
     */
    @RequestMapping("/testDrawImg")
    public void testDrawImg(HttpServletResponse response) throws IOException {
        String[] heads = {"品牌","产品", "正常库存", "冻结库存", "回收量"};
        List<DataCell> list = new ArrayList<>(30);
        DataCell cell = new DataCell("星巴克", "中杯通兑伙伴券", " 300 ", "20" ,"20");
        DataCell cell1 = new DataCell("星巴克", "大杯饮品券", " 200 ", "500","20");
        DataCell cell2 = new DataCell("星巴克", "中杯全国券", " 200 ", "33","20");
        DataCell cell3 = new DataCell("肯德基", "蛋挞2只", " 2000 ", "10","20");
        DataCell cell4 = new DataCell("肯德基", "早餐粥", " 200 ", "53","20");
        DataCell cell5 = new DataCell("肯德基", "100元代金券", " 24 ", "78","20");
        DataCell cell6 = new DataCell("京东E卡", "京东E卡1000元", " 3 ", "5","20");
        list.add(cell);
        list.add(cell1);
        list.add(cell2);
        list.add(cell3);
        list.add(cell4);
        list.add(cell5);
        list.add(cell6);
        list.add(cell);
        list.add(cell1);
        list.add(cell2);
        list.add(cell3);
        list.add(cell4);
        list.add(cell5);
        list.add(cell6);
        list.add(cell);
        list.add(cell1);
        list.add(cell2);
        list.add(cell3);
        list.add(cell4);
        list.add(cell5);
        list.add(cell6);
        cellService.drawTable(list, heads, response);
    }
}
