package wmding.example.com.mylivetools.fragment.third

import android.util.Log

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import wmding.example.com.mylivetools.bean.CategoryResult
import wmding.example.com.mylivetools.net.NetWork

/**
 * @author wmding
 * @date 2018/12/24
 * @describe
 */
class ThirdPresenter(private val mView: ThirdContract.View) : ThirdContract.Presenter {

    companion object {

        private const val TAG = "ThirdPresenter"
    }

    init {
        this.mView.setPresenter(this)
    }

    override fun getImages(isRefresh: Boolean, category: String, count: Int, page: Int) {
        NetWork.getGankApi()?.getCategoryData(category, count, page)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<CategoryResult> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(value: CategoryResult) {

                        Log.e(TAG, value.toString())

                        if (mView.isActive) {

                            if (!isRefresh) {
                                mView.showImages(value)
                            } else {
                                mView.addImages(value)

                            }

                        }

                    }

                    override fun onError(e: Throwable) {

                        Log.e(TAG, e.message)

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

    override fun unSubscribe() {

    }
}
