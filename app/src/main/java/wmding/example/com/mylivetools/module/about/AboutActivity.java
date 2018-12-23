package wmding.example.com.mylivetools.module.about;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import wmding.example.com.mylivetools.R;
/**
 * @author wmding
 * @date   2018/12/23
 * @describe 关于我的页面
 */
public class AboutActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private FloatingActionButton mShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initView();
    }

    private void initView() {
        mToolbar = findViewById(R.id.nav_home_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//不添加这句华为手机会出现标题显示不完整的问题

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mShare = findViewById(R.id.nav_home_fab);
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AboutActivity.this,"share",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
