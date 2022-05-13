package com.v_chek_host.vcheckhost.V2.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.V2.models.TrainDataActivity;
import com.v_chek_host.vcheckhost.V2.models.TrainDataModel;

import java.util.List;

public class ActivityDataAdapter extends RecyclerView.Adapter<ActivityDataAdapter.MyViewHolder> {
    List<TrainDataActivity.Message> list;
    Context ctx;
    int lastitemPosition = -1;

    public ActivityDataAdapter(List<TrainDataActivity.Message> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ActivityDataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list, parent, false);
        return new ActivityDataAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityDataAdapter.MyViewHolder holder, int position) {
        TrainDataActivity.Message message = list.get(position);
        holder.txtTrainName.setText(message.getActivityName());
        if (message.isSelected()) {
            holder.cardView.setBackground(this.ctx.getDrawable(R.drawable.gradient_color_background));
            holder.txtTrainName.setTextColor(Color.WHITE);
            System.out.println("hasjdhajshdjashd"+"sadasd");
        } else {
            holder.cardView.setBackground(this.ctx.getDrawable(R.drawable.background_white));
            holder.txtTrainName.setTextColor(Color.BLACK);
        }
        if(message.getActivityThumbnailUrl()!=null)
        Picasso.get()
                .load(message.getActivityThumbnailUrl())
                .into(holder.thumbnailImg, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                    }
                });
       /* holder.txtTrainName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastitemPosition != -1 && lastitemPosition != position) {
                    list.get(lastitemPosition).setSelected(false);
                    list.get(position).setSelected(true);
                } else {
                    list.get(position).setSelected(true);
                }
                lastitemPosition = position;
                notifyDataSetChanged();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTrainName;
        public CardView cardView;
        public ImageView thumbnailImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTrainName = (TextView) itemView.findViewById(R.id.txt_train_name);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            thumbnailImg = (ImageView) itemView.findViewById(R.id.thumbnail);
        }
    }
}
