package wmding.example.com.mylivetools.module.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.*
import android.webkit.WebSettings
import android.widget.FrameLayout
import com.just.agentweb.AgentWeb
import wmding.example.com.mylivetools.R

/**
 * @author wmding
 * @date 2018/9/12
 * @describe
 */

class DetailFragment : Fragment(), DetailContract.View {

    private var mToolbar: Toolbar? = null
    private var mWebViewContainer: FrameLayout? = null

    private var url: String? = null
    private var title: String? = null
    private var mAgentWeb: AgentWeb? = null
    private var id: Int? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = activity!!.intent
        url = intent.getStringExtra(DetailActivity.URL)
        title = intent.getStringExtra(DetailActivity.TITLE)
        id = intent.getIntExtra(DetailActivity.ID, -1)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        initViews(view)

        loadUrl(url)
        mToolbar!!.title = title
        setHasOptionsMenu(true)

        return view
    }

    override fun initViews(view: View) {
        mToolbar = view.findViewById(R.id.toolBar)
        //设置Toolbar返回主页面
        val activity = activity as DetailActivity?
        activity!!.setSupportActionBar(mToolbar)
        val actionBar = activity.supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)

        mWebViewContainer = view.findViewById(R.id.web_view_container)
    }

    override fun onResume() {
        if (mAgentWeb != null) {
            mAgentWeb!!.webLifeCycle.onResume()
        }
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        if (mAgentWeb != null) {
            mAgentWeb!!.webLifeCycle.onPause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mAgentWeb != null) {
            mAgentWeb!!.webLifeCycle.onDestroy()
        }
    }

    override fun setPresenter(presenter: DetailContract.Presenter) {

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.detail_menu, menu)
    }

    /**
     * 加载webView
     * @param url
     */
    private fun loadUrl(url: String?) {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mWebViewContainer!!, FrameLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url)

        val webView = mAgentWeb!!.webCreator.webView
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.setSupportZoom(true)
        settings.builtInZoomControls = true
        settings.displayZoomControls = false
        settings.useWideViewPort = true
        settings.loadsImagesAutomatically = true
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> activity!!.onBackPressed()
            else -> {
            }
        }
        return true
    }

    companion object {


        fun newInstance(): DetailFragment {
            return DetailFragment()
        }
    }
}// Required empty public constructor
