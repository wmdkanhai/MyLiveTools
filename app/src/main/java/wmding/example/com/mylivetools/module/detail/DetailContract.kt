package wmding.example.com.mylivetools.module.detail

import wmding.example.com.mylivetools.base.BasePresenter
import wmding.example.com.mylivetools.base.BaseView

/**
 * @author wmding
 * @date 2018/9/12
 */
interface DetailContract {

    interface Presenter : BasePresenter

    interface View : BaseView<Presenter>
}
