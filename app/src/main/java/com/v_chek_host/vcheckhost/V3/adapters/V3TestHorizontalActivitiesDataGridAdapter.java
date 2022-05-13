package com.v_chek_host.vcheckhost.V3.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.SharedPreferenceManager;
import com.v_chek_host.vcheckhost.V3.V3HomeActivity;
import com.v_chek_host.vcheckhost.V3.models.V3GetAllActivitiesDataModel;
import com.v_chek_host.vcheckhost.services.entity.ParentData;
import com.v_chek_host.vcheckhostsdk.ModelCheckActivity;
import com.v_chek_host.vcheckhostsdk.utils.Utility;

import java.util.ArrayList;
import java.util.List;

public class V3TestHorizontalActivitiesDataGridAdapter extends RecyclerView.Adapter<V3TestHorizontalActivitiesDataGridAdapter.MyViewHolder> implements Filterable {
    List<V3GetAllActivitiesDataModel.AllActivity> list;
    List<V3GetAllActivitiesDataModel.AllActivity> listFilter;
    Context context;
    EditText etVinNumber;
    Button btnTest;
    EditText etAccesoryDesc;
    EditText etAccessCode;
    String ACC_CODE, ACC_DESC;
    Gson metaGson;
    String activityNameStr, activityIdStr;
    private ParentData parentData;
    private ParentData.PrimaryParams primaryParams;
    private List<ParentData.PrimaryParams> primaryParamsList;
    String vinMandateStr;
    int vinMaxLength;

    public V3TestHorizontalActivitiesDataGridAdapter(List<V3GetAllActivitiesDataModel.AllActivity> list, Context context) {
        this.list = list;
        this.listFilter = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_all_activities_items_data, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        V3GetAllActivitiesDataModel.AllActivity titlename = listFilter.get(position);
        holder.tvActivitiesname.setText(titlename.getActivityName());
        if (!TextUtils.isEmpty(titlename.getActivityThumbnailUrl())) {
            CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
            circularProgressDrawable.setStrokeWidth(5f);
            circularProgressDrawable.setCenterRadius(30f);
            circularProgressDrawable.start();
            Glide.with(context).load(titlename.getActivityThumbnailUrl()).placeholder(circularProgressDrawable).into(holder.imgActivityUrl);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityIdStr = titlename.getActivityId() + "";
                if (titlename.getIsVinMandatory() == 0) {
                    openBottomDialogSheet(titlename);
                } else {
                    submitActivity("11111111111", titlename.getMlModelParameters().get(0).getDefaultValue(), titlename.getMlModelParameters().get(1).getDefaultValue(),titlename.getActivityName());
                }
            }
        });
        holder.tvActivitiesname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityIdStr = titlename.getActivityId() + "";
                if (titlename.getIsVinMandatory() == 0) {
                    openBottomDialogSheet(titlename);
                } else {
                    submitActivity("11111111111", titlename.getMlModelParameters().get(0).getDefaultValue(), titlename.getMlModelParameters().get(1).getDefaultValue(),titlename.getActivityName());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFilter.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    listFilter = list;
                } else {
                    List<V3GetAllActivitiesDataModel.AllActivity> filteredList = new ArrayList<>();
                    for (V3GetAllActivitiesDataModel.AllActivity row : list) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getActivityName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    listFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listFilter = (ArrayList<V3GetAllActivitiesDataModel.AllActivity>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvActivitiesname;
        public ImageView imgActivityUrl;
        public CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvActivitiesname = (TextView) itemView.findViewById(R.id.tvActivityName);
            imgActivityUrl = (ImageView) itemView.findViewById(R.id.iv_activity_img);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    public void openBottomDialogSheet(V3GetAllActivitiesDataModel.AllActivity titlename) {
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.test_activities_bottom_sheet_layout, null);
        etVinNumber = (EditText) view.findViewById(R.id.et_vin_number);
        etAccessCode = (EditText) view.findViewById(R.id.et_access_code);
        etAccesoryDesc = (EditText) view.findViewById(R.id.et_accessory_desc);
        btnTest = (Button) view.findViewById(R.id.btn_test);
        if (titlename.getMlModelParameters().size() > 0) {
            etAccessCode.setText(titlename.getMlModelParameters().get(0).getDefaultValue());
            etAccesoryDesc.setText(titlename.getMlModelParameters().get(1).getDefaultValue());
        }
        BottomSheetDialog dialog = new BottomSheetDialog(context, R.style.DialogStyle);
        dialog.setContentView(view);
        dialog.show();
        vinMaxLength = 17;
        etVinNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == vinMaxLength) {
                    String vinNumber = etVinNumber.getText().toString();
                    if (vinNumber.length() == vinMaxLength) {
                        Utility.hideKeyboard((V3HomeActivity) context);
                        //  gotoCaptureScreen(vinNumber);
                        // checkPermissionsAgin(vinNumber);
                    }
                }
            }
        });
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValidate = false;
                if (TextUtils.isEmpty(etVinNumber.getText().toString().trim())) {
                    etVinNumber.setError("Please enter vin number");
                    etVinNumber.requestFocus();
                    isValidate = true;
                } else {
                    etVinNumber.setError(null);
                }
                if (TextUtils.isEmpty(etAccessCode.getText().toString().trim())) {
                    etAccessCode.setError("Please enter accesory code");
                    etAccessCode.requestFocus();
                    isValidate = true;
                } else {
                    etAccessCode.setError(null);
                }
                if (TextUtils.isEmpty(etAccesoryDesc.getText().toString().trim())) {
                    etAccesoryDesc.setError("Please enter accesory description");
                    etAccesoryDesc.requestFocus();
                    isValidate = true;
                } else {
                    etAccesoryDesc.setError(null);
                }

                if (!isValidate) {
                    dialog.dismiss();
                    //open sdk
                    submitActivity(etVinNumber.getText().toString().trim(),
                            etAccessCode.getText().toString(),
                            etAccesoryDesc.getText().toString(),titlename.getActivityName());

                }
            }
        });
    }

    public void submitActivity(String vin_number, String accCode, String accDesc,String activityname) {
        SharedPreferenceManager.setActivityName(context, activityname);
        SharedPreferenceManager.setActivityId(context, activityIdStr);
        String VIN_NO = vin_number;
        ACC_CODE = accCode;
        ACC_DESC = accDesc;
        primaryParamsList = new ArrayList<>();
        primaryParams = new ParentData.PrimaryParams("Acc.Cd.", ACC_CODE);
        primaryParamsList.add(primaryParams);
        primaryParams = new ParentData.PrimaryParams("Acc.Desc.", ACC_DESC);
        primaryParamsList.add(primaryParams);
        parentData = new ParentData(new ParentData.MetaData(activityIdStr,
                SharedPreferenceManager.getLanguagecode(context), SharedPreferenceManager.getConnString(context),
                SharedPreferenceManager.getSiteCode(context), "000", "Demo",
                SharedPreferenceManager.getUserid(context),
                SharedPreferenceManager.getUsername(context),
                "VIN", VIN_NO, SharedPreferenceManager.getParentId(context),
                SharedPreferenceManager.getTenantId(context),
                /*SharedPreferenceManager.getSiteCode(context)*/"CAN"),
                new ParentData.PrimaryData(primaryParamsList));
        metaGson = new Gson();
        String metaString = metaGson.toJson(parentData);
        Bundle bundle = new Bundle();
        bundle.putString("input_data", metaString);
        Intent intent = new Intent(context, ModelCheckActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
