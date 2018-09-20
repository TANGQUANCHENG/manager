package cn.decentchina.manager.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 唐全成
 * @date 2018-09-20
 */
@Data
@Component
@ConfigurationProperties(prefix = "common-config")
public class CommonConfig {

    /**
     * 文件上传地址
     */
    private String uploadPath;
}
