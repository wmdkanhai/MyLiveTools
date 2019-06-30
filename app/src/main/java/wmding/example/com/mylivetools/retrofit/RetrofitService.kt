package wmding.example.com.mylivetools.retrofit


import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import wmding.example.com.mylivetools.bean.ArticlesData

/**
 * Created by CoderLengary
 */


interface RetrofitService {

    //    @FormUrlEncoded
    //    @POST(Api.LOGIN)
    //    Observable<LoginData> login(@Field("username") String username, @Field("password") String password);
    //
    //    @FormUrlEncoded
    //    @POST(Api.REGISTER)
    //    Observable<LoginData> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);
    //
    //获取首页文章
    @GET(Api.ARTICLE_LIST + "{page}/json")
    fun getArticles(@Path("page") page: Int): Observable<ArticlesData>


    //
    //    //获取分类文章，cid指的是类别
    //    @GET(Api.ARTICLE_LIST + "{page}/json")
    //    Observable<ArticlesData> getArticlesFromCatg(@Path("page") int page, @Query("cid") int cid);
    //
    //    //获取文章的所有分类
    //    @GET(Api.CATEGORIES)
    //    Observable<CategoriesData> getCategories();
    //
    //获取查询的文章，k指的是用户输入的词
    @POST(Api.QUERY_ARTICLES + "{page}/json")
    fun queryArticles(@Path("page") page: Int, @Query("k") k: String): Observable<ArticlesData>
    //
    //    //获取热搜词
    //    @GET(Api.HOT_KEY)
    //    Observable<HotKeysData> getHotKeys();
    //
    //    @GET(Api.BANNER)
    //    Observable<BannerData> getBanner();
    //
    //    @POST(Api.COLLECT_ARTICLE+"{id}/json")
    //    Observable<Status> collectArticle(@Path("id") int id);
    //
    //    @POST(Api.CANCEL_COLLECTING_ARTICLE + "{originId}/json")
    //    Observable<Status> uncollectArticle(@Path("originId") int originId);
    //
    //    //获取收藏文章的列表
    //    @GET(Api.GET_FAVORITE_ARTICLES + "{page}/json")
    //    Observable<FavoriteArticlesData> getFavoriteArticles(@Path("page") int page);


}
