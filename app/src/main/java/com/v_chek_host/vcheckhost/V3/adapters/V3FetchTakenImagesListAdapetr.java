package com.v_chek_host.vcheckhost.V3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.SquareLayout;
import com.v_chek_host.vcheckhost.V2.models.ModelTrainingSetDataModel;
import com.v_chek_host.vcheckhost.V2.models.TrainDataModel;
import com.v_chek_host.vcheckhost.V3.database.TrainModel;

import java.util.List;

public class V3FetchTakenImagesListAdapetr extends RecyclerView.Adapter<V3FetchTakenImagesListAdapetr.MyViewHolder> {
    List<TrainModelData> list;
    Context ctx;
    DeleteIconEnabled deleteIconEnabled;

    public V3FetchTakenImagesListAdapetr(List<TrainModelData> list, Context ctx, DeleteIconEnabled deleteIconEnabled) {
        this.list = list;
        this.ctx = ctx;
        this.deleteIconEnabled = deleteIconEnabled;
    }

    @NonNull
    @Override
    public V3FetchTakenImagesListAdapetr.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_training_data_list, parent, false);
        return new V3FetchTakenImagesListAdapetr.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull V3FetchTakenImagesListAdapetr.MyViewHolder holder, int position) {
        TrainModelData message = list.get(position);
      /*  if (message.getImageResult() == 0) {
            holder.thumbnail.setPadding(2, 2, 2, 2);
            holder.squareLayout.setBackground(ctx.getDrawable(R.drawable.rectangle_shape_bck_red));
        } else {
            holder.thumbnail.setPadding(0, 0, 0, 0);
            holder.squareLayout.setBackground(null);
        }*/
        // holder.thumbnail.setPadding(2,2,2,2);
        // holder.squareLayout.setBackground(ctx.getDrawable(R.drawable.rectangle_shape_bck_red));
        if (message.isCheckBoxSelected) {
            holder.checkBox.setVisibility(View.VISIBLE);
            if (message.isSetChecked) {
                holder.checkBox.setChecked(true);
            } else {
                holder.checkBox.setChecked(false);
            }
        } else {
            holder.checkBox.setVisibility(View.GONE);
        }
        Glide.with(ctx).load(message.getImagePATH())
                /*   .placeholder(circularProgressDrawable)*/
                .thumbnail(0.5f)
                .transition(new DrawableTransitionOptions().crossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.thumbnail);
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                for (TrainModelData trainModelData : list) {
                    trainModelData.setCheckBoxSelected(true);
                }
                message.setSetChecked(true);
                deleteIconEnabled.deleteIconEnabled();
                notifyDataSetChanged();
                return true;
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (message.isCheckBoxSelected()) {
                    if (message.isSetChecked()) {
                        message.setSetChecked(false);
                    } else {
                        message.setSetChecked(true);
                    }
                    notifyDataSetChanged();
                }
            }
        });
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()) {
                    message.setSetChecked(true);
                } else {
                    message.setSetChecked(false);
                }
                notifyDataSetChanged();
            }
        });
       /* holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    message.setSetChecked(true);
                    //notifyDataSetChanged();
                } else {
                    message.setSetChecked(false);
                    //notifyDataSetChanged();
                }
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView txtTrainName;
        SquareLayout squareLayout;
        CheckBox checkBox;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            squareLayout = (SquareLayout) itemView.findViewById(R.id.square);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    public static class TrainModelData {
        private int id;
        private String imagePATH;
        public boolean isCheckBoxSelected = false;
        public boolean isSetChecked = false;
        public boolean isDeleted = false;
        public boolean isUploaded = false;

        public TrainModelData() {
        }

        public TrainModelData(int id, String imagePATH) {
            this.id = id;
            this.imagePATH = imagePATH;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImagePATH() {
            return imagePATH;
        }

        public void setImagePATH(String imagePATH) {
            this.imagePATH = imagePATH;
        }

        public boolean isCheckBoxSelected() {
            return isCheckBoxSelected;
        }

        public void setCheckBoxSelected(boolean checkBoxSelected) {
            isCheckBoxSelected = checkBoxSelected;
        }

        public boolean isSetChecked() {
            return isSetChecked;
        }

        public void setSetChecked(boolean setChecked) {
            isSetChecked = setChecked;
        }

        public boolean isDeleted() {
            return isDeleted;
        }

        public void setDeleted(boolean deleted) {
            isDeleted = deleted;
        }

        public boolean isUploaded() {
            return isUploaded;
        }

        public void setUploaded(boolean uploaded) {
            isUploaded = uploaded;
        }
    }

    public interface DeleteIconEnabled {
        void deleteIconEnabled();
    }
}
