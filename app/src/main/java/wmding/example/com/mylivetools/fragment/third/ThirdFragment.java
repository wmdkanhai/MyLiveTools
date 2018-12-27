package wmding.example.com.mylivetools.fragment.third;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import wmding.example.com.mylivetools.R;
import wmding.example.com.mylivetools.bean.CategoryResult;
import wmding.example.com.mylivetools.interfaze.OnRecyclerViewItemOnClickListener;
import wmding.example.com.mylivetools.utils.NetworkUtil;


public class ThirdFragment extends Fragment implements ThirdContract.View {

    private static final String TAG = "ThirdFragment";

    private RecyclerView mRecyclerViewImage;
    private SwipeRefreshLayout refreshLayout;
    private NestedScrollView nestedScrollView;

    private ThirdContract.Presenter mPresenter;
    private final int INDEX = 0;
    private int currentPage;
    private boolean isFirstLoad = true;
    private String category = "福利";


    public ThirdFragment() {
        // Required empty public constructor
    }

    public static ThirdFragment newInstance() {
        ThirdFragment fragment = new ThirdFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);

        initViews(view);

        return view;
    }


    @Override
    public void initViews(View view) {
        mRecyclerViewImage = view.findViewById(R.id.recycler_view_image);
        refreshLayout = view.findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        refreshLayout.setRefreshing(true);

        nestedScrollView = view.findViewById(R.id.nested_scroll_view);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = INDEX;
                mPresenter.getImages(category, 10, currentPage);
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


        mPresenter = new ThirdPresenter(this);

        initData();
    }

    private void initData() {

        //根据是否是首次加载，如果是首次加载，就从首页加载，否则从当前页加载
        if (isFirstLoad) {
            mPresenter.getImages(category, 10, INDEX);

            currentPage = INDEX;
            isFirstLoad = false;
        } else {
            mPresenter.getImages(category, 10, currentPage);
        }
    }

    private void loadMore() {
        boolean isNetworkAvailable = NetworkUtil.isNetworkAvailable(getContext());
        if (isNetworkAvailable) {
            currentPage += 1;
            mPresenter.getImages(category, 10, currentPage);
        } else {
            Toast.makeText(getContext(), "Network is not available", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean isActive() {
        return isAdded() && isResumed();
    }

    @Override
    public void showImages(final CategoryResult categoryResult) {
        ImageAdapter imageAdapter = new ImageAdapter(getContext(), categoryResult);

        imageAdapter.setListener(new OnRecyclerViewItemOnClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.e(TAG,"点击的图片的地址："+categoryResult.results.get(position));

                String url = categoryResult.results.get(position).getUrl();
                toImageDetailActivity(url);
            }
        });
        //瀑布效果
        mRecyclerViewImage.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerViewImage.setAdapter(imageAdapter);

    }


    private void toImageDetailActivity(String url) {
        Intent intent = new Intent();
        intent.putExtra("imageUrl",url);
        intent.setClass(getContext().getApplicationContext(),ImageDetailActivity.class);
        startActivity(intent);
    }


    @Override
    public void setPresenter(ThirdContract.Presenter presenter) {

        this.mPresenter = presenter;
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
