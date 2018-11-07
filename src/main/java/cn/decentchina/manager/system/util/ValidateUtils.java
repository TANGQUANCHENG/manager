package cn.decentchina.manager.system.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 规则验证
 *
 * @author 唐全成
 */
public class ValidateUtils {

    private static Pattern IS_INTEGER = Pattern.compile("^[-\\+]?[\\d]*$");
    private static Pattern IS_POSITIVE_INTEGER = Pattern.compile("^[\\d]*$");
    private static Pattern IS_DOUBLE = Pattern.compile("^[-\\+]?[.\\d]*$");
    private static Pattern IS_DOUBLE2 = Pattern.compile("^[\\d]*[.]*[\\d]{0,2}$");
    private static Pattern IS_INTEGER2 = Pattern.compile("^[\\d]*[.]*[0]*$");
    private static Pattern IS_EMAIL = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    private static Pattern IS_CHINESE = Pattern.compile("^[\u4e00-\u9fa5]*$");
    private static Pattern IS_MOBILE = Pattern.compile("^1[3|4|5|6|7|8|9][0-9]{9}$");
    private static Pattern IS_PHONE = Pattern.compile("^0[0-9]{2,3}[0-9]{7,8}$");
    private static Pattern IS_POST = Pattern.compile("^[0-9]{6}$");
    private static Pattern IS_URL = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
    private static Pattern IS_IP = Pattern.compile("^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$");
    private static Pattern IS_MAC = Pattern.compile("^([0-9a-fA-F]{2})(([\\s:-][0-9a-fA-F]{2}){5})$");
    private static Pattern IS_PASSPORT = Pattern.compile("^1[45][0-9]{7}|G[0-9]{8}|E[0-9]{8}|P[0-9]{7}|S[0-9]{7,8}|D[0-9]+$");
    private static Pattern IS_LETTER_DIGIT_OR_CHINESE = Pattern.compile("^[·a-z0-9A-Z\u4e00-\u9fa5]+$");
    private static Pattern IS_LEGAL_PASSWORD = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$");

    public ValidateUtils() {
    }

    public static boolean isInteger(String str) {
        return IS_INTEGER.matcher(str).matches();
    }

    public static boolean isPositiveInteger(String str) {
        return IS_POSITIVE_INTEGER.matcher(str).matches();
    }

    public static boolean isDouble(String str) {
        return IS_DOUBLE.matcher(str).matches();
    }

    public static boolean isDouble2(String str) {
        return IS_DOUBLE2.matcher(str).matches();
    }

    public static boolean isInteger2(String str) {
        return IS_INTEGER2.matcher(str).matches();
    }

    public static boolean isEmail(String str) {
        return IS_EMAIL.matcher(str).matches();
    }

    public static boolean isChinese(String str) {
        return IS_CHINESE.matcher(str).matches();
    }

    public static boolean isLegalPassword(String str) {
        return IS_LEGAL_PASSWORD.matcher(str).matches();
    }

    public static boolean isMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return false;
        }
        return IS_MOBILE.matcher(mobile).matches();
    }

    public static boolean isPhone(String phone) {
        return IS_PHONE.matcher(phone).matches();
    }

    public static boolean isPost(String post) {
        return IS_POST.matcher(post).matches();
    }

    public static boolean isDate(String dateStr) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        try {
            date = df.parse(dateStr);
        } catch (ParseException var4) {
            return false;
        }

        return date != null;
    }

    public static boolean isDateTime(String dateTime) {
        int first = dateTime.indexOf(":");
        int last = dateTime.lastIndexOf(":");
        if (first == -1) {
            return false;
        } else {
            SimpleDateFormat df = null;
            if (first == last) {
                df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            } else {
                df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            }

            Date date = null;

            try {
                date = df.parse(dateTime);
            } catch (ParseException var6) {
                return false;
            }

            return date != null;
        }
    }

    public static boolean isURL(String url) {
        return IS_URL.matcher(url).matches();
    }

    public static boolean isIP(String ip) {
        return IS_IP.matcher(ip).matches();
    }

    public static boolean isMac(String mac) {
        return IS_MAC.matcher(mac).matches();
    }

    public static boolean isBankCard(String bankCard) {
        if (!StringUtils.isBlank(bankCard)) {
            String nonCheckCodeCardId = bankCard.substring(0, bankCard.length() - 1);
            if (nonCheckCodeCardId.matches("\\d+")) {
                char[] chs = nonCheckCodeCardId.toCharArray();
                int luhmSum = 0;
                int i = chs.length - 1;

                for (int j = 0; i >= 0; ++j) {
                    int k = chs[i] - 48;
                    if (j % 2 == 0) {
                        k *= 2;
                        k = k / 10 + k % 10;
                    }

                    luhmSum += k;
                    --i;
                }

                char b = luhmSum % 10 == 0 ? 48 : (char) (10 - luhmSum % 10 + 48);
                return bankCard.charAt(bankCard.length() - 1) == b;
            }
        }

        return false;
    }

    public static boolean isLetterDigitOrChinese(String str) {
        return IS_LETTER_DIGIT_OR_CHINESE.matcher(str).matches();
    }

    public static boolean isPassport(String passport) {
        return IS_PASSPORT.matcher(passport).matches();
    }

    public static void checkStrMinLength(String str, Integer minLength, String message) {
        if (str.trim().length() < minLength) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkStrMaxLength(String str, Integer maxLength, String message) {
        if (str.trim().length() > maxLength) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkNotNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkNotEmpty(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        } else if (obj instanceof String && obj.toString().trim().length() == 0) {
            throw new IllegalArgumentException(message);
        } else if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            throw new IllegalArgumentException(message);
        } else if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            throw new IllegalArgumentException(message);
        } else if (obj instanceof Map && ((Map) obj).isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof String && obj.toString().trim().length() == 0) {
            return true;
        } else if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            return true;
        } else if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;
        } else {
            return obj instanceof Map && ((Map) obj).isEmpty();
        }
    }

}
