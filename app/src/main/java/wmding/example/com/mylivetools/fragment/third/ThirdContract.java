package wmding.example.com.mylivetools.fragment.third;

import wmding.example.com.mylivetools.base.BasePresenter;
import wmding.example.com.mylivetools.base.BaseView;
import wmding.example.com.mylivetools.bean.CategoryResult;

/**
 * @author wmding
 * @date 2018/12/24
 * @describe
 */
public interface ThirdContract {

    interface Presenter extends BasePresenter {

        void getImages(boolean isRefresh, String category, int count, int page);
    }


    interface View extends BaseView<Presenter> {

        boolean isActive();

        void showImages(CategoryResult categoryResult);

        void addImages(CategoryResult categoryResult);

        /**
         * 设置下拉加载的控件
         *
         * @param isShow 是否显示
         */
        void setLoadingIndicator(boolean isShow);
    }
}
