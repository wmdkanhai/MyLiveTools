package wmding.example.com.mylivetools.fragment.third

import wmding.example.com.mylivetools.base.BasePresenter
import wmding.example.com.mylivetools.base.BaseView
import wmding.example.com.mylivetools.bean.CategoryResult

/**
 * @author wmding
 * @date 2018/12/24
 * @describe
 */
interface ThirdContract {

    interface Presenter : BasePresenter {

        fun getImages(isRefresh: Boolean, category: String, count: Int, page: Int)
    }


    interface View : BaseView<Presenter> {

        val isActive: Boolean

        fun showImages(categoryResult: CategoryResult)

        fun addImages(categoryResult: CategoryResult)

        /**
         * 设置下拉加载的控件
         *
         * @param isShow 是否显示
         */
        fun setLoadingIndicator(isShow: Boolean)
    }
}
