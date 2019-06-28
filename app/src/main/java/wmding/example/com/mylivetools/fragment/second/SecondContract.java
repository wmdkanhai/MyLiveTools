package wmding.example.com.mylivetools.fragment.second;

import wmding.example.com.mylivetools.base.BasePresenter;
import wmding.example.com.mylivetools.base.BaseView;
import wmding.example.com.mylivetools.bean.CategoryResult;

/**
 * @author wmding
 * @date 2019/6/28
 * @describe
 */
public interface SecondContract {

    interface Presenter extends BasePresenter {

        /**
         * 获取条目数据
         *
         * @param page
         * @param count
         */
        void getItemData(boolean isRefresh, int page, int count);
    }


    interface View extends BaseView<Presenter> {

        boolean isActive();

        void showItem(CategoryResult categoryResult);

        void addItem(CategoryResult categoryResult);

        void setLoadingIndicator(boolean isShow);

    }


}
