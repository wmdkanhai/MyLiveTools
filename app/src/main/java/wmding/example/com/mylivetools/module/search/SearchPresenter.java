package wmding.example.com.mylivetools.module.search;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import wmding.example.com.mylivetools.bean.ArticleDetailData;
import wmding.example.com.mylivetools.data.ArticlesDataRepository;

/**
 * Created by wmding on 2018/9/5.
 */
public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View view;
    private CompositeDisposable compositeDisposable;
    private ArticlesDataRepository articlesDataRepository;



    public SearchPresenter(ArticlesDataRepository articlesDataRepository,SearchContract.View view) {
        compositeDisposable = new CompositeDisposable();
        this.view = view;
        this.articlesDataRepository = articlesDataRepository;
        view.setPresenter(this);

    }

    @Override
    public void searchArticles(@NonNull int page, @NonNull String keyWords, boolean forceUpdate, boolean clearCache) {
        Disposable disposable = articlesDataRepository.queryArticles(page, keyWords, forceUpdate, clearCache)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<ArticleDetailData>>() {

                    @Override
                    public void onNext(List<ArticleDetailData> value) {
                        if (view.isActive()) {
                            view.showArticles(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showEmptyView(true);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        compositeDisposable.clear();
    }
}
