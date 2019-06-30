package wmding.example.com.mylivetools.utils

import android.text.TextUtils

object StringUtil {
    fun isInvalid(text: String): Boolean {
        var isInvalid = false
        if (TextUtils.isEmpty(text) || text.contains(" ")) {
            isInvalid = true
        }
        return isInvalid
    }

    fun replaceInvalidChar(text: String): String {
        return text.replace("<em class='highlight'>", "")
                .replace("</em>", "")
                .replace("&mdash;", "-")
                .replace("&ndash;", "-")
                .replace("&ldquo;", "'")
                .replace("&rdquo;", "'")
                .replace("&amp;", "&")
    }
}
