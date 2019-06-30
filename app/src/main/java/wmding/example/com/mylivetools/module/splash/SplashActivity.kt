package wmding.example.com.mylivetools.module.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_splash.*
import wmding.example.com.mylivetools.MainActivity
import wmding.example.com.mylivetools.R

class SplashActivity : AppCompatActivity() {

    private var isIn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val url = "https://cn.bing.com/th?id=OHR.RootBridge_ZH-CN5173953292_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp"
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.background)
                .error(R.drawable.background)
                .into(imageView)

        Handler().postDelayed({ toMainActivity() }, 3500)

        tv_jump.setOnClickListener { toMainActivity() }
    }

    /**
     * 跳转到主页面
     */
    private fun toMainActivity() {
        if (isIn) {
            return
        }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out)
        finish()
        isIn = true
    }
}


