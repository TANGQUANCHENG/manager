package cn.decentchina.manager.quartz.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 唐全成
 * @date 2018-09-04
 */
@SuppressWarnings("unused")
public class CronUtil {
    /**
     * 功能描述：日期转换cron表达式
     *
     * @param date       日期
     * @param dateFormat 格式化格式 e.g:yyyy-MM-dd HH:mm:ss
     * @return : java.lang.String
     */
    private static String formatDateByPattern(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /**
     * convert Date to cron ,eg.  "0 07 10 15 1 ? 2016"
     *
     * @param date 时间点
     * @return : java.lang.String
     */
    public static String getCron(java.util.Date date) {
        String dateFormat = "ss mm HH dd MM ? yyyy";
        return formatDateByPattern(date, dateFormat);
    }
}
