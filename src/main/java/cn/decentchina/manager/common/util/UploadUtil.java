package cn.decentchina.manager.common.util;

import cn.decentchina.manager.config.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Arrays;

/**
 * @author 唐全成
 * @date 2018-09-20
 */
public class UploadUtil {

    /**
     * 允许上传的文件后缀
     */
    public static String[] ALLOW_FILE_SUFFIEX={"jpg","JPG","PNG","png"};

    /**
     * 最大上传
     */
    public static long MAXIMUM_FILE_SIZE=52428800L;


    public static String uploadFile(MultipartFile file, String uploadUrl){
        if (!file.isEmpty()) {
            String url="";
            try {
                // 这里只是简单例子，文件直接输出到项目路径下。
                // 实际项目中，文件需要输出到指定位置，需要在增加代码处理。
                // 还有关于文件格式限制、文件大小限制，详见：中配置。
                String fileName= uploadUrl+System.currentTimeMillis()+file.getOriginalFilename();
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(fileName)));
                out.write(file.getBytes());
                out.flush();
                out.close();
                url=fileName;
            } catch (IOException e) {
                e.printStackTrace();
                return Constant.FAIL;
            }
            return url;
        } else {
            return Constant.FAIL;
        }
    }
    /**
     * 检查文件大小
     *
     * @param file
     * @return
     */
    public static  boolean checkSize(MultipartFile file) {

        return MAXIMUM_FILE_SIZE >= file.getSize();
    }

    /**
     * 检查文件格式是否被允许
     *
     * @param fileName
     * @return
     */
    public static boolean checkSuffix(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return false;
        }
        String suffix = StringUtils.split(fileName, ".")[1];
        return Arrays.asList(ALLOW_FILE_SUFFIEX).contains(suffix);

    }
}
