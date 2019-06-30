package wmding.example.com.mylivetools.fragment.second

import wmding.example.com.mylivetools.base.BasePresenter
import wmding.example.com.mylivetools.base.BaseView
import wmding.example.com.mylivetools.bean.CategoryResult

/**
 * @author wmding
 * @date 2019/6/28
 * @describe
 */
interface SecondContract {

    interface Presenter : BasePresenter {

        /**
         * 获取条目数据
         *
         * @param page
         * @param count
         */
        fun getItemData(isRefresh: Boolean, page: Int, count: Int)
    }


    interface View : BaseView<Presenter> {

        val isActive: Boolean

        fun showItem(categoryResult: CategoryResult)

        fun addItem(categoryResult: CategoryResult)

        fun setLoadingIndicator(isShow: Boolean)

    }


}
