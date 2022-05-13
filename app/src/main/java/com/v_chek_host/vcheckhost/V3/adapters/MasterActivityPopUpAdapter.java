package com.v_chek_host.vcheckhost.V3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.vision.text.Line;
import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.V3.models.V3MasterActivityNameModel;
import com.v_chek_host.vcheckhost.V3.models.V3MobileDashboardActivityMasterResponce;

import java.util.List;

public class MasterActivityPopUpAdapter extends RecyclerView.Adapter<MasterActivityPopUpAdapter.MyViewHolder> {
    List<V3MobileDashboardActivityMasterResponce.Message> masterActivityNameModelList;
    Context context;
    ChangeActivityFilter changeActivityFilter;

    public MasterActivityPopUpAdapter(List<V3MobileDashboardActivityMasterResponce.Message>
                                              masterActivityNameModelList, Context context, ChangeActivityFilter changeActivityFilter) {
        this.masterActivityNameModelList = masterActivityNameModelList;
        this.context = context;
        this.changeActivityFilter = changeActivityFilter;
    }

    @NonNull
    @Override
    public MasterActivityPopUpAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.master_activityname_popup_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MasterActivityPopUpAdapter.MyViewHolder holder, int position) {
        V3MobileDashboardActivityMasterResponce.Message v3MasterActivityNameModel = masterActivityNameModelList.get(position);
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
                    for (V3MobileDashboardActivityMasterResponce.Message message : masterActivityNameModelList) {
                        message.setChecked(false);
                    }
                    v3MasterActivityNameModel.setChecked(true);
                    notifyDataSetChanged();
                    changeActivityFilter.changeFilterActivity(position);
                } else {
                    v3MasterActivityNameModel.setChecked(true);
                    notifyDataSetChanged();
                }
            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!v3MasterActivityNameModel.isChecked()) {
                    for (V3MobileDashboardActivityMasterResponce.Message message : masterActivityNameModelList) {
                        message.setChecked(false);
                    }
                    v3MasterActivityNameModel.setChecked(true);
                    notifyDataSetChanged();
                    changeActivityFilter.changeFilterActivity(position);
                } else {
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

    public interface ChangeActivityFilter {
        void changeFilterActivity(int postion);
    }
}
