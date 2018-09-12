package wmding.example.com.mylivetools.module.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import wmding.example.com.mylivetools.R;
import wmding.example.com.mylivetools.bean.ArticleDetailData;
import wmding.example.com.mylivetools.interfaze.OnRecyclerViewItemOnClickListener;
import wmding.example.com.mylivetools.utils.NetworkUtil;
import wmding.example.com.mylivetools.utils.adapter.CategoryAdapter;

public class SearchFragment extends Fragment implements SearchContract.View {

    private SearchView mSearchView;
    private Toolbar mToolBar;
    private RecyclerView mRecycler_view;
    private LinearLayoutManager mLinearLayoutManager;
    private LinearLayout mEmpty_view;
    private TagFlowLayout mFlowLayout;

    private final int INDEX = 0;
    private int currentPage;
    private int articlesListSize;
    private String keyWords;

    private CategoryAdapter adapter;
    private SearchContract.Presenter mPresenter;

    public SearchFragment() {
        // Required empty public constructor
    }


    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initViews(view);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //确认搜索后就隐藏键盘和热词布局，给文章列表腾出空间
                hideKeyboard();

                hideTagFlowLayout(true);

                mPresenter.searchArticles(INDEX,query,false,false);

                keyWords = query;
                currentPage = INDEX;
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        //滑动到底部加载下一页
        mRecycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    if (mLinearLayoutManager.findLastCompletelyVisibleItemPosition() == articlesListSize - 1) {
                        loadMore();
                    }
                }
            }
        });

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void initViews(View view) {
        SearchActivity searchActivity = (SearchActivity) getActivity();

        mToolBar = view.findViewById(R.id.toolBar);
        searchActivity.setSupportActionBar(mToolBar);
        searchActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSearchView = view.findViewById(R.id.search_view);
        mSearchView.setIconified(false);


        mLinearLayoutManager = new LinearLayoutManager(getContext());

        mRecycler_view = view.findViewById(R.id.recycler_view);
        mRecycler_view.setLayoutManager(mLinearLayoutManager);

        mEmpty_view = view.findViewById(R.id.empty_view);
        mFlowLayout = view.findViewById(R.id.flow_layout);

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                hideKeyboard();
                getActivity().onBackPressed();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    private void loadMore() {
        boolean isNetworkAvailable = NetworkUtil.isNetworkAvailable(getContext());
        if (isNetworkAvailable) {
            currentPage += 1;
            mPresenter.searchArticles(currentPage, keyWords, true, false);
        } else {
            Toast.makeText(getContext(), "Network is not available", Toast.LENGTH_SHORT).show();
        }
    }

    private void hideTagFlowLayout(boolean hide) {
        if (hide) {
            mFlowLayout.setVisibility(View.GONE);
            mRecycler_view.setVisibility(View.VISIBLE);
        }else {
            mFlowLayout.setVisibility(View.VISIBLE);
            mRecycler_view.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void hideKeyboard() {
        InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager.isActive()) {
            manager.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
        }
    }

    @Override
    public boolean isActive() {
        return isAdded() && isResumed();
    }

    @Override
    public void showArticles(final List<ArticleDetailData> articlesList) {
        if (articlesList.isEmpty()){
            showEmptyView(true);
            return;
        }

        showEmptyView(false);

        if (adapter == null) {
            adapter = new CategoryAdapter(getContext(),articlesList);
            adapter.setItemClickListener(new OnRecyclerViewItemOnClickListener() {
                @Override
                public void onClick(View view, int position) {
//                    Intent intent = new Intent(getContext(), DetailActivity.class);
//                    ArticleDetailData data = articlesList.get(position);
//                    intent.putExtra(DetailActivity.ID, data.getId());
//                    intent.putExtra(DetailActivity.URL, data.getLink());
//                    intent.putExtra(DetailActivity.TITLE, data.getTitle());
//                    intent.putExtra(DetailActivity.FROM_FAVORITE_FRAGMENT, false);
//                    intent.putExtra(DetailActivity.FROM_BANNER, false);
//                    startActivity(intent);
                }
            });
            mRecycler_view.setAdapter(adapter);
        }else {
            adapter.updateData(articlesList);
        }
        articlesListSize = articlesList.size();
    }

    @Override
    public void showEmptyView(boolean isShow) {
        mEmpty_view.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
        mRecycler_view.setVisibility(!isShow ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unSubscribe();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
