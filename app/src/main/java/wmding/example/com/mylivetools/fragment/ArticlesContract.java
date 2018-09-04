package wmding.example.com.mylivetools.fragment;

import java.util.List;

import wmding.example.com.mylivetools.base.BasePresenter;
import wmding.example.com.mylivetools.base.BaseView;
import wmding.example.com.mylivetools.bean.ArticleDetailData;

/**
 * Created by wmding on 2018/8/27.
 */
public interface ArticlesContract {

    interface Presenter extends BasePresenter {
        /**
         * 获取文章列表
         *
         * @param page
         * @param forceUpdate
         * @param clearCache
         */
        void getArticles(int page, boolean forceUpdate, boolean clearCache);
    }

    interface View extends BaseView<Presenter> {

        /**
         * 页面是否是活动的
         *
         * @return
         */
        boolean isActive();

        /**
         * 显示文章列表
         *
         * @param list
         */
        void showArticles(List<ArticleDetailData> list);

        /**
         * 设置下拉加载的控件
         *
         * @param isShow 是否显示
         */
        void setLoadingIndicator(boolean isShow);

    }
}
