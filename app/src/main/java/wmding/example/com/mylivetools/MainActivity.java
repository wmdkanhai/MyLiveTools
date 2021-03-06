package wmding.example.com.mylivetools;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import wmding.example.com.mylivetools.activity.Main2Activity;
import wmding.example.com.mylivetools.fragment.first.FirstFragment;
import wmding.example.com.mylivetools.fragment.second.SecondFragment;
import wmding.example.com.mylivetools.fragment.third.ThirdFragment;
import wmding.example.com.mylivetools.module.about.AboutActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String KEY_BOTTOM_NAVIGATION_VIEW_SELECTED_ID = "KEY_BOTTOM_NAVIGATION_VIEW_SELECTED_ID";

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    private BottomNavigationView bottomNavigationView;
    private FirstFragment mFirstFragment;
    private SecondFragment mSecondFragment;
    private ThirdFragment mThirdFragment;

    /**
     * 保存用户按返回键的时间
     */
    private long mExitTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initFragments(savedInstanceState);

        if (savedInstanceState != null) {
            int selectId = savedInstanceState.getInt(KEY_BOTTOM_NAVIGATION_VIEW_SELECTED_ID);
            switch (selectId) {
                case R.id.nav_first:
                    showFragment(mFirstFragment);
                    break;
                case R.id.nav_second:
                    showFragment(mSecondFragment);
                    break;
                case R.id.nav_third:
                    showFragment(mThirdFragment);
                    break;
                default:
                    break;
            }
        } else {
            showFragment(mFirstFragment);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_BOTTOM_NAVIGATION_VIEW_SELECTED_ID, bottomNavigationView.getSelectedItemId());
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (mFirstFragment.isAdded()) {
            fragmentManager.putFragment(outState, FirstFragment.class.getSimpleName(), mFirstFragment);
        } else if (mSecondFragment.isAdded()) {
            fragmentManager.putFragment(outState, SecondFragment.class.getSimpleName(), mSecondFragment);
        } else if (mThirdFragment.isAdded()) {
            fragmentManager.putFragment(outState, ThirdFragment.class.getSimpleName(), mThirdFragment);
        }

    }

    private void initView() {
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar
                , R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_first:
                        showFragment(mFirstFragment);
                        break;
                    case R.id.nav_second:
                        showFragment(mSecondFragment);
                        break;
                    case R.id.nav_third:
                        showFragment(mThirdFragment);
                        break;
                    default:
                        break;
                }

                return true;
            }
        });

        // 设置navigationView 上头布局，个人信息
        View headerView = navigationView.getHeaderView(0);
        TextView textUserIcon = headerView.findViewById(R.id.text_user_icon);
        textUserIcon.setText("wmding");
        TextView textUserName = headerView.findViewById(R.id.text_user_name);
        textUserName.setText("小明");

    }

    private void initFragments(Bundle savedInstanceState) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) {
            mFirstFragment = (FirstFragment) fragmentManager.getFragment(savedInstanceState, FirstFragment.class.getSimpleName());
            mSecondFragment = (SecondFragment) fragmentManager.getFragment(savedInstanceState, SecondFragment.class.getSimpleName());
            mThirdFragment = (ThirdFragment) fragmentManager.getFragment(savedInstanceState, ThirdFragment.class.getSimpleName());
        } else {
            mFirstFragment = FirstFragment.newInstance();
            mSecondFragment = SecondFragment.newInstance();
            mThirdFragment = ThirdFragment.newInstance();
        }

        if (!mFirstFragment.isAdded()) {
            fragmentManager.beginTransaction()
                    .add(R.id.frame_layout, mFirstFragment, FirstFragment.class.getSimpleName())
                    .commit();
        }

        if (!mSecondFragment.isAdded()) {
            fragmentManager.beginTransaction()
                    .add(R.id.frame_layout, mSecondFragment, SecondFragment.class.getSimpleName())
                    .commit();
        }

        if (!mThirdFragment.isAdded()) {
            fragmentManager.beginTransaction()
                    .add(R.id.frame_layout, mThirdFragment, ThirdFragment.class.getSimpleName())
                    .commit();
        }

    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragment instanceof FirstFragment) {
            fragmentManager.beginTransaction()
                    .show(mFirstFragment)
                    .hide(mSecondFragment)
                    .hide(mThirdFragment)
                    .commit();
            setTitle(R.string.nav_first_name);
        } else if (fragment instanceof SecondFragment) {
            fragmentManager.beginTransaction()
                    .show(mSecondFragment)
                    .hide(mFirstFragment)
                    .hide(mThirdFragment)
                    .commit();
            setTitle(R.string.nav_second_name);
        } else if (fragment instanceof ThirdFragment) {
            fragmentManager.beginTransaction()
                    .show(mThirdFragment)
                    .hide(mFirstFragment)
                    .hide(mSecondFragment)
                    .commit();
            setTitle(R.string.nav_third_name);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent intent = new Intent(this, Main2Activity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setCheckable(false);
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.nav_sign_out:
                finish();
                break;
            case R.id.nav_about_me:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            default:
                break;
        }

        return true;
    }


    /**
     * 设置连续点击返回后退出该应用
     */
    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            // 使用Snackbar控件在页面底部进行提示
            Snackbar.make(drawerLayout, "再按一次退出程序哦~", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }

    }
}
