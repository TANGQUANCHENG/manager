package cn.decentchina.manager.demo.controller;

import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.common.enums.ErrorCodeEnum;
import cn.decentchina.manager.demo.dto.MemberQueryDTO;
import cn.decentchina.manager.demo.entity.Member;
import cn.decentchina.manager.demo.service.MemberService;
import cn.decentchina.manager.demo.vo.MemberVO;
import cn.decentchina.manager.system.vo.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 唐全成
 * @date 2018-08-29
 */
//@Controller
@RestController
@RequestMapping("member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    /**
     * 跳转页面(类注解为@Controller时应使用此方式)
     *
     * @return
     */
    @RequestMapping("")
    public ModelAndView member() {
        ModelAndView md = new ModelAndView("member/page");
        md.addObject("genderEnum", Member.GenderEnum.getMap());
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
        map.put("genderEnum", Member.GenderEnum.getMap());
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
    public SimpleMessage addMember(Member member) {
        if (StringUtils.isBlank(member.getName())) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "会员名称不能为空");
        }
        if (member.getAge() == null) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "会员年龄不能为空");
        }
        if (member.getGender() == null) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "会员性别不能为空");
        }
        return memberService.insertMember(member);
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
}
