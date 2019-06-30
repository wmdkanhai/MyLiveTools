package wmding.example.com.mylivetools.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Binder
import android.util.Base64
import java.io.ByteArrayInputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate

/**
 * @author wmding
 * @date 2018/9/16
 * @describe
 */
object Utils {

    var appid: String? = null


    /**
     * 获取 APPID
     * @param context
     * @return
     */
    fun getApplicationID(context: Context): String? {

        do {

            if (appid != null) {

                break
            }

            val appidBuf = getApplicationIDBuff(context)
            appid = Base64.encodeToString(appidBuf, Base64.DEFAULT or Base64.NO_WRAP or Base64.NO_PADDING)
        } while (false)

        return appid
    }

    /**
     * 获取 app ID
     * @param context
     * @return
     */
    fun getApplicationIDBuff(context: Context): ByteArray? {

        var appidBuf: ByteArray? = null

        // 获取 packageName
        val callingUid = Binder.getCallingUid()
        val packageNames = context.packageManager.getPackagesForUid(callingUid) ?: return appidBuf

        // packageName 不能为空

        try {

            // 获取 appid
            val packageInfo = context.packageManager.getPackageInfo(packageNames[0], PackageManager.GET_SIGNATURES)
            val cert = packageInfo.signatures[0].toByteArray()
            val inputStream = ByteArrayInputStream(cert)
            val certificateFactory = CertificateFactory.getInstance("X509")
            val x509Certificate = certificateFactory.generateCertificate(inputStream) as X509Certificate

            // 获取签名的 SHA1 摘要
            val messageDigest = MessageDigest.getInstance("SHA1")
            appidBuf = messageDigest.digest(x509Certificate.encoded)
        } catch (e: PackageManager.NameNotFoundException) {

            e.printStackTrace()
        } catch (e: CertificateException) {

            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {

            e.printStackTrace()
        } finally {

            return appidBuf
        }
    }
}
