package wmding.example.com.mylivetools.data.remote

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import wmding.example.com.mylivetools.bean.ArticleDetailData
import wmding.example.com.mylivetools.bean.ArticlesData
import wmding.example.com.mylivetools.data.ArticlesDataSource
import wmding.example.com.mylivetools.retrofit.RetrofitClient
import wmding.example.com.mylivetools.retrofit.RetrofitService
import wmding.example.com.mylivetools.utils.SortDescendUtil

/**
 * Created by wmding on 2018/8/27.
 */
class ArticlesDataRemoteSource private constructor() : ArticlesDataSource {

    override fun getArticles(page: Int, forceUpdate: Boolean, clearCache: Boolean): Observable<List<ArticleDetailData>> {
        return RetrofitClient.instance
                .create(RetrofitService::class.java!!)
                .getArticles(page)
                .filter(Predicate<ArticlesData> { articlesData -> articlesData.errorCode != -1 })
                //获取的数据类型是ArticlesData，我们需要的是它内部的ArticleDetailData，所以要用到flatMap
                .flatMap(Function<ArticlesData, ObservableSource<List<ArticleDetailData>>> { articlesData ->
                    Observable.fromIterable(articlesData.data!!.datas!!
                    ).toSortedList { articleDetailData, t1 -> SortDescendUtil.sortArticleDetailData(articleDetailData, t1) }.toObservable().doOnNext { articleDetailData ->
                        for (item in articleDetailData) {
                            //saveToRealm(item);
                        }
                    }
                })
    }

    override fun queryArticles(page: Int, keyWords: String, forceUpdate: Boolean, clearCache: Boolean): Observable<List<ArticleDetailData>> {

        return RetrofitClient.instance
                .create(RetrofitService::class.java!!)
                .queryArticles(page, keyWords)
                .filter(Predicate<ArticlesData> { articlesData -> articlesData.errorCode != -1 })
                .flatMap(Function<ArticlesData, ObservableSource<List<ArticleDetailData>>> { articlesData ->
                    Observable.fromIterable(articlesData.data!!.datas!!).toSortedList { articleDetailData, t1 -> SortDescendUtil.sortArticleDetailData(articleDetailData, t1) }.toObservable().doOnNext { list ->
                        for (item in list) {
                            //saveToRealm(item);
                        }
                    }
                })
    }

    companion object {

        private lateinit var INSTANCE: ArticlesDataRemoteSource

        val instance: ArticlesDataRemoteSource
            get() {
                if (INSTANCE == null) {
                    INSTANCE = ArticlesDataRemoteSource()
                }
                return INSTANCE
            }
    }
}
