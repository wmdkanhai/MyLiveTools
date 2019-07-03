package wmding.example.com.mylivetools.utils

import android.content.Context
import android.net.ConnectivityManager


object NetworkUtil {

    /**
     * 检查网络是否可用
     * @param context
     * @return
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val manager = context
                .applicationContext.getSystemService(
                Context.CONNECTIVITY_SERVICE) as ConnectivityManager ?: return false
        val networkInfo = manager.activeNetworkInfo
        return !(networkInfo == null || !networkInfo.isAvailable)
    }

}
