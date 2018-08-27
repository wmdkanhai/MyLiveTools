package wmding.example.com.mylivetools.data;

import android.support.annotation.NonNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import wmding.example.com.mylivetools.bean.ArticleDetailData;

/**
 * Created by wmding on 2018/8/27.
 */
public class ArticlesDataRepository implements ArticlesDataSource{

    @NonNull
    private ArticlesDataSource mArticlesDataSource;

    private Map<Integer, ArticleDetailData> articlesCache;


    public static ArticlesDataRepository INSTANCE;

    private ArticlesDataRepository(@NonNull ArticlesDataSource articlesDataSource) {
        mArticlesDataSource = articlesDataSource;
    }

    public static ArticlesDataRepository getInstance(@NonNull ArticlesDataSource articlesDataSource) {
        if (INSTANCE == null){
            INSTANCE = new ArticlesDataRepository(articlesDataSource);
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<ArticleDetailData>> getArticles(@NonNull int page, @NonNull boolean forceUpdate, @NonNull final boolean clearCache) {
        return mArticlesDataSource.getArticles(page, forceUpdate, clearCache)
                .doOnNext(new Consumer<List<ArticleDetailData>>() {
                    @Override
                    public void accept(List<ArticleDetailData> list) throws Exception {
                        refreshArticlesCache(clearCache, list);
                    }
                });
    }

    private void refreshArticlesCache(@NonNull boolean clearCache, @NonNull List<ArticleDetailData> list){
        if (articlesCache == null) {
            articlesCache = new LinkedHashMap<>();
        }
        if (clearCache){
            articlesCache.clear();
        }
        for (ArticleDetailData item : list) {
            articlesCache.put(item.getId(), item);
        }

    }
}
