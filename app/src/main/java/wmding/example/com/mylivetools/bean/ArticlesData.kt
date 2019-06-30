package wmding.example.com.mylivetools.bean

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by CoderLengary
 */


class ArticlesData {

    @Expose
    @SerializedName("errorCode")
    var errorCode: Int = 0
    @Expose
    @SerializedName("errorMsg")
    var errorMsg: String? = null

    @Expose
    @SerializedName("data")
    var data: Data? = null


    inner class Data {

        @Expose
        @SerializedName("curPage")
        var curPage: Int = 0

        @Expose
        @SerializedName("datas")
        var datas: List<ArticleDetailData>? = null
        @Expose
        @SerializedName("offset")
        var offset: Int = 0
        @Expose
        @SerializedName("over")
        var isOver: Boolean = false
        @Expose
        @SerializedName("pageCount")
        var pageCount: Int = 0
        @Expose
        @SerializedName("size")
        var size: Int = 0
        @Expose
        @SerializedName("total")
        var total: Int = 0
    }

}
