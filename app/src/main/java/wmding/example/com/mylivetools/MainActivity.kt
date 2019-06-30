package wmding.example.com.mylivetools

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast

import wmding.example.com.mylivetools.activity.Main2Activity
import wmding.example.com.mylivetools.fragment.first.FirstFragment
import wmding.example.com.mylivetools.fragment.second.SecondFragment
import wmding.example.com.mylivetools.fragment.third.ThirdFragment
import wmding.example.com.mylivetools.module.about.AboutActivity

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var navigationView: NavigationView? = null
    private var drawerLayout: DrawerLayout? = null
    private var toolbar: Toolbar? = null

    private var bottomNavigationView: BottomNavigationView? = null
    private var mFirstFragment: FirstFragment? = null
    private var mSecondFragment: SecondFragment? = null
    private var mThirdFragment: ThirdFragment? = null

    /**
     * 保存用户按返回键的时间
     */
    private var mExitTime: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initFragments(savedInstanceState)

        if (savedInstanceState != null) {
            val selectId = savedInstanceState.getInt(KEY_BOTTOM_NAVIGATION_VIEW_SELECTED_ID)
            when (selectId) {
                R.id.nav_first -> showFragment(mFirstFragment)
                R.id.nav_second -> showFragment(mSecondFragment)
                R.id.nav_third -> showFragment(mThirdFragment)
                else -> {
                }
            }
        } else {
            showFragment(mFirstFragment)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_BOTTOM_NAVIGATION_VIEW_SELECTED_ID, bottomNavigationView!!.selectedItemId)
        val fragmentManager = supportFragmentManager
        if (mFirstFragment!!.isAdded) {
            fragmentManager.putFragment(outState, FirstFragment::class.java!!.getSimpleName(), mFirstFragment)
        } else if (mSecondFragment!!.isAdded) {
            fragmentManager.putFragment(outState, SecondFragment::class.java!!.getSimpleName(), mSecondFragment)
        } else if (mThirdFragment!!.isAdded) {
            fragmentManager.putFragment(outState, ThirdFragment::class.java!!.getSimpleName(), mThirdFragment)
        }

    }

    private fun initView() {
        toolbar = findViewById(R.id.toolBar)
        setSupportActionBar(toolbar)
        navigationView = findViewById(R.id.nav_view)
        drawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout!!.addDrawerListener(toggle)
        toggle.syncState()
        navigationView!!.setNavigationItemSelectedListener(this)

        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView!!.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_first -> showFragment(mFirstFragment)
                R.id.nav_second -> showFragment(mSecondFragment)
                R.id.nav_third -> showFragment(mThirdFragment)
                else -> {
                }
            }

            true
        }

        // 设置navigationView 上头布局，个人信息
        val headerView = navigationView!!.getHeaderView(0)
        val textUserIcon = headerView.findViewById<TextView>(R.id.text_user_icon)
        textUserIcon.text = "wmding"
        val textUserName = headerView.findViewById<TextView>(R.id.text_user_name)
        textUserName.text = "小明"

    }

    private fun initFragments(savedInstanceState: Bundle?) {
        val fragmentManager = supportFragmentManager

        if (savedInstanceState != null) {
            mFirstFragment = fragmentManager.getFragment(savedInstanceState, FirstFragment::class.java!!.getSimpleName()) as FirstFragment
            mSecondFragment = fragmentManager.getFragment(savedInstanceState, SecondFragment::class.java!!.getSimpleName()) as SecondFragment
            mThirdFragment = fragmentManager.getFragment(savedInstanceState, ThirdFragment::class.java!!.getSimpleName()) as ThirdFragment
        } else {
            mFirstFragment = FirstFragment.newInstance()
            mSecondFragment = SecondFragment.newInstance()
            mThirdFragment = ThirdFragment.newInstance()
        }

        if (!mFirstFragment!!.isAdded) {
            fragmentManager.beginTransaction()
                    .add(R.id.frame_layout, mFirstFragment, FirstFragment::class.java!!.getSimpleName())
                    .commit()
        }

        if (!mSecondFragment!!.isAdded) {
            fragmentManager.beginTransaction()
                    .add(R.id.frame_layout, mSecondFragment, SecondFragment::class.java!!.getSimpleName())
                    .commit()
        }

        if (!mThirdFragment!!.isAdded) {
            fragmentManager.beginTransaction()
                    .add(R.id.frame_layout, mThirdFragment, ThirdFragment::class.java!!.getSimpleName())
                    .commit()
        }

    }

    private fun showFragment(fragment: Fragment?) {
        val fragmentManager = supportFragmentManager
        if (fragment is FirstFragment) {
            fragmentManager.beginTransaction()
                    .show(mFirstFragment)
                    .hide(mSecondFragment)
                    .hide(mThirdFragment)
                    .commit()
            setTitle(R.string.nav_first_name)
        } else if (fragment is SecondFragment) {
            fragmentManager.beginTransaction()
                    .show(mSecondFragment)
                    .hide(mFirstFragment)
                    .hide(mThirdFragment)
                    .commit()
            setTitle(R.string.nav_second_name)
        } else if (fragment is ThirdFragment) {
            fragmentManager.beginTransaction()
                    .show(mThirdFragment)
                    .hide(mFirstFragment)
                    .hide(mSecondFragment)
                    .commit()
            setTitle(R.string.nav_third_name)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                val intent = Intent(this, Main2Activity::class.java)
                startActivity(intent)
            }
            else -> {
            }
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        item.isCheckable = false
        drawerLayout!!.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.nav_sign_out -> finish()
            R.id.nav_about_me -> startActivity(Intent(this, AboutActivity::class.java))
            else -> {
            }
        }

        return true
    }


    /**
     * 设置连续点击返回后退出该应用
     */
    override fun onBackPressed() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            // 使用Snackbar控件在页面底部进行提示
            Snackbar.make(drawerLayout!!, "再按一次退出程序哦~", Toast.LENGTH_SHORT).show()
            mExitTime = System.currentTimeMillis()
        } else {
            finish()
        }

    }

    companion object {

        private val KEY_BOTTOM_NAVIGATION_VIEW_SELECTED_ID = "KEY_BOTTOM_NAVIGATION_VIEW_SELECTED_ID"
    }
}
