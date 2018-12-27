package wmding.example.com.mylivetools.net.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import wmding.example.com.mylivetools.bean.CategoryResult;

/**
 * @author wmding
 * @date 2018/12/24
 * @describe 代码家的gank.io接口
 */
public interface GankApi {

    /**
     * 根据category获取Android、iOS等干货数据
     *
     * @param category 类别
     * @param count    条目数目
     * @param page     页数
     */
    @GET("data/{category}/{count}/{page}")
    Observable<CategoryResult> getCategoryData(@Path("category") String category, @Path("count") int count, @Path("page") int page);
}
