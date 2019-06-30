package wmding.example.com.mylivetools.module.search

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import com.zhy.view.flowlayout.TagFlowLayout
import wmding.example.com.mylivetools.R
import wmding.example.com.mylivetools.bean.ArticleDetailData
import wmding.example.com.mylivetools.utils.NetworkUtil
import wmding.example.com.mylivetools.utils.adapter.CategoryAdapter
class SearchFragment : Fragment(), SearchContract.View {

    private var mSearchView: SearchView? = null
    private var mToolBar: Toolbar? = null
    private var mRecycler_view: RecyclerView? = null
    private var mLinearLayoutManager: LinearLayoutManager? = null
    private var mEmpty_view: LinearLayout? = null
    private var mFlowLayout: TagFlowLayout? = null

    private val INDEX = 0
    private var currentPage: Int = 0
    private var articlesListSize: Int = 0
    private var keyWords: String? = null

    private var adapter: CategoryAdapter? = null
    private var mPresenter: SearchContract.Presenter? = null

    override val isActive: Boolean
        get() = isAdded && isResumed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        initViews(view)

        mSearchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                //确认搜索后就隐藏键盘和热词布局，给文章列表腾出空间
                hideKeyboard()

                hideTagFlowLayout(true)

                mPresenter!!.searchArticles(INDEX, query, false, false)

                keyWords = query
                currentPage = INDEX
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })

        //滑动到底部加载下一页
        mRecycler_view!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    if (mLinearLayoutManager!!.findLastCompletelyVisibleItemPosition() == articlesListSize - 1) {
                        loadMore()
                    }
                }
            }
        })

        setHasOptionsMenu(true)
        return view
    }

    override fun initViews(view: View) {
        val searchActivity = activity as SearchActivity?

        mToolBar = view.findViewById(R.id.toolBar)
        searchActivity!!.setSupportActionBar(mToolBar)
        searchActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mSearchView = view.findViewById(R.id.search_view)
        mSearchView!!.isIconified = false


        mLinearLayoutManager = LinearLayoutManager(context)

        mRecycler_view = view.findViewById(R.id.recycler_view)
        mRecycler_view!!.layoutManager = mLinearLayoutManager

        mEmpty_view = view.findViewById(R.id.empty_view)
        mFlowLayout = view.findViewById(R.id.flow_layout)

    }

    override fun onResume() {
        super.onResume()
        mPresenter!!.subscribe()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                hideKeyboard()
                activity!!.onBackPressed()
            }
            else -> {
            }
        }
        return true
    }

    override fun setPresenter(presenter: SearchContract.Presenter) {
        this.mPresenter = presenter
    }

    private fun loadMore() {
        val isNetworkAvailable = NetworkUtil.isNetworkAvailable(context!!)
        if (isNetworkAvailable) {
            currentPage += 1
            mPresenter!!.searchArticles(currentPage, keyWords!!, true, false)
        } else {
            Toast.makeText(context, "Network is not available", Toast.LENGTH_SHORT).show()
        }
    }

    private fun hideTagFlowLayout(hide: Boolean) {
        if (hide) {
            mFlowLayout!!.visibility = View.GONE
            mRecycler_view!!.visibility = View.VISIBLE
        } else {
            mFlowLayout!!.visibility = View.VISIBLE
            mRecycler_view!!.visibility = View.INVISIBLE
        }
    }


    override fun hideKeyboard() {
        val manager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (manager.isActive) {
            manager.hideSoftInputFromWindow(mSearchView!!.windowToken, 0)
        }
    }

    override fun showArticles(articlesList: List<ArticleDetailData>) {
        if (articlesList.isEmpty()) {
            showEmptyView(true)
            return
        }

        showEmptyView(false)

        if (adapter == null) {
            adapter = CategoryAdapter(context, articlesList)
//            adapter!!.setItemClickListener { view, position ->
//                //                    Intent intent = new Intent(getContext(), DetailActivity.class);
//                //                    ArticleDetailData data = articlesList.get(position);
//                //                    intent.putExtra(DetailActivity.ID, data.getId());
//                //                    intent.putExtra(DetailActivity.URL, data.getLink());
//                //                    intent.putExtra(DetailActivity.TITLE, data.getTitle());
//                //                    intent.putExtra(DetailActivity.FROM_FAVORITE_FRAGMENT, false);
//                //                    intent.putExtra(DetailActivity.FROM_BANNER, false);
//                //                    startActivity(intent);
//            }
            mRecycler_view!!.adapter = adapter
        } else {
            adapter!!.updateData(articlesList)
        }
        articlesListSize = articlesList.size
    }

    override fun showEmptyView(isShow: Boolean) {
        mEmpty_view!!.visibility = if (isShow) View.VISIBLE else View.INVISIBLE
        mRecycler_view!!.visibility = if (!isShow) View.VISIBLE else View.INVISIBLE
    }

    override fun onPause() {
        super.onPause()
        mPresenter!!.unSubscribe()
    }

    override fun onStop() {
        super.onStop()

    }

    override fun onDestroy() {
        super.onDestroy()

    }

    companion object {


        fun newInstance(): SearchFragment {

            return SearchFragment()
        }
    }
}// Required empty public constructor
