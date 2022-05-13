package com.v_chek_host.vcheckhost.V2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.v_chek_host.vcheckhost.AllImagesDataModel;
import com.v_chek_host.vcheckhost.DataCollectionActivity;
import com.v_chek_host.vcheckhost.GallaryAdapter;
import com.v_chek_host.vcheckhost.LoginActivity;
import com.v_chek_host.vcheckhost.MainActivity;
import com.v_chek_host.vcheckhost.ParentDetailActivity;
import com.v_chek_host.vcheckhost.ParentDetailsDataActivity;
import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.SharedPreferenceManager;
import com.v_chek_host.vcheckhost.StrConstants;
import com.v_chek_host.vcheckhost.Utility;
import com.v_chek_host.vcheckhost.V2.adapters.ActivityDataAdapter;
import com.v_chek_host.vcheckhost.V2.adapters.TrainDataAdapter;
import com.v_chek_host.vcheckhost.V2.models.TrainDataActivity;
import com.v_chek_host.vcheckhost.V2.models.TrainDataModel;
import com.v_chek_host.vcheckhost.services.ApiClient;

//import com.v_chek_host.vcheckhostsdk.service.ModelDownloadService;
import com.v_chek_host.vcheckhost.services.VideoUploadService;
import com.v_chek_host.vcheckhost.services.entity.ParentData;
import com.v_chek_host.vcheckhostsdk.Details;
import com.v_chek_host.vcheckhostsdk.JSONObjectCallBack;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class V2DashboardActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private List<TrainDataActivity.Message> images;
    private ActivityDataAdapter mAdapter;
    private ProgressBar progressBar;
    private Context ctx;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private int recordCount = 50;
    private int startingIndex = 0;
    private boolean isCallingApi = false;
    boolean isToolBarVisible = false;
    // public ProgressDialog progressDialog;
    public int lastItemPosition = -1;
    public LinearLayout linearLayout;
    public Button btnTest, btnTrain;
    public static int modelId = -1;
    public ImageView imgSearch;
    public LinearLayout llToolabr, llSearchBar;
    public SearchView searchView;
    public ImageView ivBack, ivLogOut;
    private TextView txtCmpyName;
    private ParentData parentData;
    private ParentData.PrimaryParams primaryParams;
    private List<ParentData.PrimaryParams> primaryParamsList;
    private Gson metaGson;
    private Gson modelGson;
    public static String activityName;

    JSONObjectCallBack jsonObjectCallBack = new JSONObjectCallBack() {
        @Override
        public void onSuccess(String jsonObject) {
            if (jsonObject != null) {
                Intent intent = new Intent(V2DashboardActivity.this, ParentDetailsDataActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("parentdata", jsonObject);
                startActivity(intent);
                finishAffinity();
                // Log.v("kjhjhjk",jsonObject+"");
            } else {
                Toast.makeText(V2DashboardActivity.this, "No Data", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(String s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v2_dashboard);
        ctx = V2DashboardActivity.this;
        Details.getInstance().setCallback(jsonObjectCallBack);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        images = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        linearLayout = (LinearLayout) findViewById(R.id.linear_layout);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        imgSearch = (ImageView) findViewById(R.id.img_search);
        ivBack = (ImageView) findViewById(R.id.iv_back_icon);
        ivLogOut = (ImageView) findViewById(R.id.iv_log_out);
        btnTest = (Button) findViewById(R.id.btn_test);
        btnTrain = (Button) findViewById(R.id.btn_train);
        llToolabr = (LinearLayout) findViewById(R.id.ll_toolbar);
        llSearchBar = (LinearLayout) findViewById(R.id.ll_search_bar);
        searchView = (SearchView) findViewById(R.id.search_view);
        txtCmpyName = (TextView) findViewById(R.id.txt_cmpy_name);
        txtCmpyName.setText(SharedPreferenceManager.getDisplayName(ctx));
        mAdapter = new ActivityDataAdapter(images, ctx);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ctx, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        /*Intent intent = new Intent(getBaseContext(), ModelDownloadService.class);
        startService(intent);*/
       // callAllModelData();
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(ctx, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (lastItemPosition != -1 && lastItemPosition != position) {
                    images.get(lastItemPosition).setSelected(false);
                }
                linearLayout.setVisibility(View.VISIBLE);
                lastItemPosition = position;
                images.get(position).setSelected(true);
                mAdapter.notifyDataSetChanged();
                if (images.get(position).getIsLive() == 1) {
                    btnTest.setEnabled(true);
                } else {
                    btnTest.setEnabled(false);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        btnTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastItemPosition != -1) {
                   /* modelId = images.get(lastItemPosition).getActivityId();
                    Bundle bundle = new Bundle();
                    bundle.putString("ModelName", images.get(lastItemPosition).getActivityName());
                    Intent intent = new Intent(V2DashboardActivity.this, V2ModelTrainingDataSetActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);*/
                    modelGson=new Gson();
                    String modelString = modelGson.toJson(images.get(lastItemPosition).getModelData());
                    Bundle bundle = new Bundle();
                    bundle.putString("Model", modelString);
                    bundle.putString("ModelName",images.get(lastItemPosition).getActivityName());
                    Intent intent = new Intent(V2DashboardActivity.this, V2ModelActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastItemPosition != -1) {
                   /* if (images.get(lastItemPosition).getActivityId() == 2) {
                        primaryParamsList = new ArrayList<>();
                        primaryParams = new ParentData.PrimaryParams("Acc.Cd.","color");
                        primaryParamsList.add(primaryParams);
                        primaryParams = new ParentData.PrimaryParams("Acc.Desc.","mark");
                        primaryParamsList.add(primaryParams);
                        parentData = new ParentData(new ParentData.MetaData("2",
                                SharedPreferenceManager.getLanguagecode(V2DashboardActivity.this),SharedPreferenceManager.getConnString(V2DashboardActivity.this),
                                SharedPreferenceManager.getSiteId(V2DashboardActivity.this),"000","test",SharedPreferenceManager.getUserid(V2DashboardActivity.this),SharedPreferenceManager.getUsername(V2DashboardActivity.this),
                                "VIN","12345678987456321","2",SharedPreferenceManager.getTenantId(V2DashboardActivity.this),SharedPreferenceManager.getSiteCode(V2DashboardActivity.this)),
                                new ParentData.PrimaryData(primaryParamsList));
                        metaGson=new Gson();
                        String metaString = metaGson.toJson(parentData);
                        Bundle bundle = new Bundle();
                        bundle.putString("input_data", metaString);
                        Intent intent= new Intent(V2DashboardActivity.this, ModelCheckActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }if (images.get(lastItemPosition).getActivityId() == 1){
                        parentData = new ParentData(new ParentData.MetaData("1",
                                SharedPreferenceManager.getLanguagecode(V2DashboardActivity.this),SharedPreferenceManager.getConnString(V2DashboardActivity.this),
                                SharedPreferenceManager.getSiteId(V2DashboardActivity.this),"000","test",SharedPreferenceManager.getUserid(V2DashboardActivity.this),SharedPreferenceManager.getUsername(V2DashboardActivity.this),
                                "VIN","12345678987456321","2",SharedPreferenceManager.getTenantId(V2DashboardActivity.this),SharedPreferenceManager.getSiteCode(V2DashboardActivity.this)),
                                new ParentData.PrimaryData(primaryParamsList));
                        metaGson=new Gson();
                        String metaString = metaGson.toJson(parentData);
                        Bundle bundle = new Bundle();
                        bundle.putString(*//*getString(R.string.key_activity_json)*//*"input_data", metaString);
                        Intent intent= new Intent(V2DashboardActivity.this, ModelCheckActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else {
                        Toast.makeText(ctx, "Please select monroney", Toast.LENGTH_SHORT).show();
                    }*/
                    Bundle bundle = new Bundle();
                    activityName = images.get(lastItemPosition).getActivityName();
                    bundle.putString("activity_name", images.get(lastItemPosition).getActivityName());
                    bundle.putString("activity_id", Integer.toString(images.get(lastItemPosition).getActivityId()));
                    bundle.putString("vin_mandate", Integer.toString(images.get(lastItemPosition).getVinMandate()));
                    bundle.putString("vin_max_lenght", Integer.toString(images.get(lastItemPosition).getVinLength()));
                    bundle.putString("acc_code", images.get(lastItemPosition).getModelParams().get(0).getValue());
                    bundle.putString("acc_desc", images.get(lastItemPosition).getModelParams().get(1).getValue());
                    Intent intent= new Intent(V2DashboardActivity.this, VinInputActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
       /* imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llToolabr.setVisibility(View.GONE);
                llSearchBar.setVisibility(View.VISIBLE);
                searchView.onActionViewExpanded();
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSearchBar.setVisibility(View.GONE);
                llToolabr.setVisibility(View.VISIBLE);
            }
        });*/
        ivLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.clearData(ctx);
                Intent intent = new Intent(V2DashboardActivity.this, V2LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }



    private void callAllModelData() {
        if (Utility.isConnectingToInternet(V2DashboardActivity.this, ctx)) {
            images.clear();
            progressBar.setVisibility(View.VISIBLE);
            Call<TrainDataActivity> call = ApiClient.getInstance().getApi().
                    allActivityData(StrConstants.API_VALUE, SharedPreferenceManager.getTenantId(V2DashboardActivity.this),
                            SharedPreferenceManager.getSiteId(V2DashboardActivity.this));
        /*progressDialog = new ProgressDialog(V2DashboardActivity.this);
        progressDialog.setMessage("Loading.");
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }*/
            call.enqueue(new Callback<TrainDataActivity>() {
                @Override
                public void onResponse(Call<TrainDataActivity> call, Response<TrainDataActivity> response) {
                    progressBar.setVisibility(View.GONE);
                    int statusCode = response.code();
                    if (statusCode == 200) {
                        images.clear();
                        if (response.body().getMessage() != null) {
                            List<TrainDataActivity.Message> trainDataModels = response.body().getMessage();
                            for (int i = 0; i < trainDataModels.size(); i++) {
                                images.add(trainDataModels.get(i));
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(V2DashboardActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TrainDataActivity> call, Throwable t) {
                    Toast.makeText(V2DashboardActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        Intent start = new Intent(Intent.ACTION_MAIN);
        start.addCategory(Intent.CATEGORY_HOME);
        start.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(start);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        callAllModelData();
        linearLayout.setVisibility(View.GONE);
     /*   if (images.size() > 0) {
            for (int i = 0; i < images.size(); i++) {
                if (images.get(i).isSelected()) {
                    images.get(i).setSelected(false);
                    linearLayout.setVisibility(View.GONE);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }*/
    }
}