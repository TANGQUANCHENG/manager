package cn.decentchina.manager.demo.service;

import cn.decentchina.manager.demo.dao.AqyCodeDao;
import cn.decentchina.manager.demo.entity.AqyCode;
import cn.decentchina.manager.system.util.HttpClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author 唐全成
 * @date 2019-03-30
 */
@Slf4j
@Service
public class AqyService {

    @Autowired
    private AqyCodeDao aqyCodeDao;

    public void processor() throws IOException {
        AqyCode aqyCode = aqyCodeDao.queryUnDealed(1);
        if(aqyCode==null){
            return;
        }
        try {
            log.info("dealing :[{}]",aqyCode);
            String url="http://172.16.0.165:8070/getstate?card="+aqyCode.getCode();
            String result = HttpClient.postString(url, "","utf-8",4500, 4500);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(result);
            String errcode = jsonNode.get("msg").toString();
            errcode= StringUtils.replace(errcode,"\"","");
            aqyCodeDao.update(aqyCode.getId(), errcode);
        }catch (Exception e){
            aqyCodeDao.pause(aqyCode.getId());
            log.info("首次异常，跳过:[{}]",aqyCode);
        }
    }
}
