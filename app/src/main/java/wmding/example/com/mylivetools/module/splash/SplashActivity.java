package wmding.example.com.mylivetools.module.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wmding.example.com.mylivetools.MainActivity;
import wmding.example.com.mylivetools.R;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.imageView)
    ImageView mImage;
    @BindView(R.id.tv_jump)
    TextView mTvJump;

    private boolean isIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        String url = "https://cn.bing.com/th?id=OHR.RootBridge_ZH-CN5173953292_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp";
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.background)
                .error(R.drawable.background)
                .into(mImage);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toMainActivity();
            }
        }, 3500);
    }

    @OnClick(R.id.tv_jump)
    public void onViewClicked() {
        toMainActivity();
    }


    /**
     * 跳转到主页面
     */
    private void toMainActivity() {
        if (isIn) {
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
        isIn = true;
    }
}
