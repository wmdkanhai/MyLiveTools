package wmding.example.com.mylivetools.fragment.second;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import wmding.example.com.mylivetools.bean.CategoryResult;
import wmding.example.com.mylivetools.net.NetWork;

/**
 * @author wmding
 * @date 2019/6/28
 * @describe
 */
public class SecondPresenter implements SecondContract.Presenter {
    private static final String TAG = "SecondPresenter";

    private SecondContract.View mView;

    public SecondPresenter(SecondContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getItemData(final boolean isRefresh, int page, int count) {

        NetWork.getGankApi().getCategoryData("Android", count, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryResult>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(CategoryResult categoryResult) {

                        Log.d(TAG, categoryResult.toString());

                        if (mView.isActive()) {

                            if (!isRefresh) {
                                mView.showItem(categoryResult);
                            } else {
                                mView.addItem(categoryResult);
                            }

                        }

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e(TAG, throwable.getMessage());

                        if (mView.isActive()) {
                            //设置加载控件不显示
                            mView.setLoadingIndicator(false);
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (mView.isActive()) {
                            //设置加载控件不显示
                            mView.setLoadingIndicator(false);
                        }
                    }
                });


    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
    }
}
