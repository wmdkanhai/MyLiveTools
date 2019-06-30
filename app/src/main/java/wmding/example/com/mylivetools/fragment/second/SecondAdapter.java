package wmding.example.com.mylivetools.fragment.second;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import wmding.example.com.mylivetools.R;
import wmding.example.com.mylivetools.bean.ResultsBean;
import wmding.example.com.mylivetools.interfaze.OnRecyclerViewItemOnClickListener;

/**
 * @author wmding
 * @date 2019/6/28
 * @describe
 */
public class SecondAdapter extends RecyclerView.Adapter {

    private LayoutInflater inflater;

    private List<ResultsBean> mList;

    public void addData(List data){
        if (data != null){
            mList.addAll(data);
        }
        notifyDataSetChanged();
    }

    private OnRecyclerViewItemOnClickListener listener;

    public OnRecyclerViewItemOnClickListener getListener() {
        return listener;
    }

    public void setListener(OnRecyclerViewItemOnClickListener listener) {
        this.listener = listener;
    }

    public SecondAdapter(Context context,List<ResultsBean> data) {
        this.mList = data;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflate = inflater.inflate(R.layout.item_article, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.textTitle.setText(mList.get(position).getDesc());
        viewHolder.textCategory.setText(mList.get(position).getType());
        viewHolder.textTime.setText(mList.get(position).getPublishedAt());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnRecyclerViewItemOnClickListener listener;

        TextView textTitle;
        TextView textTime;
        TextView textCategory;

        private ViewHolder(View itemView, OnRecyclerViewItemOnClickListener listener) {
            super(itemView);
            this.listener = listener;

            textTitle = itemView.findViewById(R.id.text_view_title);
            textTime = itemView.findViewById(R.id.text_view_time);
            textCategory = itemView.findViewById(R.id.text_view_category);
            textTitle.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            listener.onClick(view, getAdapterPosition());
        }
    }
}
