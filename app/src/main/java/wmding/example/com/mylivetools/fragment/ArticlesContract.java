package wmding.example.com.mylivetools.fragment;

import java.util.List;

import wmding.example.com.mylivetools.base.BasePresenter;
import wmding.example.com.mylivetools.base.BaseView;
import wmding.example.com.mylivetools.bean.ArticleDetailData;

/**
 * Created by wmding on 2018/8/27.
 */
public interface ArticlesContract {

    interface Presenter extends BasePresenter{
        void getArticles(int page, boolean forceUpdate, boolean clearCache);
    }

    interface View extends BaseView<Presenter>{

        boolean isActive();

        void showArticles(List<ArticleDetailData> list);


    }
}
