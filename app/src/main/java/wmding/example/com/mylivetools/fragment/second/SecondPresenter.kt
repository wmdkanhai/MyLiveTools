package wmding.example.com.mylivetools.fragment.second

import android.util.Log

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import wmding.example.com.mylivetools.bean.CategoryResult
import wmding.example.com.mylivetools.net.NetWork

/**
 * @author wmding
 * @date 2019/6/28
 * @describe
 */
class SecondPresenter(private val mView: SecondContract.View) : SecondContract.Presenter {

    init {
        mView.setPresenter(this)
    }

    companion object {
        private const val TAG = "SecondPresenter"
    }

    override fun getItemData(isRefresh: Boolean, page: Int, count: Int) {

        NetWork.getGankApi()
                ?.getCategoryData("Android", count, page)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<CategoryResult> {
                    override fun onSubscribe(disposable: Disposable) {

                    }

                    override fun onNext(categoryResult: CategoryResult) {

                        Log.d(TAG, categoryResult.toString())

                        if (mView.isActive) {

                            if (!isRefresh) {
                                mView.showItem(categoryResult)
                            } else {
                                mView.addItem(categoryResult)
                            }

                        }

                    }

                    override fun onError(throwable: Throwable) {
                        Log.e(TAG, throwable.message)

                        if (mView.isActive) {
                            //设置加载控件不显示
                            mView.setLoadingIndicator(false)
                        }
                    }

                    override fun onComplete() {
                        if (mView.isActive) {
                            //设置加载控件不显示
                            mView.setLoadingIndicator(false)
                        }
                    }
                })


    }

    override fun subscribe() {

    }

    override fun unSubscribe() {}
}
