package cn.decentchina.manager.demo.service;

import cn.decentchina.manager.demo.vo.DataCell;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 唐全成
 * @date 2018-10-12
 */
public interface CellService {

    /**
     * 将数据生成图片
     * @param dataCellList
     * @param heads
     * @param response
     * @throws IOException
     */
    void drawTable(List<DataCell> dataCellList,String[] heads, HttpServletResponse response) throws IOException;

}
