package wmding.example.com.mylivetools.base;

import android.view.View;

/**
 * Created by CoderLengary
 */


public interface BaseView<T> {
    void initViews(View view);

    void setPresenter(T presenter);
}
