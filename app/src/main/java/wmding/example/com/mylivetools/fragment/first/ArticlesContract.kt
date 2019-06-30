package wmding.example.com.mylivetools.fragment.first

import wmding.example.com.mylivetools.base.BasePresenter
import wmding.example.com.mylivetools.base.BaseView
import wmding.example.com.mylivetools.bean.ArticleDetailData

/**
 * Created by wmding on 2018/8/27.
 */
interface ArticlesContract {

    interface Presenter : BasePresenter {
        /**
         * 获取文章列表
         *
         * @param page
         * @param forceUpdate
         * @param clearCache
         */
        fun getArticles(page: Int, forceUpdate: Boolean, clearCache: Boolean)
    }

    interface View : BaseView<Presenter> {

        /**
         * 页面是否是活动的
         *
         * @return
         */
        val isActive: Boolean

        /**
         * 显示文章列表
         *
         * @param list
         */
        fun showArticles(list: List<ArticleDetailData>)

        /**
         * 设置下拉加载的控件
         *
         * @param isShow 是否显示
         */
        fun setLoadingIndicator(isShow: Boolean)

    }
}
