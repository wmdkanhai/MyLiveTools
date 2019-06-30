package wmding.example.com.mylivetools.module.about

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Toast
import wmding.example.com.mylivetools.R

/**
 * @author wmding
 * @date   2018/12/23
 * @describe 关于我的页面
 */
class AboutActivity : AppCompatActivity() {

    private var mToolbar: Toolbar? = null
    private var mShare: FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        initView()
    }

    private fun initView() {
        mToolbar = findViewById(R.id.nav_home_toolbar)
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)//不添加这句华为手机会出现标题显示不完整的问题

        mToolbar!!.setNavigationOnClickListener { finish() }

        mShare = findViewById(R.id.nav_home_fab)
        mShare!!.setOnClickListener { Toast.makeText(this@AboutActivity, "share", Toast.LENGTH_SHORT).show() }

    }
}
