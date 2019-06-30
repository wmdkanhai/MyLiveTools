package wmding.example.com.mylivetools.base

import android.view.View

/**
 * Created by CoderLengary
 */


interface BaseView<T> {
    fun initViews(view: View)

    fun setPresenter(presenter: T)
}
