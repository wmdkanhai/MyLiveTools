package wmding.example.com.mylivetools.fragment.first;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import wmding.example.com.mylivetools.R;
import wmding.example.com.mylivetools.bean.ArticleDetailData;
import wmding.example.com.mylivetools.data.ArticlesDataRepository;
import wmding.example.com.mylivetools.data.remote.ArticlesDataRemoteSource;
import wmding.example.com.mylivetools.interfaze.OnRecyclerViewItemOnClickListener;
import wmding.example.com.mylivetools.module.detail.DetailActivity;
import wmding.example.com.mylivetools.utils.NetworkUtil;

public class FirstFragment extends Fragment implements ArticlesContract.View {

    private NestedScrollView nestedScrollView;
    private RecyclerView recyclerView;
    private LinearLayout emptyView;
    private SwipeRefreshLayout refreshLayout;
    private LinearLayoutManager layoutManager;

    private ArticlesContract.Presenter presenter;

    private ArticlesAdapter adapter;


    private final int INDEX = 0;
    private int currentPage;
    private int articlesListSize;


    private boolean isFirstLoad = true;


    public FirstFragment() {

    }

    public static FirstFragment newInstance() {
        FirstFragment fragment = new FirstFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new ArticlesPresenter(this, ArticlesDataRepository.getInstance(ArticlesDataRemoteSource.getInstance()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        initViews(view);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = INDEX;
                //获取文章信息
                presenter.getArticles(INDEX, true, true);
            }
        });

        //滑动到底部加载下一页
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    loadMore();
                }
            }
        });


        return view;
    }


    private void loadMore() {
        boolean isNetworkAvailable = NetworkUtil.isNetworkAvailable(getContext());
        if (isNetworkAvailable) {
            currentPage += 1;
            presenter.getArticles(currentPage, true, false);
        } else {
            Toast.makeText(getContext(), "Network is not available", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void initViews(View view) {
        emptyView = view.findViewById(R.id.empty_view);
        layoutManager = new LinearLayoutManager(getContext());
        nestedScrollView = view.findViewById(R.id.nested_scroll_view);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.INVISIBLE);
        refreshLayout = view.findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        refreshLayout.setRefreshing(true);

    }

    @Override
    public void setPresenter(ArticlesContract.Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void onResume() {
        super.onResume();

        //根据是否是首次加载，如果是首次加载，就从首页加载，否则从当前页加载
        if (isFirstLoad) {

            presenter.getArticles(INDEX, true, true);
            currentPage = INDEX;
            isFirstLoad = false;
        } else {
            presenter.getArticles(currentPage, false, false);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unSubscribe();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean isActive() {
        return isAdded() && isResumed();
    }

    @Override
    public void showArticles(final List<ArticleDetailData> list) {
        if (adapter != null) {
            adapter.updateData(list);
        } else {
            adapter = new ArticlesAdapter(getContext(), list);
            adapter.setItemClickListener(new OnRecyclerViewItemOnClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Intent intent = new Intent(getContext(), DetailActivity.class);
                    ArticleDetailData data = list.get(position);
                    intent.putExtra(DetailActivity.URL, data.getLink());
                    intent.putExtra(DetailActivity.TITLE, data.getTitle());
                    intent.putExtra(DetailActivity.ID, data.getId());
                    intent.putExtra(DetailActivity.FROM_FAVORITE_FRAGMENT, false);
                    intent.putExtra(DetailActivity.FROM_BANNER, false);
                    startActivity(intent);
                }
            });
            adapter.setHeaderView(null);
            recyclerView.setAdapter(adapter);
        }
        articlesListSize = list.size();

    }

    @Override
    public void setLoadingIndicator(final boolean isActive) {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(isActive);
            }
        });
    }


}
