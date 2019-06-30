package wmding.example.com.mylivetools.module.search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import wmding.example.com.mylivetools.R

class SearchActivity : AppCompatActivity() {

    private var mSearchFragment: SearchFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        if (savedInstanceState != null) {
            mSearchFragment = supportFragmentManager.getFragment(savedInstanceState, SearchFragment::class.java!!.getSimpleName()) as SearchFragment
        } else {
            mSearchFragment = SearchFragment.newInstance()
        }



        supportFragmentManager.beginTransaction()
                .replace(R.id.view_pager, mSearchFragment, SearchFragment::class.java!!.getSimpleName())
                .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (mSearchFragment!!.isAdded) {
            supportFragmentManager.putFragment(outState, SearchFragment::class.java!!.getSimpleName(), mSearchFragment)
        }
    }
}
