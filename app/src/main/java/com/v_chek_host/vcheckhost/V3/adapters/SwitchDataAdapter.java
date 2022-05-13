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
import com.v_chek_host.vcheckhost.V3.models.V3SwitchDataModelRespone;

import java.util.List;

public class SwitchDataAdapter extends RecyclerView.Adapter<SwitchDataAdapter.MyViewHolder> {
    List<V3SwitchDataModelRespone.Message> messageList;
    Context context;
    SwitchDataFilter switchDataFilter;


    public SwitchDataAdapter(List<V3SwitchDataModelRespone.Message> messageList, Context context, SwitchDataFilter switchDataFilter) {
        this.messageList = messageList;
        this.context = context;
        this.switchDataFilter = switchDataFilter;
    }

    @NonNull
    @Override
    public SwitchDataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.master_activityname_popup_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SwitchDataAdapter.MyViewHolder holder, int position) {
        V3SwitchDataModelRespone.Message message = messageList.get(position);
        holder.tvActivityName.setText(message.getDisplayName());
        if (message.isSelected()) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!message.isSelected()) {
                    for (int i = 0; i < messageList.size(); i++) {
                        messageList.get(i).setSelected(false);
                    }
                    message.setSelected(true);
                } else {
                    for (int i = 0; i < messageList.size(); i++) {
                        messageList.get(i).setSelected(false);
                    }
                    message.setSelected(true);
                }
                switchDataFilter.switchDataFilter(position);
            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!message.isSelected()) {
                    for (int i = 0; i < messageList.size(); i++) {
                        messageList.get(i).setSelected(false);
                    }
                    message.setSelected(true);
                } else {
                    for (int i = 0; i < messageList.size(); i++) {
                        messageList.get(i).setSelected(false);
                    }
                    message.setSelected(true);
                }
                switchDataFilter.switchDataFilter(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return messageList.size();
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

    public interface SwitchDataFilter {
        void switchDataFilter(int position);
    }
}
