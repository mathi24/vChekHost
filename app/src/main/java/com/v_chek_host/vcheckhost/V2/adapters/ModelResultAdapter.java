package com.v_chek_host.vcheckhost.V2.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.V2.models.TrainDataActivity;
import com.v_chek_host.vcheckhost.services.entity.HostResponseData;

import java.util.List;

public class ModelResultAdapter extends RecyclerView.Adapter<ModelResultAdapter.MyViewHolder> {
    List<HostResponseData.ModelResult> list;
    Context ctx;
    int lastitemPosition = -1;

    public ModelResultAdapter(List<HostResponseData.ModelResult> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ModelResultAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_result_list_item, parent, false);
        return new ModelResultAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ModelResultAdapter.MyViewHolder holder, int position) {
        HostResponseData.ModelResult message = list.get(position);
        holder.txtModelName.setText(message.getModelName() );
        if(message.getInspktResult().equalsIgnoreCase("Fail")) {
            holder.txtModelResult.setText(message.getResultMessage());
            holder.txtModelResult.setTextColor(ContextCompat.getColor(ctx, R.color.red));
            holder.inspktLyt.setBackgroundColor(Color.parseColor("#D50000"));
            holder.resultImageView.setBackgroundResource(R.drawable.ic_fail_white_24dp);
        }else {
            holder.txtModelResult.setText(message.getResultMessage());
            holder.txtModelResult.setTextColor(ContextCompat.getColor(ctx, R.color.green));
            holder.inspktLyt.setBackgroundColor(Color.parseColor("#8BC34A"));
            holder.resultImageView.setBackgroundResource(R.drawable.ic_pass_white_24dp);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtModelName,txtModelResult;
        public CardView cardView;
        public ImageView resultImageView;
        public RelativeLayout inspktLyt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtModelName = (TextView) itemView.findViewById(R.id.txt_model_name);
            txtModelResult = (TextView) itemView.findViewById(R.id.txt_model_result);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            resultImageView = (ImageView) itemView.findViewById(R.id.inspkt_img);
            inspktLyt = (RelativeLayout) itemView.findViewById(R.id.inspkt_lyt);

        }
    }
}
