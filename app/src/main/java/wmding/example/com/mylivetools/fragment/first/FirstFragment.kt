package wmding.example.com.mylivetools.fragment.first


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import wmding.example.com.mylivetools.R
import wmding.example.com.mylivetools.bean.ArticleDetailData
import wmding.example.com.mylivetools.utils.NetworkUtil


class FirstFragment : Fragment(), ArticlesContract.View {




    override fun showArticles(list: List<ArticleDetailData>) {



        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var nestedScrollView: NestedScrollView? = null
    private var recyclerView: RecyclerView? = null
    private var emptyView: LinearLayout? = null
    private var refreshLayout: SwipeRefreshLayout? = null
    private var layoutManager: LinearLayoutManager? = null



    private val INDEX = 0
    private var currentPage: Int = 0
    private var articlesListSize: Int = 0


    private var isFirstLoad = true

    override val isActive: Boolean
        get() = isAdded && isResumed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        initViews(view)

        refreshLayout!!.setOnRefreshListener {
            currentPage = INDEX
            //获取文章信息
        }

        //滑动到底部加载下一页
        nestedScrollView!!.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                loadMore()
            }
        })


        return view
    }


    private fun loadMore() {
        val isNetworkAvailable = NetworkUtil.isNetworkAvailable(context!!)
        if (isNetworkAvailable) {
            currentPage += 1




        } else {
            Toast.makeText(context, "Network is not available", Toast.LENGTH_LONG).show()
        }

    }

    override fun initViews(view: View) {
        emptyView = view.findViewById(R.id.empty_view)
        layoutManager = LinearLayoutManager(context)
        nestedScrollView = view.findViewById(R.id.nested_scroll_view)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.visibility = View.VISIBLE
        emptyView!!.visibility = View.INVISIBLE
        refreshLayout = view.findViewById(R.id.refresh_layout)
        refreshLayout!!.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorPrimary))
        refreshLayout!!.isRefreshing = true

    }

    override fun setPresenter(presenter: ArticlesContract.Presenter) {

    }


    override fun onResume() {
        super.onResume()


    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()

    }

    override fun onDestroy() {
        super.onDestroy()

    }


    override fun setLoadingIndicator(isActive: Boolean) {
        refreshLayout!!.post { refreshLayout!!.isRefreshing = isActive }
    }

    companion object {

        fun newInstance(): FirstFragment {
            return FirstFragment()
        }
    }


}
