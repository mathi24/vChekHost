package com.v_chek_host.vcheckhost.V2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.v_chek_host.vcheckhost.BaseActivity;
import com.v_chek_host.vcheckhost.ParentDetailsDataActivity;
import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.SharedPreferenceManager;
import com.v_chek_host.vcheckhost.StrConstants;
import com.v_chek_host.vcheckhost.Utility;
import com.v_chek_host.vcheckhost.V2.adapters.TrainDataAdapter;
import com.v_chek_host.vcheckhost.V2.models.TrainDataActivity;
import com.v_chek_host.vcheckhost.V2.models.TrainDataModel;
import com.v_chek_host.vcheckhost.services.ApiClient;
import com.v_chek_host.vcheckhost.services.entity.ParentData;
import com.v_chek_host.vcheckhostsdk.model.entity.Result;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.v_chek_host.vcheckhostsdk.service.ModelDownloadService;

public class V2ModelActivity extends BaseActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private List<TrainDataActivity.MLModelData> mlModelData;
    private TrainDataAdapter mAdapter;
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
    public Button  btnTrainPhoto,btnTrainVideo;
    public static int modelId = -1;
    public static int modelImageWidth = 1280;
    public static int modelImageHeight = 960;
    public static int modelBottomCropHeight = 100;
    public static String modelOverlayUrl;
    public ImageView imgSearch;
    public LinearLayout llToolabr, llSearchBar;
    public SearchView searchView;
    public ImageView ivBack, ivLogOut;
    private TextView txtModelName;
    private ParentData parentData;
    private ParentData.PrimaryParams primaryParams;
    private List<ParentData.PrimaryParams> primaryParamsList;
    private Gson modelGson;
    private String model,activityName;
    private List<TrainDataActivity.MLModelData> modelData;
    public ImageView imageViewBack;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v2_model);
        ctx = V2ModelActivity.this;

        /*this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        Bundle intent = getIntent().getExtras();
        mlModelData = new ArrayList<>();
        if (intent != null) {
            model = intent.getString("Model");
            activityName = intent.getString("ModelName");
            modelGson = new Gson();
            Type modelType = new TypeToken<ArrayList<TrainDataActivity.MLModelData>>() {
            }.getType();
            mlModelData = modelGson.fromJson(model, modelType);
        }
        imageViewBack = (ImageView) findViewById(R.id.image_back);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        linearLayout = (LinearLayout) findViewById(R.id.linear_layout);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        imgSearch = (ImageView) findViewById(R.id.img_search);
        ivBack = (ImageView) findViewById(R.id.iv_back_icon);
        ivLogOut = (ImageView) findViewById(R.id.iv_log_out);

        btnTrainPhoto = (Button) findViewById(R.id.btn_train_photo);
        btnTrainVideo = (Button) findViewById(R.id.btn_train_video);
        llToolabr = (LinearLayout) findViewById(R.id.ll_toolbar);
        llSearchBar = (LinearLayout) findViewById(R.id.ll_search_bar);
        searchView = (SearchView) findViewById(R.id.search_view);
        txtModelName = (TextView) findViewById(R.id.txt_activity_name);
        txtModelName.setText(activityName);
        mAdapter = new TrainDataAdapter(mlModelData, ctx);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ctx, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        /*Intent intent = new Intent(getBaseContext(), ModelDownloadService.class);
        startService(intent);*/
       // callAllModelData();
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(ctx, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (lastItemPosition != -1 && lastItemPosition != position) {
                    mlModelData.get(lastItemPosition).setSelected(false);
                }
                linearLayout.setVisibility(View.VISIBLE);
                lastItemPosition = position;
                mlModelData.get(position).setSelected(true);
                mAdapter.notifyDataSetChanged();
                modelId = mlModelData.get(lastItemPosition).getMlModelId();
                modelImageWidth = mlModelData.get(lastItemPosition).getTrainingImageWidth();
                modelImageHeight = mlModelData.get(lastItemPosition).getTrainingImageHeight();
                modelBottomCropHeight = mlModelData.get(lastItemPosition).getBottomCropHeight();
                modelOverlayUrl = mlModelData.get(lastItemPosition).getTrain_overlay_url();
                SharedPreferenceManager.setModelId(V2ModelActivity.this,modelId);
                SharedPreferenceManager.setModelImageWidth(V2ModelActivity.this,modelImageWidth);
                SharedPreferenceManager.setModelImageHeight(V2ModelActivity.this,modelImageHeight);
                SharedPreferenceManager.setModelBottomCropHeight(V2ModelActivity.this,modelBottomCropHeight);
                SharedPreferenceManager.setOverlayUrl(V2ModelActivity.this,modelOverlayUrl);
                SharedPreferenceManager.setModelVideoHeight(V2ModelActivity.this,mlModelData.get(lastItemPosition).getTrainingVideoHeight());
                SharedPreferenceManager.setModelVideoWidth(V2ModelActivity.this,mlModelData.get(lastItemPosition).getTrainingVideoWidth());
                SharedPreferenceManager.setModelVideoTime(V2ModelActivity.this,mlModelData.get(lastItemPosition).getTrainingVideoTime());
             /*   Bundle bundle = new Bundle();
                bundle.putString("ModelName", mlModelData.get(lastItemPosition).getModelName());
                Intent intent = new Intent(V2ModelActivity.this, V2ModelTrainingDataSetActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);*/
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        btnTrainPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastItemPosition != -1) {
                  /*  modelId = mlModelData.get(lastItemPosition).getMlModelId();
                    Bundle bundle = new Bundle();
                    bundle.putString("ModelName", mlModelData.get(lastItemPosition).getModelName());
                    Intent intent = new Intent(V2ModelActivity.this, V2ModelActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);*/
                    Bundle bundle = new Bundle();
                    bundle.putString("ModelName", mlModelData.get(lastItemPosition).getModelName());
                    Intent intent = new Intent(V2ModelActivity.this, V2ModelTrainingDataSetActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        btnTrainVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastItemPosition != -1) {
                  /*  modelId = mlModelData.get(lastItemPosition).getMlModelId();
                    Bundle bundle = new Bundle();
                    bundle.putString("ModelName", mlModelData.get(lastItemPosition).getModelName());
                    Intent intent = new Intent(V2ModelActivity.this, V2ModelActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);*/
                    Bundle bundle = new Bundle();
                    bundle.putString("ModelName", mlModelData.get(lastItemPosition).getModelName());
                    Intent intent = new Intent(V2ModelActivity.this, V2ModelVideoTrainingDataSetActivity.class);
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
                Intent intent = new Intent(V2ModelActivity.this, V2LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }


   /* private void callAllModelData() {
        if (Utility.isConnectingToInternet(V2ModelActivity.this, ctx)) {
            mlModelData.clear();
            progressBar.setVisibility(View.VISIBLE);
            Call<TrainDataModel> call = ApiClient.getInstance().getApi().allModelData(StrConstants.API_VALUE, "1");
        *//*progressDialog = new ProgressDialog(V2DashboardActivity.this);
        progressDialog.setMessage("Loading.");
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }*//*
            call.enqueue(new Callback<TrainDataModel>() {
                @Override
                public void onResponse(Call<TrainDataModel> call, Response<TrainDataModel> response) {
                    progressBar.setVisibility(View.GONE);
                    int statusCode = response.code();
                    if (statusCode == 200) {
                        mlModelData.clear();
                        if (response.body().getMessage() != null) {
                            List<TrainDataModel.Message> trainDataModels = response.body().getMessage();
                            for (int i = 0; i < trainDataModels.size(); i++) {
                                mlModelData.add(trainDataModels.get(i));
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(V2ModelActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TrainDataModel> call, Throwable t) {
                    Toast.makeText(V2ModelActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }*/

    @Override
    public void onBackPressed() {
        /*Intent start = new Intent(Intent.ACTION_MAIN);
        start.addCategory(Intent.CATEGORY_HOME);
        start.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(start);*/
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
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