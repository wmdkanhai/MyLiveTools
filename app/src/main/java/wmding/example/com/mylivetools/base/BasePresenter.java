package wmding.example.com.mylivetools.base;

import java.lang.ref.WeakReference;

/**
 * Created by wmding on 2018/8/23.
 */
public class BasePresenter<T> {

    protected WeakReference<T> mViewRef;

    //进行绑定
    public void attachView(T view){
        mViewRef = new WeakReference<T>(view);
    }

    //进行解绑
    public void detachView(){
        mViewRef.clear();
    }
}
