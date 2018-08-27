package wmding.example.com.mylivetools.data;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import wmding.example.com.mylivetools.bean.ArticleDetailData;

/**
 * Created by wmding on 2018/8/27.
 */
public interface ArticlesDataSource {

    Observable<List<ArticleDetailData>> getArticles(@NonNull int page, @NonNull boolean forceUpdate, @NonNull boolean clearCache);
}
