package wmding.example.com.mylivetools.data

import io.reactivex.Observable
import wmding.example.com.mylivetools.bean.ArticleDetailData

/**
 * Created by wmding on 2018/8/27.
 */
interface ArticlesDataSource {

    /**
     * 获取所有文章
     */
    fun getArticles(page: Int, forceUpdate: Boolean, clearCache: Boolean): Observable<List<ArticleDetailData>>


    /**
     * 通过关键字获取文章
     */
    fun queryArticles(page: Int, keyWords: String, forceUpdate: Boolean, clearCache: Boolean): Observable<List<ArticleDetailData>>
}
