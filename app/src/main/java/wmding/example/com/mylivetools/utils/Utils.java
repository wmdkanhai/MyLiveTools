package wmding.example.com.mylivetools.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * @author wmding
 * @date 2018/9/16
 * @describe
 */
public class Utils {

    public static String appid;


    /**
     * 获取 APPID
     * @param context
     * @return
     */
    public static String getApplicationID(Context context) {

        do {

            if (appid !=null) {

                break;
            }

            byte[] appidBuf = getApplicationIDBuff(context);
            appid =  Base64.encodeToString(appidBuf, Base64.DEFAULT | Base64.NO_WRAP | Base64.NO_PADDING);
        } while (false);

        return appid;
    }

    /**
     * 获取 app ID
     * @param context
     * @return
     */
    public static byte[] getApplicationIDBuff(Context context) {

        byte[] appidBuf = null;

        // 获取 packageName
        int callingUid = Binder.getCallingUid();
        String packageNames[] = context.getPackageManager().getPackagesForUid(callingUid);

        // packageName 不能为空
        if (packageNames == null) {

            return appidBuf;
        }

        try {

            // 获取 appid
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageNames[0], PackageManager.GET_SIGNATURES);
            byte[] cert = packageInfo.signatures[0].toByteArray();
            InputStream inputStream = new ByteArrayInputStream(cert);
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
            X509Certificate x509Certificate = (X509Certificate) certificateFactory.generateCertificate(inputStream);

            // 获取签名的 SHA1 摘要
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            appidBuf = messageDigest.digest(x509Certificate.getEncoded());
        } catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();
        } catch (CertificateException e) {

            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        } finally {

            return appidBuf;
        }
    }
}
