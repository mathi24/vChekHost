package com.v_chek_host.vcheckhost.V2;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.v_chek_host.vcheckhost.BaseActivity;
import com.v_chek_host.vcheckhost.DataCollectionCamaraActivity;
import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.SharedPreferenceManager;
import com.v_chek_host.vcheckhost.StrConstants;
import com.v_chek_host.vcheckhost.Utility;
import com.v_chek_host.vcheckhost.V2.adapters.ModelTrainingDataAdapetr;
import com.v_chek_host.vcheckhost.V2.models.ModelTrainingSetDataModel;
import com.v_chek_host.vcheckhost.V2.models.UploadVideoData;
import com.v_chek_host.vcheckhost.services.ApiClient;
import com.v_chek_host.vcheckhost.services.VideoUploadService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class V2ModelVideoTrainingDataSetActivity extends BaseActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private List<ModelTrainingSetDataModel.Message> images;
    private ModelTrainingDataAdapetr mAdapter;
    private ProgressBar progressBar;
    private Context ctx;
    // public static int model = 0;
    //public ProgressDialog progressDialog;
    public ImageView imageViewBack;
    public Button btnAddPassImages, btnAddFailImages;
    private TextView txtCount, txtModelTitleName,txtListType;
    private String modelName = "";
    public static int vVideoResult = 0;
    public Handler mHandler;
    public Runnable runnable;
    //PhotoTaker photoTaker;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v2_model_training_data_set);
       /* this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        ctx = V2ModelVideoTrainingDataSetActivity.this;
        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            modelName = intent.getString("ModelName");
        }
        images = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        imageViewBack = (ImageView) findViewById(R.id.image_back);
        btnAddPassImages = (Button) findViewById(R.id.btn_add_pass_images);
        btnAddPassImages.setText("PASS VIDEO");
        btnAddFailImages = (Button) findViewById(R.id.btn_add_fail_images);
        btnAddFailImages.setText("FAIL VIDEO");
        txtCount = (TextView) findViewById(R.id.txt_count);
        txtListType = (TextView) findViewById(R.id.txt_list_type);
        txtListType.setText("Video List");
        txtModelTitleName = (TextView) findViewById(R.id.txt_model_title_name);
        txtModelTitleName.setText(modelName + " - " + "Training Data Set");
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
       // mAdapter = new ModelTrainingDataAdapetr(images, ctx);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ctx, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
       // recyclerView.setAdapter(mAdapter);
        txtCount.setText("( " + images.size() + " )");
        //callModelTrainedDataSet();
       // createPhotoTaker();
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Intent intent = new Intent(V2ModelTrainingDataSetActivity.this, V2DashboardActivity.class);
                //startActivity(intent);
                finish();

            }
        });
        btnAddPassImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vVideoResult = 1;
                cameraPermission();

            }
        });
        btnAddFailImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vVideoResult = 0;
                cameraPermission();
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(ctx, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
             //  startActivity(new Intent(getApplicationContext(),VideoActivity.class));
                Bundle bundle = new Bundle();
                bundle.putString("VideoUrl", images.get(position).getMlModelImageUrl());
                Intent intent = new Intent(ctx, VideoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        mHandler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                callModelTrainedDataSet();
            }
        };
        //mHandler.postDelayed(runnable, 1000);

    }

    private void callModelTrainedDataSet() {
        if (Utility.isConnectingToInternet(V2ModelVideoTrainingDataSetActivity.this, ctx)) {
            progressBar.setVisibility(View.VISIBLE);
            Call<ModelTrainingSetDataModel> call = ApiClient.getInstance().getApi().
                    modelTrainedDataSet(StrConstants.API_VALUE, String.valueOf(SharedPreferenceManager
                                    .getModelId(this)),"video");
            call.enqueue(new Callback<ModelTrainingSetDataModel>() {
                @Override
                public void onResponse(Call<ModelTrainingSetDataModel> call, Response<ModelTrainingSetDataModel> response) {
                    progressBar.setVisibility(View.GONE);
                    int statusCode = response.code();
                    if (statusCode == 200) {

                        if (response.body().getMessage() != null) {
                            List<ModelTrainingSetDataModel.Message> trainDataModels = response.body().getMessage();
                            images = new ArrayList<>();
                            /*for (int i = 0; i < trainDataModels.size(); i++) {
                                images.add(trainDataModels.get(i));
                            }*/
                            images = trainDataModels;
                            txtCount.setText("( " + images.size() + " )");
                          //  mAdapter = new ModelTrainingDataAdapetr(images, ctx);
                           // recyclerView.setAdapter(mAdapter);
                           // mAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(V2ModelVideoTrainingDataSetActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                    mHandler.postDelayed(runnable, 1000*15);
                }

                @Override
                public void onFailure(Call<ModelTrainingSetDataModel> call, Throwable t) {
                    Toast.makeText(V2ModelVideoTrainingDataSetActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    mHandler.postDelayed(runnable, 1000*15);
                }
            });
        }
    }

    private void cameraPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
                            //Navigation.findNavController(view).navigate(R.id.photoBasedDataCameraFragment);
                            // Intent intent = new Intent(MainActivity.this, CamaraActivity.class);
                            // startActivity(intent);
                          //  openCamera();
                            //createPhotoTaker();
                         //  photoTaker.captureImage();
                     //       startActivity(new Intent(V2ModelVideoTrainingDataSetActivity.this,CaptureActivity.class));
                            startActivity(new Intent(getApplicationContext(),CustomRecordActivity.class));
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }


    private void showSettingsDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ctx);
        builder.setTitle(getString(R.string.txt_need_permissions));
        builder.setMessage(getString(R.string.txt_app_settings_permissions));
        builder.setPositiveButton(getString(R.string.txt_goto_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton(getString(R.string.txt_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", this.getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void openCamera() {
        Intent intent = new Intent(V2ModelVideoTrainingDataSetActivity.this, DataCollectionCamaraActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        callModelTrainedDataSet();
      /*  if(!isMyServiceRunning(VideoUploadService.class)){
            startService(new Intent(V2ModelVideoTrainingDataSetActivity.this, VideoUploadService.class));
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(runnable);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Intent intent = new Intent(V2ModelTrainingDataSetActivity.this, V2DashboardActivity.class);
        // startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}