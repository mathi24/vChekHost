package com.v_chek_host.vcheckhost.V3.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.V3.models.V3MobileDashBoardDataResponce;

import java.util.List;

public class MobileDashBoardAllFilterDataAdapter extends RecyclerView.Adapter<MobileDashBoardAllFilterDataAdapter.MyViewHolder> {

    List<V3MobileDashBoardDataResponce.AllWiseData> v3MobileDashBoardDataResponceList;
    Context context;
    AllWiseFilterData allWiseFilterData;

    public MobileDashBoardAllFilterDataAdapter(List<V3MobileDashBoardDataResponce.AllWiseData>
                                                       v3MobileDashBoardDataResponceList,
                                               Context context, AllWiseFilterData allWiseFilterData) {
        this.v3MobileDashBoardDataResponceList = v3MobileDashBoardDataResponceList;
        this.context = context;
        this.allWiseFilterData = allWiseFilterData;
    }

    @NonNull
    @Override
    public MobileDashBoardAllFilterDataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dashboard_graph_bar_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MobileDashBoardAllFilterDataAdapter.MyViewHolder holder, int position) {
        V3MobileDashBoardDataResponce.AllWiseData allWiseData = v3MobileDashBoardDataResponceList.get(position);
        if (allWiseData.getTotalInspectionsCount() > 0) {
            holder.progressBar.setVisibility(View.VISIBLE);
        } else {
            holder.progressBar.setVisibility(View.INVISIBLE);
        }
        holder.progressBar.setMax(allWiseData.getTotalInspectionsCount());
        holder.progressBar.setProgress(allWiseData.getFailInspectionsCount());
        holder.tvDay.setText(allWiseData.getYear() + "");
        holder.tvDayCount.setVisibility(View.GONE);
        //holder.tvDayCount.setText(allWiseData.getPassInspectionsCount() + "/" + allWiseData.getFailInspectionsCount());
        if (allWiseData.isSelected()) {
            if (allWiseData.getTotalInspectionsCount() > 0) {
                int colorPrimary = Color.parseColor("#00CA24");
                int colorSecondary = Color.parseColor("#FB0066");
                holder.progressBar.setProgressBackgroundTintList(ColorStateList.valueOf(colorPrimary));
                holder.progressBar.setProgressTintList(ColorStateList.valueOf(colorSecondary));
            } else {
                int colorPrimary = Color.parseColor("#FFFFFF");
                int colorSecondary = Color.parseColor("#FFFFFF");
                holder.progressBar.setProgressBackgroundTintList(ColorStateList.valueOf(colorPrimary));
                holder.progressBar.setProgressTintList(ColorStateList.valueOf(colorSecondary));
            }
        } else {
            if (allWiseData.getTotalInspectionsCount() > 0) {
                int colorPrimary = Color.parseColor("#5DB07C");
                int colorSecondary = Color.parseColor("#BE8176");
                holder.progressBar.setProgressBackgroundTintList(ColorStateList.valueOf(colorPrimary));
                holder.progressBar.setProgressTintList(ColorStateList.valueOf(colorSecondary));
            } else {
                int colorPrimary = Color.parseColor("#FFFFFF");
                int colorSecondary = Color.parseColor("#FFFFFF");
                holder.progressBar.setProgressBackgroundTintList(ColorStateList.valueOf(colorPrimary));
                holder.progressBar.setProgressTintList(ColorStateList.valueOf(colorSecondary));
            }
        }
        holder.progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < v3MobileDashBoardDataResponceList.size(); i++) {
                    v3MobileDashBoardDataResponceList.get(i).setSelected(false);
                }
                allWiseData.setSelected(true);
                notifyDataSetChanged();
                allWiseFilterData.allWiseFilterData(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return v3MobileDashBoardDataResponceList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public TextView tvDay, tvDayCount;
        public LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
            tvDay = (TextView) itemView.findViewById(R.id.tvDay);
            tvDayCount = (TextView) itemView.findViewById(R.id.tvDayCount);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear_layout);
        }
    }

    public interface AllWiseFilterData {
        void allWiseFilterData(int position);
    }
}
