package com.v_chek_host.vcheckhost;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.extensions.HdrImageCaptureExtender;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DataCollectionCamaraActivity extends BaseActivity {
    PreviewView mPreviewView;
    private ImageView imgCaptureBtn, takenImage;
    private ProgressBar progressBar;
    private String position = "2wdatacollectionimage";
    private int captureMode = 0;
    private Executor executor = Executors.newSingleThreadExecutor();
    private ImageCapture imageCapture;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};
    Camera camera;
    android.hardware.Camera.Parameters parameters;
    private Context ctx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);
        ctx = DataCollectionCamaraActivity.this;
        mPreviewView = findViewById(R.id.view_finder);
        imgCaptureBtn = findViewById(R.id.imgCapture);
        takenImage = findViewById(R.id.taken_image);
        progressBar = findViewById(R.id.progress_bar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        imgCaptureBtn.setEnabled(true);
        takenImage.setVisibility(View.GONE);
        if (progressBar != null && progressBar.isShown()) {
            progressBar.setVisibility(View.GONE);
        }
        checkPermissions();
        Log.v("callled", "called");
    }

    private void startCamera() {

        final ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {

                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    bindPreview(cameraProvider);

                } catch (ExecutionException | InterruptedException e) {
                    // No errors need to be handled for this Future.
                    // This should never be reached.
                }
            }
        }, ContextCompat.getMainExecutor(this));
    }

    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {

        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .build();
        ImageCapture.Builder builder = new ImageCapture.Builder();
        //Vendor-Extensions (The CameraX extensions dependency in build.gradle)
        HdrImageCaptureExtender hdrImageCaptureExtender = HdrImageCaptureExtender.create(builder);

        // Query if extension is available (optional).
        if (hdrImageCaptureExtender.isExtensionAvailable(cameraSelector)) {
            // Enable the extension if available.
            hdrImageCaptureExtender.enableExtension(cameraSelector);
        }
        if (captureMode == 0) {
            Log.v("hello55", "clckied");
            imageCapture = builder
                    .setTargetRotation(this.getWindowManager().getDefaultDisplay().getRotation())
                    .setFlashMode(ImageCapture.FLASH_MODE_AUTO)
                    .build();

            preview.setSurfaceProvider(mPreviewView.createSurfaceProvider());
            cameraProvider.unbindAll();

            camera = cameraProvider.bindToLifecycle((LifecycleOwner) this,
                    cameraSelector, preview, imageAnalysis, imageCapture);
        }
        findViewById(R.id.imgCapture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // showProgressDialog();
                // imgSkip.setEnabled(false);
                Log.v("hello", "clckied");
                progressBar.setVisibility(View.VISIBLE);
                imgCaptureBtn.setEnabled(false);
                // imgSkip.setEnabled(false);
                String root = Environment.getExternalStorageDirectory().toString();
                final File file = new File(root + "/" + position + ".png");//images/media/2918");
                Log.v("filleeee", file.getAbsolutePath() + "");

               /* file.delete();
                if (file.exists()) {
                    try {
                        file.getCanonicalFile().delete();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (file.exists()) {
                        getApplicationContext().deleteFile(file.getName());
                    }
                }*/

                ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(file).build();
                Log.v("hello22", "clckied");
                imageCapture.takePicture(outputFileOptions, executor, new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void run() {
                             //    cameraProvider.unbind(preview);

                                String msg = "Pic captured at " + file.getAbsolutePath();
                                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                takenImage.setVisibility(View.VISIBLE);
                                takenImage.setImageBitmap(bitmap);
                                try {
                                    bitmap = Utility.rotateImageIfRequired(ctx, bitmap);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                //bitmap = Utility.imageRotation(getApplicationContext(), file.getAbsolutePath(), bitmap);
                                // bitmap = resize(bitmap, 500, 7000);
                                int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
                                bitmap = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                                OutputStream os = null;
                                try {
                                    os = new BufferedOutputStream(new FileOutputStream(file));
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                                    os.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (file.exists()) {
                                    progressBar.setVisibility(View.GONE);
                                    imgCaptureBtn.setEnabled(true);

                                    // imgSkip.setEnabled(true);
                                    // Glide.with(CameraActivity.this).load(new File(file.getPath())).into(imgCaptureView);
                                    //Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
                                    // isCancelActivity = true;
                                    /*isVisibleImageView(file);
                                    bitmap.recycle();*/
                                    Bundle bundle = new Bundle();
                                    bundle.putString("file", file + "");
                                    Intent intent = new Intent(DataCollectionCamaraActivity.this, PreviewImageActivity.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    cameraProvider.unbind(preview);
                                    finish();
                                    //  Navigation.findNavController(view).navigate(R.id.photobasedPreviewFragment, bundle);
                                   /* if (Utility.isConnectingToInternet(CamaraActivity.this, ctx)) {
                                        insertImageApiCall(file);
                                    } else {
                                        takenImage.setVisibility(View.GONE);
                                    }*/
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException error) {
                        Log.v("ImageCaptureException", error + "");
                        error.printStackTrace();
                    }
                });
            }
        });
    }

   /* private void insertImageApiCall(File file) {

        try {

            InputStream in = new FileInputStream(file.getPath());
            Log.v("filepathhhhh", file.getPath());
            byte[] buf = new byte[in.available()];
            while (in.read(buf) != -1) ;
            final RequestBody requestBody = RequestBody
                    .create(MediaType.parse("application/octet-stream"), buf);
            Call<UploadingPhotoImageModel> call = ApiClient.getInstance().getApi().insertCollectionImage(StrConstants.APIvalue, requestBody);
            call.enqueue(new Callback<UploadingPhotoImageModel>() {
                @Override
                public void onResponse(Call<UploadingPhotoImageModel> call, Response<UploadingPhotoImageModel> response) {
                    int statusCode = response.code();
                    progressBar.setVisibility(View.GONE);
                    Log.v("phottttttttt", statusCode + "");
                    if (statusCode == StrConstants.STATUS_CODE_200) {
                        // Navigation.findNavController(view).popBackStack(R.id.photoBasedDataCameraFragment, true);
                        finish();
                    } else {
                        takenImage.setVisibility(View.GONE);
                        Toast.makeText(ctx, R.string.txt_something_went_wrong_please_try_again_later, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<UploadingPhotoImageModel> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    takenImage.setVisibility(View.GONE);
                    Log.v("errorMag", t.getMessage());
                    if (t.getMessage().equals("timeout")) {
                        Toast.makeText(ctx, R.string.txt_poor_internet_connection_please_check_your_internet_and_try_again, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ctx, R.string.txt_something_went_wrong_please_try_again_later, Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }*/

    private void checkPermissions() {
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
                            //  Navigation.findNavController(view).navigate(R.id.photoBasedDataCameraFragment);
                            startCamera();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}