package com.v_chek_host.vcheckhost.V3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.V3.models.V3GetAllActivitiesDataModel;
import com.v_chek_host.vcheckhost.V3.models.V3TrainModelData;

import java.util.List;

public class V3TrainHorizontalTitleAdapter extends RecyclerView.Adapter<V3TrainHorizontalTitleAdapter.MyViewHolder> {
    List<V3TrainModelData.MlModelTypeMasterDatum> mlModelTypeMasterDatumList;
    Context context;
    V3TrainHorizontalTitleAdapter.TitleActivityClick titleActivityClick;

    public V3TrainHorizontalTitleAdapter(List<V3TrainModelData.MlModelTypeMasterDatum> mlModelTypeMasterDatumList
            , Context context, V3TrainHorizontalTitleAdapter.TitleActivityClick titleActivityClick) {
        this.mlModelTypeMasterDatumList = mlModelTypeMasterDatumList;
        this.context = context;
        this.titleActivityClick = titleActivityClick;
    }

    @NonNull
    @Override
    public V3TrainHorizontalTitleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_all_activites_title_data_items_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull V3TrainHorizontalTitleAdapter.MyViewHolder holder, int position) {
        V3TrainModelData.MlModelTypeMasterDatum titlename = mlModelTypeMasterDatumList.get(position);
        if (titlename.isSelected()) {
            holder.tvActivitiesname.setTextColor(context.getResources().getColor(R.color.black));
        } else {
            holder.tvActivitiesname.setTextColor(context.getResources().getColor(R.color.activity_title_color));
        }
        holder.tvActivitiesname.setText(titlename.getMlModelTypeName());

        holder.tvActivitiesname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (V3TrainModelData.MlModelTypeMasterDatum activityTypeMasterDatum : mlModelTypeMasterDatumList) {
                    activityTypeMasterDatum.setSelected(false);
                }
                titlename.setSelected(true);
                notifyDataSetChanged();
                titleActivityClick.titleActivityClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlModelTypeMasterDatumList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvActivitiesname;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvActivitiesname = (TextView) itemView.findViewById(R.id.tvActivityName);
        }
    }

    public interface TitleActivityClick {
        void titleActivityClick(int position);
    }
}
