/*
 * Copyright 2019 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.v_chek_host.vcheckhost.V2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.media.ImageReader.OnImageAvailableListener;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Size;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.v_chek_host.vcheckhost.PreviewImageActivity;
import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.SharedPreferenceManager;
import com.v_chek_host.vcheckhost.StrConstants;
import com.v_chek_host.vcheckhost.V3.V3FetchImagesListActivity;
import com.v_chek_host.vcheckhost.V3.V3PassAndFailActivity;
import com.v_chek_host.vcheckhost.V3.database.DatabaseClient;
import com.v_chek_host.vcheckhost.V3.database.TrainModel;
import com.v_chek_host.vcheckhost.V3.models.MultipleImageModelResponce;
import com.v_chek_host.vcheckhost.V3.models.MultipleImageUploadModel;
import com.v_chek_host.vcheckhost.services.ApiClient;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CaptureActivity extends TrainCameraActivity implements OnImageAvailableListener {

    // private static Size DESIRED_PREVIEW_SIZE = new Size(640, 480);
    private static Size DESIRED_PREVIEW_SIZE = new Size(1360, 960);
    ;
    private static final float TEXT_SIZE_DIP = 10;
    private Bitmap rgbFrameBitmap = null;
    private long lastProcessingTimeMs;
    private Integer sensorOrientation;
    /*private Classifier classifier;*/
    private String modelOverlayUrl;
    String root = Environment.getExternalStorageDirectory().toString();
    String timeStamp;
    File file;
    /**
     * Input image size of the model along x axis.
     */
    private int imageSizeX;
    /**
     * Input image size of the model along y axis.
     */
    private int imageSizeY;
    public static boolean isVideoRecordStarted = false;

    ImageView startVideo;
    ImageView ivClose;
    ImageView ivCapturePrevieImageView;
    TextView tvSingleAndMulti;
    boolean isSingleAndMulti = false;
    public List<MultipleImageUploadModel> multipleImageUploadModelList = V3PassAndFailActivity.multipleImageUploadModelList;
    public boolean isUploading = false;
    Handler mHandler;
    Runnable runnable;
    boolean isCallingAPI;
    String imagePosition = "";
    String imagePath = "";
    Call<MultipleImageModelResponce> call;
    LinearLayout rotationView;
    int imageUrlPosition = 1;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //fetchImagesDataOnLocal();
        mHandler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                boolean apiCall = false;
                for (int i = 0; i < multipleImageUploadModelList.size(); i++
                ) {
                    if (!apiCall) {
                        if (!multipleImageUploadModelList.get(i).isUploaded()) {
                            apiCall = true;
                            int finalI = i;
                            AsyncTask.execute(new Runnable() {
                                @Override
                                public void run() {
                                    //TODO your background code
                                    callMultipleImageUploadAPI(finalI, multipleImageUploadModelList.get(finalI).getLocalImagePath());

                                }
                            });
                        }
                    }
                }

            }
        };

    }

    @Override
    protected int getLayoutId() {
        return R.layout.ic_camera_connection_fragment;
    }

    @Override
    protected Size getDesiredPreviewFrameSize() {
        DESIRED_PREVIEW_SIZE = new Size(SharedPreferenceManager.getModelImageWidth(this),
                SharedPreferenceManager.getModelImageHeight(this));
        ;
        return DESIRED_PREVIEW_SIZE;
    }

    @Override
    protected String getOverlayUrl() {
        return modelOverlayUrl;
    }

    @Override
    public void onPreviewSizeChosen(final Size size, final int rotation) {


        /*recreateClassifier(getModel(), getDevice(), getNumThreads());
        if (classifier == null) {
            LOGGER.e("No classifier on preview!");
            return;
        }*/
        previewWidth = size.getWidth();
        previewHeight = size.getHeight();
        sensorOrientation = rotation - getScreenOrientation();
        rgbFrameBitmap = Bitmap.createBitmap(previewWidth, previewHeight, Config.ARGB_8888);
    }

    @Override
    protected void processImage() {
        rgbFrameBitmap.setPixels(getRgbBytes(), 0, previewWidth, 0, 0, previewWidth, previewHeight);
        runInBackground(
                new Runnable() {
                    @Override
                    public void run() {
                        if (isVideoRecordStarted) {
                            OutputStream os = null;
                            Bitmap previewBitmap = cropImage(rgbFrameBitmap, previewWidth,
                                    previewHeight, 0, 0, previewWidth,
                                    (previewHeight - SharedPreferenceManager.getModelBottomCropHeight(CaptureActivity.this)));
                            try {
                                imageUrlPosition = imageUrlPosition + 1;
                                timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                                file = new File(saveImageInLocalStorage() + "/train" + imageUrlPosition + ".png");
                                System.out.println("pathhhhhhhhhhhhhhhh " + file.getAbsolutePath());
                                os = new BufferedOutputStream(new FileOutputStream(file));
                                previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);
                                os.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (file.exists()) {

                                //saveFileLocal();
                                Bundle bundle = new Bundle();
                                bundle.putString("file", file + "");
                                if (isSingleAndMulti) {
                                    MultipleImageUploadModel multipleImageUploadModel = new MultipleImageUploadModel(file.getAbsolutePath(), "", false, "");
                                    multipleImageUploadModelList.add(multipleImageUploadModel);
                                    Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ivCapturePrevieImageView.setVisibility(View.VISIBLE);
                                            ivCapturePrevieImageView.setImageBitmap(myBitmap);
                                            isVideoRecordStarted = false;
                                            startVideo.setVisibility(View.VISIBLE);
                                            readyForNextImage();
                                            if (!isUploading) {
                                                isUploading = true;
                                                // mHandler.postDelayed(runnable, 1000);
                                            }
                                            //callMultipleImageUploadAPI();
                                        }
                                    });
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ivCapturePrevieImageView.setVisibility(View.INVISIBLE);
                                            Intent intent = new Intent(CaptureActivity.this, PreviewImageActivity.class);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });

                                }

                            } else {
                                System.out.println("File is not exist");
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {

                                        // Stuff that updates the UI
                                        isVideoRecordStarted = false;
                                        startVideo.setVisibility(View.VISIBLE);
                                        readyForNextImage();
                                    }
                                });

                            }
                        } else
                            readyForNextImage();
                    }
                });
    }


    public static Bitmap cropImage(Bitmap picture, int width, int height, int imageCropX, int imageCropY, int imageCropWidth, int imageCropHeight) {
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(picture, width, height, true);
        Bitmap cropedBitmap = Bitmap.createBitmap(scaledBitmap, imageCropX, imageCropY, imageCropWidth, imageCropHeight);
        return cropedBitmap;
    }

    @Override
    protected void onInferenceConfigurationChanged() {
        if (rgbFrameBitmap == null) {
            // Defer creation until we're getting camera frames.
            return;
        }
      /*  final Classifier.Device device = getDevice();
        final Classifier.Model model = getModel();
        final int numThreads = getNumThreads();*/
        //  runInBackground(() -> recreateClassifier(model, device, numThreads));
    }

    /*private void recreateClassifier(Classifier.Model model, Classifier.Device device, int numThreads) {
     */
    /* if (classifier != null) {
            LOGGER.d("Closing classifier.");
            classifier.close();
            classifier = null;
        }
        if (device == Classifier.Device.GPU
                && (model == Classifier.Model.QUANTIZED_MOBILENET || model == Classifier.Model.QUANTIZED_EFFICIENTNET)) {
            LOGGER.d("Not creating classifier: GPU doesn't support quantized models.");
            runOnUiThread(
                    () -> {
                        Toast.makeText(this, R.string.tfe_ic_gpu_quant_error, Toast.LENGTH_LONG).show();
                    });
            return;
        }
        try {
            LOGGER.d(
                    "Creating classifier (model=%s, device=%s, numThreads=%d)", model, device, numThreads);
            classifier = Classifier.create(this, model, device, numThreads, "new", modelFileName, modelLableName);
        } catch (IOException | IllegalArgumentException e) {
            LOGGER.e(e, "Failed to create classifier.");
            runOnUiThread(
                    () -> {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                    });
            return;
        }

        // Updates the input image size.
        imageSizeX = classifier.getImageSizeX();
        imageSizeY = classifier.getImageSizeY();*//*
    }*/

    void initViewAction() {
        startVideo = findViewById(R.id.startCapture);
        ivClose = findViewById(R.id.image_close);
        tvSingleAndMulti = findViewById(R.id.tvSingleAndMulti);
        ivCapturePrevieImageView = findViewById(R.id.ivCapturePreview);
        SwitchMaterial switchVideo = findViewById(R.id.video_switch);
        ImageView ivVideo = findViewById(R.id.ivVideo);
        isVideoRecordStarted = false;
        startVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isVideoRecordStarted = true;
                startVideo.setVisibility(View.GONE);
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startVideo.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                closeActivity();
            }
        });
        tvSingleAndMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSingleAndMulti) {
                    isSingleAndMulti = true;
                    tvSingleAndMulti.setText("Multi");
                } else {
                    isSingleAndMulti = false;
                    tvSingleAndMulti.setText("Single");
                }
            }
        });
        ivCapturePrevieImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSingleAndMulti) {
                    Intent intent = new Intent(getApplicationContext(), V3FetchImagesListActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        switchVideo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Intent intent = new Intent(CaptureActivity.this, CustomRecordActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        ivVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaptureActivity.this, CustomRecordActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void closeActivity() {
       /* if(PreferenceStorage.getInstance(getApplicationContext()).getVinNeed()==1) {
            Intent intent = new Intent(CaptureActivity.this, VinNumberActivity.class);
           // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }*/
        finish();

    }

    public static byte[] convertBitmapToByte(Bitmap image) {
        Bitmap bmp = image;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public static String encryptImage(byte[] imageArray) {
        String s = Base64.encodeToString(imageArray, Base64.NO_WRAP).trim();
        return s;
    }

    /*public void processResponse(Imageitem imageItem) {

    }*/

    @Override
    public synchronized void onResume() {
        super.onResume();
        // EventBus.getDefault().register(this);
        initViewAction();
        multipleImageUploadModelList.clear();
    }

    @Override
    public void onBackPressed() {
        closeActivity();
    }

    public void saveFileLocal() {
        class SaveImage extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                TrainModel trainModel = new TrainModel();
                trainModel.setImagePATH(file.getAbsolutePath());
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .dao()
                        .insert(trainModel);
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Bundle bundle = new Bundle();
                bundle.putString("file", file + "");
                if (isSingleAndMulti) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ivCapturePrevieImageView.setVisibility(View.VISIBLE);
                            ivCapturePrevieImageView.setImageBitmap(myBitmap);
                            isVideoRecordStarted = false;
                            startVideo.setVisibility(View.VISIBLE);
                            readyForNextImage();
                            //callMultipleImageUploadAPI();
                            callingAPI();
                        }
                    });
                } else {
                    ivCapturePrevieImageView.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(CaptureActivity.this, PreviewImageActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }
        }
        SaveImage saveImage = new SaveImage();
        saveImage.execute();
    }

    public void fetchImagesDataOnLocal() {
        class FetchImages extends AsyncTask<Void, Void, List<TrainModel>> {

            @Override
            protected List<TrainModel> doInBackground(Void... voids) {
                List<TrainModel> trainModelList = DatabaseClient.getInstance(getApplicationContext())
                        .getAppDatabase().dao().getAllCourses();
                return trainModelList;
            }

            @Override
            protected void onPostExecute(List<TrainModel> trainModels) {
                super.onPostExecute(trainModels);
                for (TrainModel trainModel : trainModels) {
                    File file = new File(trainModel.getImagePATH());
                    if (file.exists()) {
                        file.delete();
                    }
                    deleteImagesFromLocal(trainModel);
                }
            }
        }
        FetchImages fetchImages = new FetchImages();
        fetchImages.execute();
    }

    public void deleteImagesFromLocal(TrainModel trainModel) {
        class DeleteImages extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .dao()
                        .delete(trainModel);
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
            }
        }
        DeleteImages deleteImages = new DeleteImages();
        deleteImages.execute();
    }

    public void callMultipleImageUploadAPI(int position, String imagePath) {
        String base64Image = base64Image(imagePath);
        call = ApiClient.getInstance().getApi().getMultipleImageUpload(
                StrConstants.API_VALUE, String.valueOf(V3PassAndFailActivity.modelID), base64Image, SharedPreferenceManager.getUserid(getApplicationContext()));
        call.enqueue(new Callback<MultipleImageModelResponce>() {
            @Override
            public void onResponse(Call<MultipleImageModelResponce> call, Response<MultipleImageModelResponce> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    if (Integer.parseInt(response.body().getResponseCode()) == 200) {
                        if (response.body().getMessage().getStatusCode() == 200) {
                            multipleImageUploadModelList.get(position).setUploaded(true);
                            multipleImageUploadModelList.get(position).setMlModelImageID(response.body().getMessage().getGetMlModelImageId() + "");
                            File file = new File(imagePath);
                            if (file.exists()) {
                                file.delete();
                            }
                            isUploading = false;
                            isCallingAPI = false;
                            //mHandler.postDelayed(runnable, 1000);
                            callingAPI();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MultipleImageModelResponce> call, Throwable t) {
                if (!call.isCanceled()) {
                    isUploading = false;
                    isCallingAPI = false;
                    callingAPI();
                }
                //mHandler.postDelayed(runnable, 1000);
            }
        });
    }

    @Override
    public synchronized void onDestroy() {
        mHandler.removeCallbacks(runnable);
        super.onDestroy();
        if (call != null) {
            call.cancel();
        }
    }

    public String base64Image(String filePath) {
        Bitmap bm = BitmapFactory.decodeFile(filePath);
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, bOut);
        String base64Image = Base64.encodeToString(bOut.toByteArray(), Base64.DEFAULT);
        return base64Image;
    }

    public void callingAPI() {
        if (!isCallingAPI) {
            for (int i = 0; i < multipleImageUploadModelList.size(); i++) {
                if (!multipleImageUploadModelList.get(i).isUploaded()) {
                    isCallingAPI = true;
                    imagePath = multipleImageUploadModelList.get(i).getLocalImagePath();
                    imagePosition = i + "";
                    break;
                }
            }
            if (isCallingAPI) {
                //btnAdd.setEnabled(false);
                // btnAdd.setBackground();
                //btnAdd.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.activity_title_color));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        File file = new File(imagePath);
                        if (file.exists()) {
                            callMultipleImageUploadAPI(Integer.parseInt(imagePosition), imagePath);
                        }
                    }
                });
            }
        }
    }

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(12);
        char tempChar;
        for (int i = 0; i < randomLength; i++) {
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    public String saveImageInLocalStorage() {
        String filePath = null;
        File externalFilesDir = getExternalFilesDir(null);
        String dir = null;
        if (null != externalFilesDir) {
            dir = externalFilesDir.getAbsolutePath();
        }
        String packageName = getPackageName();
        if (!TextUtils.isEmpty(dir)) {
            if (!dir.endsWith(File.separator)) {
                filePath = dir + File.separator;
            } else {
                filePath = dir + packageName + File.separator;
            }
        }
        return filePath;

    }
}
