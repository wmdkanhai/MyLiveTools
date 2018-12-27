package wmding.example.com.mylivetools.fragment.third;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import wmding.example.com.mylivetools.R;
import wmding.example.com.mylivetools.glide.GlideLoader;

public class ImageDetailActivity extends AppCompatActivity {

    private ImageView mImageView;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        initView();
        
        initData();
    }


    private void initView() {
        mImageView = findViewById(R.id.image);
        constraintLayout = findViewById(R.id.constraintLayout);
        //设置ActionBar的Home按钮可以点击
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_detail_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_download:
                Snackbar.make(constraintLayout, "下载到本地了哦~", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    private void initData() {
        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("imageUrl");
        GlideLoader glideLoader = new GlideLoader();

        glideLoader.displayImage(this,imageUrl,mImageView);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}
