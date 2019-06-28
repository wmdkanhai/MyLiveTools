package wmding.example.com.mylivetools.fragment.second;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import wmding.example.com.mylivetools.R;
import wmding.example.com.mylivetools.bean.CategoryResult;
import wmding.example.com.mylivetools.interfaze.OnRecyclerViewItemOnClickListener;
import wmding.example.com.mylivetools.module.detail.DetailActivity;
import wmding.example.com.mylivetools.utils.NetworkUtil;


public class SecondFragment extends Fragment implements SecondContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;
    @BindView(R.id.empty_view)
    LinearLayout emptyView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    Unbinder unbinder;


    private final int INDEX = 0;
    private int currentPage;
    private final int COUNT = 10;
    private SecondContract.Presenter mSecondPresenter;
    private boolean isFirstLoad;
    private SecondAdapter mSecondAdapter;


    public SecondFragment() {
        // Required empty public constructor
    }

    public static SecondFragment newInstance() {
        SecondFragment fragment = new SecondFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        initViews(view);
        return view;
    }


    @Override
    public void initViews(View view) {
        unbinder = ButterKnife.bind(this, view);

        refreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        refreshLayout.setRefreshing(true);

        // 下拉刷新
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = INDEX;
                mSecondPresenter.getItemData(false, currentPage, COUNT);

            }
        });

        // 上拉加载
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    loadMore();
                }
            }
        });


        initData();

    }

    private void initData() {
        mSecondPresenter = new SecondPresenter(this);
        mSecondPresenter.getItemData(false, INDEX, COUNT);
    }


    private void loadMore() {
        boolean isNetworkAvailable = NetworkUtil.isNetworkAvailable(getContext());
        if (isNetworkAvailable) {
            currentPage += 1;
            mSecondPresenter.getItemData(true, currentPage, COUNT);
        } else {
            Toast.makeText(getContext(), "Network is not available", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public boolean isActive() {
        return isAdded() && isResumed();
    }

    @Override
    public void showItem(final CategoryResult categoryResult) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        mSecondAdapter = new SecondAdapter(getContext(), categoryResult.results);
        recyclerView.setAdapter(mSecondAdapter);
        mSecondAdapter.setListener(new OnRecyclerViewItemOnClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                CategoryResult.ResultsBean data = categoryResult.results.get(position);
                intent.putExtra(DetailActivity.URL, data.getUrl());
                intent.putExtra(DetailActivity.TITLE, data.getDesc());
                intent.putExtra(DetailActivity.ID, data.get_id());
                intent.putExtra(DetailActivity.FROM_FAVORITE_FRAGMENT, false);
                intent.putExtra(DetailActivity.FROM_BANNER, false);
                startActivity(intent);

            }
        });

    }

    @Override
    public void addItem(CategoryResult categoryResult) {
        mSecondAdapter.addData(categoryResult.results);
    }

    @Override
    public void setLoadingIndicator(final boolean isShow) {

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(isShow);
            }
        });
    }


    @Override
    public void setPresenter(SecondContract.Presenter presenter) {
        this.mSecondPresenter = presenter;
    }
}
