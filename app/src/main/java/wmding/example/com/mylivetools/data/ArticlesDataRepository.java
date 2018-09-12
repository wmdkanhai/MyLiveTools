package wmding.example.com.mylivetools.data;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import wmding.example.com.mylivetools.bean.ArticleDetailData;
import wmding.example.com.mylivetools.utils.SortDescendUtil;

/**
 * Created by wmding on 2018/8/27.
 */
public class ArticlesDataRepository implements ArticlesDataSource {

    @NonNull
    private ArticlesDataSource mArticlesDataSource;

    private Map<Integer, ArticleDetailData> articlesCache;

    private Map<Integer, ArticleDetailData> queryCache;

    private final int INDEX = 0;


    public static ArticlesDataRepository INSTANCE;

    private ArticlesDataRepository(@NonNull ArticlesDataSource articlesDataSource) {
        mArticlesDataSource = articlesDataSource;
    }

    public static ArticlesDataRepository getInstance(@NonNull ArticlesDataSource articlesDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new ArticlesDataRepository(articlesDataSource);
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<ArticleDetailData>> getArticles(@NonNull int page, @NonNull boolean forceUpdate, @NonNull final boolean clearCache) {
        //!forceUpdate即用户按home键然后再返回我们的APP的情况，这时候直接返回缓存的文章列表
        if (!forceUpdate && articlesCache != null) {
            return Observable.fromIterable(new ArrayList<>(articlesCache.values()))
                    .toSortedList(new Comparator<ArticleDetailData>() {
                        @Override
                        public int compare(ArticleDetailData articleDetailData, ArticleDetailData t1) {
                            return SortDescendUtil.sortArticleDetailData(articleDetailData, t1);
                        }
                    }).toObservable();
        }

        //forceUpdate&&!clearCache 即用户向下滑动列表的情况，我们需要请求下一页的数据，并保存到缓存里
        if (!clearCache && articlesCache != null) {
            Observable<List<ArticleDetailData>> ob1 = Observable.fromIterable(new ArrayList<>(articlesCache.values()))
                    .toSortedList(new Comparator<ArticleDetailData>() {
                        @Override
                        public int compare(ArticleDetailData articleDetailData, ArticleDetailData t1) {
                            return SortDescendUtil.sortArticleDetailData(articleDetailData, t1);
                        }
                    }).toObservable();

            Observable<List<ArticleDetailData>> ob2 = mArticlesDataSource.getArticles(page, forceUpdate, clearCache)
                    .doOnNext(new Consumer<List<ArticleDetailData>>() {
                        @Override
                        public void accept(List<ArticleDetailData> list) throws Exception {
                            refreshArticlesCache(clearCache, list);
                        }
                    });

            //获取到缓存的数据加上新请求的下一页的数据，需要结合这两个数据并统一发送
            return Observable.merge(ob1, ob2).collect(new Callable<List<ArticleDetailData>>() {
                @Override
                public List<ArticleDetailData> call() throws Exception {
                    return new ArrayList<>();
                }
            }, new BiConsumer<List<ArticleDetailData>, List<ArticleDetailData>>() {
                @Override
                public void accept(List<ArticleDetailData> list, List<ArticleDetailData> dataList) throws Exception {
                    list.addAll(dataList);
                }
            }).toObservable();
        }

        //forceUpdate&&clearCache 即下拉刷新，还有第一次加载的情况
        return mArticlesDataSource.getArticles(INDEX, forceUpdate, clearCache)
                .doOnNext(new Consumer<List<ArticleDetailData>>() {
                    @Override
                    public void accept(List<ArticleDetailData> list) throws Exception {
                        refreshArticlesCache(clearCache, list);
                    }
                });
    }

    private void refreshArticlesCache(@NonNull boolean clearCache, @NonNull List<ArticleDetailData> list) {
        if (articlesCache == null) {
            articlesCache = new LinkedHashMap<>();
        }
        if (clearCache) {
            articlesCache.clear();
        }
        for (ArticleDetailData item : list) {
            articlesCache.put(item.getId(), item);
        }

    }

    @Override
    public Observable<List<ArticleDetailData>> queryArticles(@NonNull int page, @NonNull String keyWords, @NonNull boolean forceUpdate, @NonNull final boolean clearCache) {
        Observable<List<ArticleDetailData>> listObservable = mArticlesDataSource.queryArticles(page, keyWords, forceUpdate, clearCache)
                .doOnNext(new Consumer<List<ArticleDetailData>>() {
                    @Override
                    public void accept(List<ArticleDetailData> articleDetailData) throws Exception {
                        refreshQueryCache(clearCache, articleDetailData);
                    }
                });
        return listObservable;
    }


    private void refreshQueryCache(@NonNull boolean clearCache, @NonNull List<ArticleDetailData> list) {
        if (queryCache == null) {
            queryCache = new LinkedHashMap<>();
        }
        if (clearCache) {
            queryCache.clear();
        }
        for (ArticleDetailData item : list) {
            queryCache.put(item.getId(), item);
        }

    }

}
