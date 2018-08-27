package wmding.example.com.mylivetools.fragment;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import wmding.example.com.mylivetools.bean.ArticleDetailData;
import wmding.example.com.mylivetools.data.ArticlesDataRepository;

/**
 * Created by wmding on 2018/8/27.
 */
public class ArticlesPresenter implements ArticlesContract.Presenter {

    private ArticlesDataRepository mArticlesDataRepository;

    private CompositeDisposable mCompositeDisposable;

    private ArticlesContract.View mView;


    public ArticlesPresenter(ArticlesContract.View view, ArticlesDataRepository articleRepository) {
        mArticlesDataRepository = articleRepository;
        mCompositeDisposable = new CompositeDisposable();
        mView = view;
        this.mView.setPresenter(this);
    }

    @Override
    public void getArticles(int page, boolean forceUpdate, boolean clearCache) {

        Disposable disposable = mArticlesDataRepository.getArticles(page, forceUpdate, clearCache)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<ArticleDetailData>>() {
                    @Override
                    public void onNext(List<ArticleDetailData> value) {

                        if (mView.isActive()) {
                            mView.showArticles(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isActive()) {

                        }
                    }

                    @Override
                    public void onComplete() {
                        if (mView.isActive()) {

                        }
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }
}
