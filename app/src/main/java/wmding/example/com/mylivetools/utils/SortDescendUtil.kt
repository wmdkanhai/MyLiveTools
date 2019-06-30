package wmding.example.com.mylivetools.utils


import wmding.example.com.mylivetools.bean.ArticleDetailData

/**
 * Created by CoderLengary
 */


object SortDescendUtil {

    fun sortArticleDetailData(articleDetailData: ArticleDetailData, t1: ArticleDetailData): Int {
        return if (articleDetailData.publishTime > t1.publishTime) {
            -1
        } else {
            1
        }
    }

}
