package com.v_chek_host.vcheckhost.V3.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.SharedPreferenceManager;
import com.v_chek_host.vcheckhost.V3.V3PassAndFailActivity;
import com.v_chek_host.vcheckhost.V3.models.V3TrainModelData;

import java.util.ArrayList;
import java.util.List;

public class V3TrainRecentHorizontalAdapter extends RecyclerView.Adapter<V3TrainRecentHorizontalAdapter.MyViewHolder> implements Filterable {
    List<V3TrainModelData.RecentMlModel> recentMlModels;
    List<V3TrainModelData.RecentMlModel> recentMlModelsFilter;
    Context context;

    public V3TrainRecentHorizontalAdapter(List<V3TrainModelData.RecentMlModel> recentMlModels, Context context) {
        this.recentMlModels = recentMlModels;
        this.recentMlModelsFilter = recentMlModels;
        this.context = context;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    recentMlModelsFilter = recentMlModels;
                } else {
                    List<V3TrainModelData.RecentMlModel> filteredList = new ArrayList<>();
                    for (V3TrainModelData.RecentMlModel row : recentMlModels) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getModelName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    recentMlModelsFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = recentMlModelsFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                recentMlModelsFilter = (ArrayList<V3TrainModelData.RecentMlModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @NonNull

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_horizontal_items_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        V3TrainModelData.RecentMlModel titlename = recentMlModelsFilter.get(position);
        holder.tvActivitiesname.setText(titlename.getModelName());
        if (!TextUtils.isEmpty(titlename.getModelThumbnailUrl())) {
            CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
            circularProgressDrawable.setStrokeWidth(5f);
            circularProgressDrawable.setCenterRadius(30f);
            circularProgressDrawable.start();
            Glide.with(context).load(titlename.getModelThumbnailUrl()).placeholder(circularProgressDrawable).into(holder.ivRecent);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferenceManager.setOverlayUrl(context,titlename.getTrainingOverlayImageUrl());
                SharedPreferenceManager.setLogModelId(context, titlename.getMlModelId());
                Intent intent = new Intent(context, V3PassAndFailActivity.class);
                intent.putExtra("model_id", titlename.getMlModelId());
                intent.putExtra("model_name", titlename.getModelName());
                context.startActivity(intent);
            }
        });
        holder.tvActivitiesname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferenceManager.setOverlayUrl(context,titlename.getTrainingOverlayImageUrl());
                SharedPreferenceManager.setLogModelId(context, titlename.getMlModelId());
                Intent intent = new Intent(context, V3PassAndFailActivity.class);
                intent.putExtra("model_id", titlename.getMlModelId());
                intent.putExtra("model_name", titlename.getModelName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recentMlModelsFilter.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvActivitiesname;
        public ImageView ivRecent;
        public CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvActivitiesname = (TextView) itemView.findViewById(R.id.tvActivityName);
            ivRecent = (ImageView) itemView.findViewById(R.id.ivRecentActivityImg);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}
