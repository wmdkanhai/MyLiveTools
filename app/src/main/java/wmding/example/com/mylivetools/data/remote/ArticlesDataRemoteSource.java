package wmding.example.com.mylivetools.data.remote;

import android.support.annotation.NonNull;

import java.util.Comparator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import wmding.example.com.mylivetools.bean.ArticleDetailData;
import wmding.example.com.mylivetools.bean.ArticlesData;
import wmding.example.com.mylivetools.data.ArticlesDataSource;
import wmding.example.com.mylivetools.retrofit.RetrofitClient;
import wmding.example.com.mylivetools.retrofit.RetrofitService;
import wmding.example.com.mylivetools.utils.SortDescendUtil;

/**
 * Created by wmding on 2018/8/27.
 */
public class ArticlesDataRemoteSource implements ArticlesDataSource {

    @NonNull
    private static ArticlesDataRemoteSource INSTANCE;

    private ArticlesDataRemoteSource() {
    }

    public static ArticlesDataRemoteSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ArticlesDataRemoteSource();
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<ArticleDetailData>> getArticles(@NonNull int page, @NonNull boolean forceUpdate, @NonNull boolean clearCache) {
        Observable<List<ArticleDetailData>> listObservable = RetrofitClient.getInstance()
                .create(RetrofitService.class)
                .getArticles(page)
                .filter(new Predicate<ArticlesData>() {
                    @Override
                    public boolean test(ArticlesData articlesData) throws Exception {
                        return articlesData.getErrorCode() != -1;
                    }
                })
                //获取的数据类型是ArticlesData，我们需要的是它内部的ArticleDetailData，所以要用到flatMap
                .flatMap(new Function<ArticlesData, ObservableSource<List<ArticleDetailData>>>() {
                    @Override
                    public ObservableSource<List<ArticleDetailData>> apply(ArticlesData articlesData) throws Exception {
                        return Observable.fromIterable(articlesData.getData().getDatas()
                        ).toSortedList(new Comparator<ArticleDetailData>() {
                            @Override
                            public int compare(ArticleDetailData articleDetailData, ArticleDetailData t1) {
                                return SortDescendUtil.sortArticleDetailData(articleDetailData, t1);
                            }
                        }).toObservable().doOnNext(new Consumer<List<ArticleDetailData>>() {
                            @Override
                            public void accept(List<ArticleDetailData> articleDetailData) throws Exception {
                                for (ArticleDetailData item : articleDetailData) {
                                    //saveToRealm(item);
                                }
                            }
                        });
                    }
                });
        return listObservable;
    }

    @Override
    public Observable<List<ArticleDetailData>> queryArticles(@NonNull int page, @NonNull String keyWords, @NonNull boolean forceUpdate, @NonNull boolean clearCache) {
        Observable<List<ArticleDetailData>> listObservable = RetrofitClient.getInstance()
                .create(RetrofitService.class)
                .queryArticles(page, keyWords)
                .filter(new Predicate<ArticlesData>() {
                    @Override
                    public boolean test(ArticlesData articlesData) throws Exception {
                        return articlesData.getErrorCode() != -1;
                    }
                })
                .flatMap(new Function<ArticlesData, ObservableSource<List<ArticleDetailData>>>() {
                    @Override
                    public ObservableSource<List<ArticleDetailData>> apply(ArticlesData articlesData) throws Exception {
                        return Observable.fromIterable(articlesData.getData().getDatas()).toSortedList(new Comparator<ArticleDetailData>() {
                            @Override
                            public int compare(ArticleDetailData articleDetailData, ArticleDetailData t1) {
                                return SortDescendUtil.sortArticleDetailData(articleDetailData, t1);
                            }
                        }).toObservable().doOnNext(new Consumer<List<ArticleDetailData>>() {
                            @Override
                            public void accept(List<ArticleDetailData> list) throws Exception {
                                for (ArticleDetailData item : list) {
                                    //saveToRealm(item);
                                }
                            }
                        });
                    }
                });

        return listObservable;
    }
}
