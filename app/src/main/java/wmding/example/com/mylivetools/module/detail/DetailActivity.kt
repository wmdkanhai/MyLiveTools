package wmding.example.com.mylivetools.module.detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import wmding.example.com.mylivetools.R

/**
 * @author wmding
 * @date 2018/9/12
 */

class DetailActivity : AppCompatActivity() {

    private var mDetailFragment: DetailFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if (savedInstanceState != null) {
            mDetailFragment = supportFragmentManager.getFragment(savedInstanceState, DetailFragment::class.java!!.getSimpleName()) as DetailFragment
        } else {
            mDetailFragment = DetailFragment.newInstance()
        }


        supportFragmentManager.beginTransaction()
                .replace(R.id.view_pager, mDetailFragment, DetailFragment::class.java!!.getSimpleName())
                .commit()

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (mDetailFragment!!.isAdded) {
            supportFragmentManager.putFragment(outState, DetailFragment::class.java!!.getSimpleName(), mDetailFragment)
        }
    }

    companion object {

        val ID = "ID"
        val URL = "URL"
        val TITLE = "TITLE"
        val FROM_FAVORITE_FRAGMENT = "FROM_FAVORITE_FRAGMENT"
        val FROM_BANNER = "FROM_BANNER"
    }
}
