package com.v_chek_host.vcheckhost.V2.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.v_chek_host.vcheckhost.PreviewImageActivity;
import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.SquareLayout;
import com.v_chek_host.vcheckhost.V2.PlayVideoActivity;
import com.v_chek_host.vcheckhost.V2.PlayerActivity;
import com.v_chek_host.vcheckhost.V2.models.ModelTrainingSetDataModel;
import com.v_chek_host.vcheckhost.V2.models.TrainDataModel;
import com.v_chek_host.vcheckhost.V3.PreviewModelImageActivity;
import com.v_chek_host.vcheckhost.V3.models.V3MlModelDatasetResponce;

import java.io.Serializable;
import java.util.List;

public class ModelTrainingDataAdapetr extends RecyclerView.Adapter<ModelTrainingDataAdapetr.MyViewHolder> {
    List<V3MlModelDatasetResponce.Message> list;
    Context ctx;

    public ModelTrainingDataAdapetr(List<V3MlModelDatasetResponce.Message> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ModelTrainingDataAdapetr.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_training_data_list, parent, false);
        return new ModelTrainingDataAdapetr.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ModelTrainingDataAdapetr.MyViewHolder holder, int position) {
        V3MlModelDatasetResponce.Message message = list.get(position);
      /*  if (message.getImageResult() == 0) {
            holder.thumbnail.setPadding(2, 2, 2, 2);
            holder.squareLayout.setBackground(ctx.getDrawable(R.drawable.rectangle_shape_bck_red));
        } else {
            holder.thumbnail.setPadding(0, 0, 0, 0);
            holder.squareLayout.setBackground(null);
        }*/
        // holder.thumbnail.setPadding(2,2,2,2);
        // holder.squareLayout.setBackground(ctx.getDrawable(R.drawable.rectangle_shape_bck_red));
        Glide.with(ctx).load(message.getMlModelImageUrl())
                /*   .placeholder(circularProgressDrawable)*/
                .thumbnail(0.5f)
                .transition(new DrawableTransitionOptions().crossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.thumbnail);
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (message.getDatasetType() == 0) {
                    Bundle args = new Bundle();
                    args.putSerializable("listImages", (Serializable) list);
                    args.putInt("urlPosition", position);
                    Intent intent = new Intent(ctx, PreviewModelImageActivity.class);
                    intent.putExtras(args);
                    ctx.startActivity(intent);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("videoPath", message.getMlModelImageUrl());
                    Intent intent = new Intent(ctx, PlayerActivity.class);
                    intent.putExtras(bundle);
                    ctx.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView txtTrainName;
        SquareLayout squareLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            squareLayout = (SquareLayout) itemView.findViewById(R.id.square);
        }
    }
}
