package wmding.example.com.mylivetools.retrofit;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wmding on 2018/8/26.
 */

//单例模式
public class RetrofitClient {

    private RetrofitClient() {
    }

    private static class ClientHolder{

        private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //添加对Cookies的管理
                //.cookieJar(new CookieManger(App.getContext()))
                .build();


        private static Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.API_BASE)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static Retrofit getInstance(){
        return ClientHolder.retrofit;
    }
}
