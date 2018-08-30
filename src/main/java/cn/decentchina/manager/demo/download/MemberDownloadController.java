package cn.decentchina.manager.demo.download;

import cn.decentchina.manager.demo.dto.MemberQueryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 唐全成
 * @date 2018-08-29
 */
@Slf4j
@RestController
@RequestMapping("memberDownload")
public class MemberDownloadController {

    @Autowired
    private MemberDownloadService memberDownloadService;

    /**
     * 条件下载会员列表信息
     * @param dto
     * @param response
     * @return
     */
    @RequestMapping("/query")
    public String queryMemberDownload(MemberQueryDTO dto, HttpServletResponse response) {
        try {
            ServletOutputStream out = response.getOutputStream();
            String fileName = new String(("会员列表 "
                    + new SimpleDateFormat("yyyy-MM-dd").format(new Date())).getBytes(), "UTF-8");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
            String[] titles = {"姓名", "年龄", "性别", "创建时间"};
            memberDownloadService.download(titles, out, dto);
        } catch (IOException e) {
            log.error("下载卖券订单失败", e);
            return "error";
        }
        return "success";
    }

    /**
     * 选中下载会员列表信息
     * @param ids
     * @param response
     * @return
     */
    @RequestMapping("/choose")
    public String chooseMemberDownload(Integer[] ids, HttpServletResponse response) {
        try {
            ServletOutputStream out = response.getOutputStream();
            String fileName = new String(("会员列表 "
                    + new SimpleDateFormat("yyyy-MM-dd").format(new Date())).getBytes(), "UTF-8");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
            String[] titles = {"姓名", "年龄", "性别", "创建时间"};
            memberDownloadService.chooseDownload(titles, out, ids);
        } catch (IOException e) {
            log.error("下载卖券订单失败", e);
            return "error";
        }
        return "success";
    }


}
