package wmding.example.com.mylivetools.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import wmding.example.com.mylivetools.net.api.GankApi

object NetWork {

    private var gankApi: GankApi? = null
    private val okHttpClient = OkHttpClient()

    fun getGankApi(): GankApi? {
        if (gankApi == null) {
            val retrofit = Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://gank.io/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            gankApi = retrofit.create<GankApi>(GankApi::class.java!!)
        }
        return gankApi
    }
}
