package wmding.example.com.mylivetools;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import wmding.example.com.mylivetools.fragment.FirstFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    private BottomNavigationView bottomNavigationView;
    private Fragment mFirstFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initFragments(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (mFirstFragment.isAdded()) {
            fragmentManager.putFragment(outState, FirstFragment.class.getSimpleName(), mFirstFragment);
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
                        break;
                    case R.id.nav_third:
                        break;
                    default:
                        break;
                }

                return true;
            }
        });


    }

    private void initFragments(Bundle savedInstanceState) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) {
            mFirstFragment = fragmentManager.getFragment(savedInstanceState, FirstFragment.class.getSimpleName());
        } else {
            mFirstFragment = FirstFragment.newInstance();
        }

        if (!mFirstFragment.isAdded()){
            fragmentManager.beginTransaction()
                    .add(R.id.frame_layout,mFirstFragment,FirstFragment.class.getSimpleName())
                    .commit();
        }

    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragment instanceof FirstFragment) {
            fragmentManager.beginTransaction()
                    .show(mFirstFragment)
//                    .hide(categoriesFragment)
//                    .hide(aboutFragment)
                    .commit();
            setTitle("aaa");

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
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setCheckable(false);
        switch (item.getItemId()) {
            case R.id.nav_sign_out:
                Toast.makeText(this, "注销了", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }
}
