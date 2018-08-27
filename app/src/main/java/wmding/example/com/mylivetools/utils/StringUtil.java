package wmding.example.com.mylivetools.utils;

import android.text.TextUtils;

public class StringUtil {
    public static boolean isInvalid(String text) {
        boolean isInvalid = false;
        if (TextUtils.isEmpty(text) || text.contains(" ")) {
            isInvalid = true;
        }
        return isInvalid;
    }

    public static String replaceInvalidChar(String text) {
        return text.replace("<em class='highlight'>", "")
                .replace("</em>", "")
                .replace("&mdash;", "-")
                .replace("&ndash;", "-")
                .replace("&ldquo;", "'")
                .replace("&rdquo;", "'")
                .replace("&amp;", "&");
    }
}
