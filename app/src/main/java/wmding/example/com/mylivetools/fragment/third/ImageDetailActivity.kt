package wmding.example.com.mylivetools.fragment.third

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast

import wmding.example.com.mylivetools.R
import wmding.example.com.mylivetools.glide.GlideLoader

class ImageDetailActivity : AppCompatActivity() {

    private var mImageView: ImageView? = null
    private var constraintLayout: ConstraintLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)

        initView()

        initData()
    }


    private fun initView() {
        mImageView = findViewById(R.id.image)
        constraintLayout = findViewById(R.id.constraintLayout)
        //设置ActionBar的Home按钮可以点击
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.image_detail_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_download -> Snackbar.make(constraintLayout!!, "下载到本地了哦~", Toast.LENGTH_SHORT).show()
            android.R.id.home -> finish()
            else -> {
            }
        }
        return true
    }

    private fun initData() {
        val intent = intent
        val imageUrl = intent.getStringExtra("imageUrl")
        val glideLoader = GlideLoader()

        glideLoader.displayImage(this, imageUrl, mImageView)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
    }
}
