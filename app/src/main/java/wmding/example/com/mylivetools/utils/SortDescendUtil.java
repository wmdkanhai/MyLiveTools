package wmding.example.com.mylivetools.utils;


import wmding.example.com.mylivetools.bean.ArticleDetailData;

/**
 * Created by CoderLengary
 */


public class SortDescendUtil {

    public static int sortArticleDetailData(ArticleDetailData articleDetailData, ArticleDetailData t1) {
        if (articleDetailData.getPublishTime() > t1.getPublishTime()){
            return -1;
        }else {
            return 1;
        }
    }

}
