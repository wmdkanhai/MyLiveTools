package wmding.example.com.mylivetools.module.search;

import android.support.annotation.NonNull;

import java.util.List;

import wmding.example.com.mylivetools.base.BasePresenter;
import wmding.example.com.mylivetools.base.BaseView;
import wmding.example.com.mylivetools.bean.ArticleDetailData;

/**
 * Created by wmding on 2018/9/5.
 */
public interface SearchContract {

    interface Presenter extends BasePresenter {


        /**
         * 搜索文章
         * @param page
         * @param keyWords
         * @param forceUpdate
         * @param clearCache
         */
        void searchArticles(@NonNull int page, @NonNull String keyWords, boolean forceUpdate, boolean clearCache);

    }

    interface View extends BaseView<Presenter> {

        /**
         * 隐藏键盘
         */
        void hideKeyboard();

        /**
         * 页面是否是活动的
         *
         * @return
         */
        boolean isActive();


        /**
         * 显示文章列表
         *
         * @param articlesList
         */
        void showArticles(List<ArticleDetailData> articlesList);

        /**
         * 是否显示空白页面
         *
         * @param isShow
         */
        void showEmptyView(boolean isShow);


    }


}
