package com.v_chek_host.vcheckhost.V3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.V3.fragments.V3DashBoardFragment;
import com.v_chek_host.vcheckhost.V3.models.V3MasterActivityNameModel;
import com.v_chek_host.vcheckhost.V3.models.V3MobileDashboardActivityMasterResponce;

import java.util.List;

public class MasterActivityFilterPopUpAdapter extends RecyclerView.Adapter<MasterActivityFilterPopUpAdapter.MyViewHolder> {
    List<V3MasterActivityNameModel> masterActivityNameModelList;
    Context context;
    ScheduleFilter scheduleFilter;

    public MasterActivityFilterPopUpAdapter(List<V3MasterActivityNameModel> masterActivityNameModelList,
                                            Context context, ScheduleFilter scheduleFilter) {
        this.masterActivityNameModelList = masterActivityNameModelList;
        this.context = context;
        this.scheduleFilter = scheduleFilter;
    }

    @NonNull
    @Override
    public MasterActivityFilterPopUpAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.master_activityname_popup_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MasterActivityFilterPopUpAdapter.MyViewHolder holder, int position) {
        V3MasterActivityNameModel v3MasterActivityNameModel = masterActivityNameModelList.get(position);
        holder.tvActivityName.setText(v3MasterActivityNameModel.getActivityName());
        if (v3MasterActivityNameModel.isChecked()) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!v3MasterActivityNameModel.isChecked()) {
                    for (V3MasterActivityNameModel message : masterActivityNameModelList) {
                        message.setChecked(false);
                    }
                    if (v3MasterActivityNameModel.getActivityID() == 1) {
                        V3DashBoardFragment.filterID = 1;
                    } else if (v3MasterActivityNameModel.getActivityID() == 2) {
                        V3DashBoardFragment.filterID = 2;
                    } else {
                        V3DashBoardFragment.filterID = 3;
                    }
                    v3MasterActivityNameModel.setChecked(true);
                    notifyDataSetChanged();
                    scheduleFilter.scheduleFilter(position);
                } else {
                    if (v3MasterActivityNameModel.getActivityID() == 1) {
                        V3DashBoardFragment.filterID = 1;
                    } else if (v3MasterActivityNameModel.getActivityID() == 2) {
                        V3DashBoardFragment.filterID = 2;
                    } else {
                        V3DashBoardFragment.filterID = 3;
                    }
                    v3MasterActivityNameModel.setChecked(true);
                    notifyDataSetChanged();
                }
            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!v3MasterActivityNameModel.isChecked()) {
                    for (V3MasterActivityNameModel message : masterActivityNameModelList) {
                        message.setChecked(false);
                    }
                    if (v3MasterActivityNameModel.getActivityID() == 1) {
                        V3DashBoardFragment.filterID = 1;
                    } else if (v3MasterActivityNameModel.getActivityID() == 2) {
                        V3DashBoardFragment.filterID = 2;
                    } else {
                        V3DashBoardFragment.filterID = 3;
                    }
                    v3MasterActivityNameModel.setChecked(true);
                    notifyDataSetChanged();
                    scheduleFilter.scheduleFilter(position);
                } else {
                    if (v3MasterActivityNameModel.getActivityID() == 1) {
                        V3DashBoardFragment.filterID = 1;
                    } else if (v3MasterActivityNameModel.getActivityID() == 2) {
                        V3DashBoardFragment.filterID = 2;
                    } else {
                        V3DashBoardFragment.filterID = 3;
                    }
                    v3MasterActivityNameModel.setChecked(true);
                    notifyDataSetChanged();
                }
            }
        });

    /*    holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (V3MobileDashboardActivityMasterResponce.Message message : masterActivityNameModelList) {
                        message.setChecked(false);
                    }
                    v3MasterActivityNameModel.setChecked(true);
                    //notifyDataSetChanged();
                } else {
                    for (V3MobileDashboardActivityMasterResponce.Message message : masterActivityNameModelList) {
                        message.setChecked(false);
                    }
                    v3MasterActivityNameModel.setChecked(false);
                    //notifyDataSetChanged();
                }
                notifyDataSetChanged();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return masterActivityNameModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvActivityName;
        CheckBox checkBox;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvActivityName = (TextView) itemView.findViewById(R.id.tvActivityName);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear_layout);
        }
    }

    public interface ScheduleFilter {
        void scheduleFilter(int position);
    }
}
