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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 唐全成
 * @date 2018-08-29
 */
//@Controller
@RestController
@RequestMapping("member")
public class MemberController extends AppExceptionHandler{
    @Autowired
    private MemberService memberService;

    @Autowired
    private CellService cellService;
    /**
     * 跳转页面(类注解为@Controller时应使用此方式)
     *
     * @return
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
     * @param map
     * @return
     */
    @RequestMapping(path = {"/", "/page.html"})
    public String member(Map<String, Object> map) {
        map.put("genderEnum", GenderEnum.getMap());
        return "member/page";
    }

    /**
     * 查询列表
     *
     * @return
     */
    @RequestMapping("/list")
    public Page<MemberVO> queryPage(Page page, MemberQueryDTO queryDTO) {
        return memberService.queryList(page, queryDTO);
    }

    /**
     * 新增
     *
     * @param member
     * @return
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

        return memberService.insertMember(member,imgFile);
    }

    /**
     * 修改
     *
     * @param member
     * @return
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
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public SimpleMessage deleteMember(@PathVariable Integer id) {
        return memberService.deleteMember(id);
    }

    /**
     * demo 根据会员id请求
     * @param id
     * @return
     */
    @RequestMapping("/queryDetail/{id}")
    public List<String> queryDetail(@PathVariable Integer id){
        return memberService.queryDetail(id);
    }


    @RequestMapping("/import")
    public SimpleMessage importMembers(@RequestParam("file") MultipartFile file) throws IOException {
        if(file==null){
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS,"请选择文件");
        }
        return memberService.fileImport(file);
    }

    @RequestMapping("/testDrawImg")
    public void testDrawImg(HttpServletResponse response) throws IOException {
        String[] heads={"店铺","销售额","预算","达成率"};
        List<DataCell> list=new ArrayList<>(30);
        DataCell cell=new DataCell("高新万达一店","7849.40"," 20000.00 ","39%");
        DataCell cell1=new DataCell("普利街店","7849.40"," 20000.00 ","39%");
        DataCell cell2=new DataCell("汇隆广场店","7849.40"," 20000.00 ","39%");
        DataCell cell3=new DataCell("滨河码头店","7849.40"," 20000.00 ","39%");
        DataCell cell4=new DataCell("滨河物流店","7849.40"," 20000.00 ","39%");
        DataCell cell5=new DataCell("华信店","7849.40"," 20000.00 ","39%");
        DataCell cell6=new DataCell("大明湖店","7849.40"," 20000.00 ","39%");
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
        cellService.drawTable(list,heads,response);
    }
}
