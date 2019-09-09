package idv.jerry.dramalist.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import idv.jerry.dramalist.R;
import idv.jerry.dramalist.data.DramaData;
import idv.jerry.dramalist.utils.GlideUtils;

public class DramaAdapter extends RecyclerView.Adapter<DramaAdapter.ViewHolder> {

    private ArrayList<DramaData> dramaList;
    private OnClickListener callback;

    public DramaAdapter(Context context) {
        this.dramaList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drama_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        DramaData dramaData = dramaList.get(position);

        GlideUtils.loadImages(dramaData.thumb, viewHolder.ivDrama);
        viewHolder.tvRating.setText(String.valueOf(dramaData.rating));
        viewHolder.tvDramaName.setText(dramaData.name);
        viewHolder.tvCreateAt.setText(transferDateFormat(dramaData.created_at));

    }

    @Override
    public int getItemCount() {
        return dramaList.size();
    }

    public void updateDramaList(ArrayList<DramaData> dramaList){
        this.dramaList.clear();
        this.dramaList.addAll(dramaList);
        notifyDataSetChanged();
    }

    public void setOnClickListener(OnClickListener callback){
        this.callback = callback;
    }

    public void destroy(){
        if (dramaList != null){
            dramaList.clear();
            dramaList = null;
        }
    }

    private String transferDateFormat(String orignalTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        String outputStr = "";
        try {
            Date date = sdf.parse(orignalTime);
            outputStr = outputFormat.format(date);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return outputStr;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivDrama;
        private TextView tvRating;
        private TextView tvDramaName;
        private TextView tvCreateAt;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onClick(getAdapterPosition());
                }
            });

            ivDrama = itemView.findViewById(R.id.iv_drama);
            tvRating = itemView.findViewById(R.id.tv_rating);
            tvDramaName = itemView.findViewById(R.id.tv_drama_name);
            tvCreateAt = itemView.findViewById(R.id.tv_create_at);
        }
    }

    public interface OnClickListener {
        void onClick(int position);
    }
}
