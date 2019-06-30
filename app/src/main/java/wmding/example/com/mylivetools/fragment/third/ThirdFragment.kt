package wmding.example.com.mylivetools.fragment.third

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import wmding.example.com.mylivetools.R
import wmding.example.com.mylivetools.bean.CategoryResult
import wmding.example.com.mylivetools.interfaze.OnRecyclerViewItemOnClickListener
import wmding.example.com.mylivetools.utils.NetworkUtil


class ThirdFragment : Fragment(), ThirdContract.View {

    private var mRecyclerViewImage: RecyclerView? = null
    private var refreshLayout: SwipeRefreshLayout? = null
    private var nestedScrollView: NestedScrollView? = null

    private var mPresenter: ThirdContract.Presenter? = null
    private var currentPage: Int = 0
    private val category = "福利"
    private var mImageAdapter: ImageAdapter? = null

    override val isActive: Boolean
        get() = isAdded && isResumed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_third, container, false)

        initViews(view)

        return view
    }


    override fun initViews(view: View) {
        mRecyclerViewImage = view.findViewById(R.id.recycler_view_image)
        refreshLayout = view.findViewById(R.id.refresh_layout)
        refreshLayout!!.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorPrimary))
        refreshLayout!!.isRefreshing = true

        nestedScrollView = view.findViewById(R.id.nested_scroll_view)

        refreshLayout!!.setOnRefreshListener {
            currentPage = 0
            mPresenter!!.getImages(false, category, 10, currentPage)
        }

        //滑动到底部加载下一页
        nestedScrollView!!.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                loadMore()
            }
        })

        initData()
    }

    private fun initData() {

        mPresenter = ThirdPresenter(this)
        mPresenter!!.getImages(false, category, 10, 0)

    }

    private fun loadMore() {
        val isNetworkAvailable = NetworkUtil.isNetworkAvailable(context!!)
        if (isNetworkAvailable) {
            currentPage += 1
            mPresenter!!.getImages(true, category, 10, currentPage)
        } else {
            Toast.makeText(context, "Network is not available", Toast.LENGTH_LONG).show()
        }

    }

    override fun showImages(categoryResult: CategoryResult) {
        mImageAdapter = ImageAdapter(context, categoryResult.results)

        mImageAdapter!!.listener = object : OnRecyclerViewItemOnClickListener {
            override fun onClick(view: View, position: Int) {
                Log.e(TAG, "点击的图片的地址：" + categoryResult.results!![position])

                val url = categoryResult.results!![position].url
                toImageDetailActivity(url)
            }
        }
        //瀑布效果
        mRecyclerViewImage!!.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mRecyclerViewImage!!.adapter = mImageAdapter

    }

    override fun addImages(categoryResult: CategoryResult) {
        mImageAdapter!!.addImages(categoryResult.results)
    }


    private fun toImageDetailActivity(url: String) {
        val intent = Intent()
        intent.putExtra("imageUrl", url)
        intent.setClass(context!!.applicationContext, ImageDetailActivity::class.java!!)
        startActivity(intent)
    }


    override fun setPresenter(presenter: ThirdContract.Presenter) {

        this.mPresenter = presenter
    }

    override fun setLoadingIndicator(isActive: Boolean) {
        refreshLayout!!.post { refreshLayout!!.isRefreshing = isActive }
    }

    companion object {

        private val TAG = "ThirdFragment"

        fun newInstance(): ThirdFragment {
            return ThirdFragment()
        }
    }

}// Required empty public constructor
