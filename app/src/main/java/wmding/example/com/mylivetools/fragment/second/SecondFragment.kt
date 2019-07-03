package wmding.example.com.mylivetools.fragment.second

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_second.*
import wmding.example.com.mylivetools.R
import wmding.example.com.mylivetools.bean.CategoryResult
import wmding.example.com.mylivetools.interfaze.OnRecyclerViewItemOnClickListener
import wmding.example.com.mylivetools.module.detail.DetailActivity
import wmding.example.com.mylivetools.utils.NetworkUtil


class SecondFragment : Fragment(), SecondContract.View {

    private var mRefreshLayout: SwipeRefreshLayout? = null
    private var mNestedScrollView: NestedScrollView? = null

    private var currentPage = 0
    private var mSecondPresenter: SecondContract.Presenter? = null
    private var mSecondAdapter: SecondAdapter? = null

    companion object {

        fun newInstance(): SecondFragment {
            return SecondFragment()
        }

        const val COUNT = 10
        const val INDEX = 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_second, container, false)

        initViews(view)
        return view
    }

    override val isActive: Boolean
        get() = isAdded && isResumed


    override fun initViews(view: View) {

        mRefreshLayout = view.findViewById(R.id.refresh_layout)
        mRefreshLayout!!.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorPrimary))
        mRefreshLayout!!.isRefreshing = true
        // 下拉刷新
        mRefreshLayout!!.setOnRefreshListener {
            currentPage = INDEX
            mSecondPresenter!!.getItemData(false, currentPage, COUNT)
        }

        mNestedScrollView = view.findViewById(R.id.nested_scroll_view)
        // 上拉加载
        mNestedScrollView!!.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                loadMore()
            }
        })


        initData()

    }

    private fun initData() {
        mSecondPresenter = SecondPresenter(this)
        mSecondPresenter!!.getItemData(false, INDEX, COUNT)

    }


    private fun loadMore() {
        val isNetworkAvailable = NetworkUtil.isNetworkAvailable(context!!)
        if (isNetworkAvailable) {
            currentPage += 1
            mSecondPresenter!!.getItemData(true, currentPage, COUNT)
        } else {
            Toast.makeText(context, "Network is not available", Toast.LENGTH_LONG).show()
        }

    }


    override fun showItem(categoryResult: CategoryResult) {
        val linearLayoutManager = LinearLayoutManager(context)
        recycler_view!!.layoutManager = linearLayoutManager
        mSecondAdapter = SecondAdapter(context, categoryResult.results)
        recycler_view!!.adapter = mSecondAdapter
        mSecondAdapter!!.listener = object : OnRecyclerViewItemOnClickListener {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(context, DetailActivity::class.java)
                val data = categoryResult.results!![position]
                intent.putExtra(DetailActivity.URL, data.url)
                intent.putExtra(DetailActivity.TITLE, data.desc)
                intent.putExtra(DetailActivity.ID, data.id)
                intent.putExtra(DetailActivity.FROM_FAVORITE_FRAGMENT, false)
                intent.putExtra(DetailActivity.FROM_BANNER, false)
                startActivity(intent)
            }
        }

    }

    override fun addItem(categoryResult: CategoryResult) {
        mSecondAdapter!!.addData(categoryResult.results)
    }

    override fun setLoadingIndicator(isShow: Boolean) {

        mRefreshLayout!!.post { mRefreshLayout!!.isRefreshing = isShow }
    }


    override fun setPresenter(presenter: SecondContract.Presenter) {
        this.mSecondPresenter = presenter
    }
}
