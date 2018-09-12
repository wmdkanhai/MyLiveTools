package wmding.example.com.mylivetools.module.detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import wmding.example.com.mylivetools.R;

/**
 * @author wmding
 * @date 2018/9/12
 */

public class DetailActivity extends AppCompatActivity {

    public static final String ID = "ID";
    public static final String URL = "URL";
    public static final String TITLE = "TITLE";
    public static final String FROM_FAVORITE_FRAGMENT = "FROM_FAVORITE_FRAGMENT";
    public static final String FROM_BANNER = "FROM_BANNER";

    private DetailFragment mDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState != null) {
            mDetailFragment = (DetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, DetailFragment.class.getSimpleName());
        } else {
            mDetailFragment = DetailFragment.newInstance();
        }


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.view_pager, mDetailFragment, DetailFragment.class.getSimpleName())
                .commit();

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mDetailFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, DetailFragment.class.getSimpleName(), mDetailFragment);
        }
    }
}
