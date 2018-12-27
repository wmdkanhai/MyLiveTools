package wmding.example.com.mylivetools.fragment.third;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wmding.example.com.mylivetools.R;
import wmding.example.com.mylivetools.bean.CategoryResult;


public class ThirdFragment extends Fragment implements ThirdContract.View {


    private ThirdContract.Presenter mPresenter;
    private RecyclerView mRecyclerViewImage;
    private SwipeRefreshLayout refreshLayout;

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

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = INDEX;
                mPresenter.getImages(category, 10, currentPage);
            }
        });

        return view;
    }

    private void loadMore() {

    }

    @Override
    public boolean isActive() {
        return isAdded() && isResumed();
    }

    @Override
    public void showImages(CategoryResult categoryResult) {
        ImageAdapter imageAdapter = new ImageAdapter(getContext(), categoryResult);

        //瀑布效果
        mRecyclerViewImage.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerViewImage.setAdapter(imageAdapter);

    }

    @Override
    public void initViews(View view) {
        mRecyclerViewImage = view.findViewById(R.id.recycler_view_image);
        refreshLayout = view.findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        refreshLayout.setRefreshing(true);

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
