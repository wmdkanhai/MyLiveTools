package wmding.example.com.mylivetools.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by wmding on 2018/8/9.
 */
public abstract class BaseActivity<V,T extends BasePresenter<V>> extends AppCompatActivity {

    public T myPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myPresenter = createPresenter();
        myPresenter.attachView((V)this);
    }

    protected abstract T createPresenter();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        myPresenter.detachView();
        myPresenter = null;
    }
}
