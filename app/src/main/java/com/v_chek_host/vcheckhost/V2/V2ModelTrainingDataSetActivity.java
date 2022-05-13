package com.v_chek_host.vcheckhost.V2;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.v_chek_host.vcheckhost.PreviewImageActivity;
import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.SharedPreferenceManager;
import com.v_chek_host.vcheckhost.StrConstants;
import com.v_chek_host.vcheckhost.Utility;
import com.v_chek_host.vcheckhost.V2.adapters.ModelTrainingDataAdapetr;
import com.v_chek_host.vcheckhost.V2.models.ModelTrainingSetDataModel;
import com.v_chek_host.vcheckhost.V2.utils.BitmapLocUtils;
import com.v_chek_host.vcheckhost.services.ApiClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;



import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class V2ModelTrainingDataSetActivity extends BaseActivity {
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
    private TextView txtCount, txtModelTitleName;
    private String modelName = "";
    public static int vImageResult = 0;
    //PhotoTaker photoTaker;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v2_model_training_data_set);
       /* this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        ctx = V2ModelTrainingDataSetActivity.this;
        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            modelName = intent.getString("ModelName");
        }
        images = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        imageViewBack = (ImageView) findViewById(R.id.image_back);
        btnAddPassImages = (Button) findViewById(R.id.btn_add_pass_images);
        btnAddFailImages = (Button) findViewById(R.id.btn_add_fail_images);
        txtCount = (TextView) findViewById(R.id.txt_count);
        txtModelTitleName = (TextView) findViewById(R.id.txt_model_title_name);
        txtModelTitleName.setText(modelName + " - " + "Training Data Set");
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        //mAdapter = new ModelTrainingDataAdapetr(images, ctx);
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
                vImageResult = 1;
                cameraPermission();
            //    startActivity(new Intent(getApplicationContext(),CustomRecordActivity.class));
            }
        });
        btnAddFailImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vImageResult = 0;
                cameraPermission();
            }
        });

    }

    /*private void createPhotoTaker() {
        photoTaker = new PhotoTaker(this, new PhotoSize(1280, 960));
        photoTaker.setListener(new PhotoTakerListener() {
            @Override public void onCancel(int action) {
            //    Toast.makeText(V2ModelTrainingDataSetActivity.this, "User canceled", Toast.LENGTH_SHORT).show();
            }

            @Override public void onError(int action) {
                *//*Toast
                        .makeText(V2ModelTrainingDataSetActivity.this, "Something error on "+action, Toast.LENGTH_SHORT)
                        .show();*//*
            }

            @Override public void onFinish(@NonNull Intent intent) {
                File captureTempDir = getApplicationContext().getExternalCacheDir();
                File temp = BitmapLocUtils.getFile(captureTempDir, "temp.jpg");
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(temp.getAbsolutePath(),bmOptions);
                progressBar.setVisibility(View.VISIBLE);
                callMlModelIMageInsert(bitmap);
               *//* if (intent.getData() != null) {
                 //   imageView.setImageURI(intent.getData());
                } else if (intent.getParcelableExtra("data") != null) {
                   //final Bitmap bitmap = intent.getParcelableExtra("data");
                   *//**//*File captureTempDir = getApplicationContext().getExternalCacheDir();
                   File temp = BitmapLocUtils.getFile(captureTempDir, "temp.jpg");
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    Bitmap bitmap = BitmapFactory.decodeFile(temp.getAbsolutePath(),bmOptions);
                   progressBar.setVisibility(View.VISIBLE);
                    callMlModelIMageInsert(bitmap);*//**//*
                   // System.out.println("success");
                   // imageView.setImageBitmap(bitmap);

                   *//**//* PhotoTakerUtils.writeBitmapToFile(bitmap, new File(
                            getExternalFilesDir(Environment.DIRECTORY_PICTURES), "photo.jpg"));*//**//*
                }*//*
            }
        });
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        photoTaker.onActivityResult(requestCode, resultCode, data);
    }*/

    public void callMlModelIMageInsert( Bitmap bitmap) {
      //  try {
            /*InputStream in = new FileInputStream(file.getPath());
            Log.v("filepathhhhh", file.getPath());*/
        Bitmap bmp = bitmap;
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        Bitmap scaledBitmap;
        if(w>h)
            scaledBitmap = Bitmap.createScaledBitmap(bitmap, V2ModelActivity.modelImageWidth, V2ModelActivity.modelImageHeight, true);
        else
            scaledBitmap = Bitmap.createScaledBitmap(bitmap, V2ModelActivity.modelImageHeight, V2ModelActivity.modelImageWidth, true);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
          //  byte[] buf = new byte[in.available()];
          //  while (in.read(buf) != -1) ;
            String value = Base64.encodeToString(byteArray, Base64.DEFAULT);
          /*  System.out.println("sdfffff "+V2ModelTrainingDataSetActivity.vImageResult+"");*/
            Call<ResponseBody> call = ApiClient.getInstance().getApi().insertMlModelImage(StrConstants.API_VALUE,
                    String.valueOf(V2ModelActivity.modelId), value,vImageResult);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                  //  progressBar.setVisibility(View.GONE);
                    int statusCode = response.code();
                    if (statusCode == 200) {
                        //   Intent intent = new Intent(PreviewImageActivity.this, V2ModelTrainingDataSetActivity.class);
                        //  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        // startActivity(intent);
                        // finishAffinity();
                       // finish();
                        callModelTrainedDataSet();
                    } else {
                        Toast.makeText(V2ModelTrainingDataSetActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(V2ModelTrainingDataSetActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        /*} catch (FileNotFoundException e) {
            progressBar.setVisibility(View.GONE);
            e.printStackTrace();
        } catch (IOException e) {
            progressBar.setVisibility(View.GONE);
            e.printStackTrace();
        }*/

    }

    private void callModelTrainedDataSet() {
        if (Utility.isConnectingToInternet(V2ModelTrainingDataSetActivity.this, ctx)) {
            progressBar.setVisibility(View.VISIBLE);
            Call<ModelTrainingSetDataModel> call = ApiClient.getInstance().getApi().
                    modelTrainedDataSet(StrConstants.API_VALUE, String.valueOf(SharedPreferenceManager.getModelId(this)),
                            "image");
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
                        Toast.makeText(V2ModelTrainingDataSetActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ModelTrainingSetDataModel> call, Throwable t) {
                    Toast.makeText(V2ModelTrainingDataSetActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void cameraPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
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
                            startActivity(new Intent(V2ModelTrainingDataSetActivity.this,CaptureActivity.class));
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

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", this.getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void openCamera() {
        Intent intent = new Intent(V2ModelTrainingDataSetActivity.this, DataCollectionCamaraActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        callModelTrainedDataSet();
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