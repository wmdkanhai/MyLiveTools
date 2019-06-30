package wmding.example.com.mylivetools.retrofit


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by wmding on 2018/8/26.
 */

//单例模式
object RetrofitClient {

    val instance: Retrofit
        get() = ClientHolder.retrofit

    private object ClientHolder {

        private val okHttpClient = OkHttpClient.Builder()
                //添加对Cookies的管理
                //.cookieJar(new CookieManger(App.getContext()))
                .build()


        val retrofit = Retrofit.Builder()
                .baseUrl(Api.API_BASE)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}
