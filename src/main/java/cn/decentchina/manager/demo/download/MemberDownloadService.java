package cn.decentchina.manager.demo.download;

import cn.decentchina.manager.demo.dao.MemberDao;
import cn.decentchina.manager.demo.dto.MemberQueryDTO;
import cn.decentchina.manager.demo.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author 唐全成
 * @date 2018-08-29
 */
@Slf4j
@Service
public class MemberDownloadService {

    @Autowired
    private MemberDao memberDao;

    public void download(String[] titles, ServletOutputStream out, MemberQueryDTO dto) {
        List<MemberVO> memberVOS = memberDao.queryList(dto);
        writeResponseProcessor(memberVOS, titles, out);
    }

    public void chooseDownload(String[] titles, ServletOutputStream out, Integer[] ids) {
        List<MemberVO> memberVOS = memberDao.queryByIds(StringUtils.join(ids, ","));
        writeResponseProcessor(memberVOS, titles, out);
    }

    private void writeResponseProcessor(List<MemberVO> memberVOS, String[] titles, ServletOutputStream out) {
        try {
            // 第一步，创建一个workbook，对应一个Excel文件
            HSSFWorkbook workbook = new HSSFWorkbook();
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            HSSFSheet hssfSheet = workbook.createSheet("sheet1");
            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
            HSSFRow hssfRow = hssfSheet.createRow(0);
            // 第四步，创建单元格，并设置值表头 设置表头居中
            HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
            //居中样式
            hssfCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            HSSFCell hssfCell = null;
            for (int i = 0; i < titles.length; i++) {
                //列索引从0开始
                hssfCell = hssfRow.createCell(i);
                //列名1
                hssfCell.setCellValue(titles[i]);
                //列居中显示
                hssfCell.setCellStyle(hssfCellStyle);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (!memberVOS.isEmpty()) {
                int size = memberVOS.size();
                for (int i = 0; i < size; i++) {
                    hssfRow = hssfSheet.createRow(i + 1);
                    MemberVO memberVO = memberVOS.get(i);
                    hssfRow.createCell(0).setCellValue(memberVO.getName());
                    hssfRow.createCell(1).setCellValue(memberVO.getAge());
                    hssfRow.createCell(2).setCellValue(memberVO.getGender() == null
                            ? ""
                            : memberVO.getGender().getStr());
                    String createTime = memberVO.getCreateTime() == null ? "" : sdf.format(memberVO.getCreateTime());
                    hssfRow.createCell(3).setCellValue(createTime);
                }
            }
            // 第七步，将文件输出到客户端浏览器
            try {
                workbook.write(out);
                out.flush();
                out.close();
                log.info("会员信息导出成功，行数：{}", memberVOS.size());
            } catch (Exception e) {
                log.info("会员信息导出失败，{}", e);
            } finally {
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            log.info("会员信息导出成功失败，{}", e);
        }
    }
}
