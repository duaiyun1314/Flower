package com.andy.flower.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by andy on 16-6-6.
 */
public class StringUtil {
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static String getWeeklyReleaseNo(String source) {
        try {
            String[] split = source.split("-");
            String releaseNo = split[0] + "/" + split[1] + " 第" + split[2] + "期";
            return releaseNo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
