package wmding.example.com.mylivetools.module.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import wmding.example.com.mylivetools.R;
import wmding.example.com.mylivetools.data.ArticlesDataRepository;
import wmding.example.com.mylivetools.data.remote.ArticlesDataRemoteSource;

public class SearchActivity extends AppCompatActivity {

    private SearchFragment mSearchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (savedInstanceState != null) {
            mSearchFragment = (SearchFragment) getSupportFragmentManager().getFragment(savedInstanceState, SearchFragment.class.getSimpleName());
        }else {
            mSearchFragment = SearchFragment.newInstance();
        }

        new SearchPresenter(ArticlesDataRepository.getInstance(ArticlesDataRemoteSource.getInstance()), mSearchFragment);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.view_pager, mSearchFragment, SearchFragment.class.getSimpleName())
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mSearchFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, SearchFragment.class.getSimpleName(), mSearchFragment);
        }
    }
}
