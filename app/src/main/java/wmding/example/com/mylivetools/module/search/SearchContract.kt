package wmding.example.com.mylivetools.module.search

import wmding.example.com.mylivetools.base.BasePresenter
import wmding.example.com.mylivetools.base.BaseView
import wmding.example.com.mylivetools.bean.ArticleDetailData

/**
 * Created by wmding on 2018/9/5.
 */
interface SearchContract {

    interface Presenter : BasePresenter {


        /**
         * 搜索文章
         * @param page
         * @param keyWords
         * @param forceUpdate
         * @param clearCache
         */
        fun searchArticles(page: Int, keyWords: String, forceUpdate: Boolean, clearCache: Boolean)

    }

    interface View : BaseView<Presenter> {

        /**
         * 页面是否是活动的
         *
         * @return
         */
        val isActive: Boolean

        /**
         * 隐藏键盘
         */
        fun hideKeyboard()


        /**
         * 显示文章列表
         *
         * @param articlesList
         */
        fun showArticles(articlesList: List<ArticleDetailData>)

        /**
         * 是否显示空白页面
         *
         * @param isShow
         */
        fun showEmptyView(isShow: Boolean)


    }


}
