package cn.decentchina.manager.demo.service.impl;

import cn.decentchina.manager.demo.service.CellService;
import cn.decentchina.manager.demo.vo.DataCell;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author 唐全成
 * @date 2018-10-12
 */
@Service
public class CellServiceImpl implements CellService {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public void drawTable(List<DataCell> dataCellList,String[] heads, HttpServletResponse response) throws IOException {
        int trHeight=34;
        int padding=20;
        int imgWidth=heads.length*120+padding*2;
        int imgHeight= (dataCellList.size()+2)*trHeight+padding*2+dataCellList.size()+1;

        String path=this.getClass().getResource("/").getPath();
        File file = new File(path+"/temp.jpg");

        BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, imgWidth, imgHeight);
        //画背景
        graphics.setColor(new Color(0, 112, 192));
        //画标题
        graphics.drawString("2018-10-12",imgWidth/2-50,padding*2);
        //画表头
        for (int i=0;i<heads.length;i++) {
            graphics.drawString(heads[i],i*120+padding*2,padding*2+trHeight);
        }
        graphics.drawLine(padding,padding+trHeight*2,imgWidth-padding,padding+trHeight*2);
        //画表格数据
        for(int i=2;i<=dataCellList.size()+1;i++){
            DataCell cell = dataCellList.get(i -2);
            graphics.drawString(cell.getName(),0*120+padding*2,i*36+ padding*2);
            graphics.drawString(cell.getAmount(),1*120+padding*2,i*36+padding*2);
            graphics.drawString(cell.getBudget(),2*120+padding*2,i*36+padding*2);
            graphics.drawString(cell.getRate(),3*120+padding*2,i*36+padding*2);
        }
        ImageIO.write(image, "jpg", file);
        long fileLength = file.length();
        response.setContentType("application/msdownload");
        response.setHeader("Content-Length", String.valueOf(fileLength));
        response.setHeader("Content-Disposition", "attachment;filename=" + "temp.jpg");
        OutputStream out = response.getOutputStream();
        byte[] temp = FileUtils.readFileToByteArray(file);
        IOUtils.write(temp, out);
        out.flush();
        IOUtils.closeQuietly(out);
    }
}
