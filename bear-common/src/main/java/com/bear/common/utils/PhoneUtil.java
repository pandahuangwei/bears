package com.bear.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author panda.
 * @version 1.0.
 * @since 2018-11-25 17:51.
 */
public class PhoneUtil {
    private static int PHONE_LENGTH = 11;
    private static String REGEX = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
    private static Pattern P = Pattern.compile(REGEX);

    /**
     * 校验手机号
     *
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {
        if (phone == null || phone.length() != PHONE_LENGTH) {
            return Boolean.FALSE;
        }

        Matcher m = P.matcher(phone);
        return m.matches();
    }
}