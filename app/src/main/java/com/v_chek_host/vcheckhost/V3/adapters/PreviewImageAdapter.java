package com.v_chek_host.vcheckhost.V3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.v_chek_host.vcheckhost.R;

import java.util.List;

public class PreviewImageAdapter extends RecyclerView.Adapter<PreviewImageAdapter.MyViewHolder> {
    List<String> urlList;
    Context context;

    public PreviewImageAdapter(List<String> urlList, Context context) {
        this.urlList = urlList;
        this.context = context;
    }

    @NonNull
    @Override
    public PreviewImageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.preview_image_list_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviewImageAdapter.MyViewHolder holder, int position) {
        String url = urlList.get(position);
        Glide.with(context).load(url).into(holder.ivPreview);
    }

    @Override
    public int getItemCount() {
        return urlList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPreview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPreview = itemView.findViewById(R.id.ivPreview);
        }
    }
}
