package wmding.example.com.mylivetools.module.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.just.agentweb.AgentWeb;

import wmding.example.com.mylivetools.R;

/**
 * @author wmding
 * @date 2018/9/12
 * @describe
 */

public class DetailFragment extends Fragment implements DetailContract.View {

    private Toolbar mToolbar;
    private FrameLayout mWebViewContainer;

    private String url;
    private String title;
    private int id;
    private AgentWeb mAgentWeb;

    public DetailFragment() {
        // Required empty public constructor
    }


    public static DetailFragment newInstance() {
        DetailFragment fragment = new DetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();
        url = intent.getStringExtra(DetailActivity.URL);
        title = intent.getStringExtra(DetailActivity.TITLE);
        id = intent.getIntExtra(DetailActivity.ID, -1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        initViews(view);

        loadUrl(url);
        mToolbar.setTitle(title);
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void initViews(View view) {
        mToolbar = view.findViewById(R.id.toolBar);
        //设置Toolbar返回主页面
        DetailActivity activity = (DetailActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

        mWebViewContainer = view.findViewById(R.id.web_view_container);
    }

    @Override
    public void onResume() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onResume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_menu, menu);
    }

    /**
     * 加载webView
     * @param url
     */
    private void loadUrl(String url) {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mWebViewContainer, new FrameLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url);

        WebView webView = mAgentWeb.getWebCreator().getWebView();
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
            default:
                break;
        }
        return true;
    }
}
