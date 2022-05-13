package com.v_chek_host.vcheckhost.V3.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.V3.models.V3MobileDashBoardDataResponce;

import java.util.List;

public class MobileDashBoardWeeklyFilterDataAdapter extends RecyclerView.Adapter<MobileDashBoardWeeklyFilterDataAdapter.MyViewHolder> {

    List<V3MobileDashBoardDataResponce.WeeklyWiseData> v3MobileDashBoardDataResponceList;
    Context context;
    WeeklyFilterData weeklyFilterData;

    public MobileDashBoardWeeklyFilterDataAdapter(List<V3MobileDashBoardDataResponce.WeeklyWiseData>
                                                          v3MobileDashBoardDataResponceList,
                                                  Context context, WeeklyFilterData weeklyFilterData) {
        this.v3MobileDashBoardDataResponceList = v3MobileDashBoardDataResponceList;
        this.context = context;
        this.weeklyFilterData = weeklyFilterData;
    }

    @NonNull
    @Override
    public MobileDashBoardWeeklyFilterDataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dashboard_graph_bar_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MobileDashBoardWeeklyFilterDataAdapter.MyViewHolder holder, int position) {
        V3MobileDashBoardDataResponce.WeeklyWiseData weeklyWiseData = v3MobileDashBoardDataResponceList.get(position);
        if (weeklyWiseData.getTotalInspectionsCount() > 0) {
            holder.progressBar.setVisibility(View.VISIBLE);
        } else {
            holder.progressBar.setVisibility(View.INVISIBLE);
        }
        holder.progressBar.setMax(weeklyWiseData.getTotalInspectionsCount());
        holder.progressBar.setProgress(weeklyWiseData.getFailInspectionsCount());
        holder.tvDay.setText(weeklyWiseData.getDayName());
        //  holder.tvDayCount.setText(weeklyWiseData.getPassInspectionsCount() + "/" + weeklyWiseData.getFailInspectionsCount());
        holder.tvDayCount.setText(weeklyWiseData.getWeekDate());
        if (weeklyWiseData.isSelected()) {
            if (weeklyWiseData.getTotalInspectionsCount() > 0) {

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
            if (weeklyWiseData.getTotalInspectionsCount() > 0) {
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
                weeklyWiseData.setSelected(true);
                notifyDataSetChanged();
                weeklyFilterData.weeklyFilterData(position);
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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
            tvDay = (TextView) itemView.findViewById(R.id.tvDay);
            tvDayCount = (TextView) itemView.findViewById(R.id.tvDayCount);
        }
    }

    public interface WeeklyFilterData {
        void weeklyFilterData(int position);
    }
}
