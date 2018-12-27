package wmding.example.com.mylivetools.fragment.third;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import wmding.example.com.mylivetools.R;
import wmding.example.com.mylivetools.bean.CategoryResult;
import wmding.example.com.mylivetools.interfaze.OnRecyclerViewItemOnClickListener;

import static com.bumptech.glide.request.target.Target.SIZE_ORIGINAL;

/**
 * @author wmding
 * @date 2018/12/24
 * @describe
 */
public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;

    private CategoryResult mCategoryResult;

    private OnRecyclerViewItemOnClickListener listener;


    public ImageAdapter(Context context, CategoryResult categoryResult) {
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
        mCategoryResult = categoryResult;
    }

    public OnRecyclerViewItemOnClickListener getListener() {
        return listener;
    }

    public void setListener(OnRecyclerViewItemOnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_image, parent, false);

        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolder viewHolder = (ViewHolder) holder;
        String url = mCategoryResult.results.get(position).getUrl();

        //屏幕的宽度(px值）
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        //Item的宽度，或图片的宽度
        int width = screenWidth / 2;

        //这里的SIZE_ORIGINAL为Gilde里自带的参数，不是我定义的常量
        Glide.with(context).load(url).override(width, SIZE_ORIGINAL)
                .fitCenter().into(viewHolder.mImage);
    }

    @Override
    public int getItemCount() {
        return mCategoryResult.results.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnRecyclerViewItemOnClickListener listener;

        ImageView mImage;

        private ViewHolder(View itemView, OnRecyclerViewItemOnClickListener listener) {
            super(itemView);
            this.listener = listener;

            mImage = itemView.findViewById(R.id.image);
            mImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            listener.onClick(view, getAdapterPosition());
        }
    }
}
