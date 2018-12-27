package wmding.example.com.mylivetools.fragment.third;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import wmding.example.com.mylivetools.bean.CategoryResult;
import wmding.example.com.mylivetools.net.NetWork;

/**
 * @author wmding
 * @date 2018/12/24
 * @describe
 */
public class ThirdPresenter implements ThirdContract.Presenter {

    private static final String TAG = "ThirdPresenter";
    private ThirdContract.View mView;

    public ThirdPresenter(ThirdContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }

    @Override
    public void getImages(String category, int count, int page) {
        NetWork.getGankApi()
                .getCategoryData(category, count, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CategoryResult value) {

                        Log.e(TAG,value.toString());

                        if (mView.isActive()){
                            mView.showImages(value);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e(TAG,e.getMessage());

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
