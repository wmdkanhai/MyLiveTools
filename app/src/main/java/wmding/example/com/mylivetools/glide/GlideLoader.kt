package wmding.example.com.mylivetools.glide

import android.content.Context
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader


class GlideLoader : ImageLoader() {
    override fun displayImage(context: Context, path: Any, imageView: ImageView?) {
        Glide.with(context).load(path).into(imageView)
    }
}
