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

package com.v_chek_host.vcheckhostsdk;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.ImageReader.OnImageAvailableListener;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Base64;
import android.util.Size;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.RequiresApi;

import com.v_chek_host.vcheckhostsdk.customview.Classifier;
import com.v_chek_host.vcheckhostsdk.customview.CustomVisionStrConstant;
import com.v_chek_host.vcheckhostsdk.env.BorderedText;
import com.v_chek_host.vcheckhostsdk.env.Logger;
import com.v_chek_host.vcheckhostsdk.model.PushMonroneyDataModel;
import com.v_chek_host.vcheckhostsdk.model.api.ApiClient;
import com.v_chek_host.vcheckhostsdk.model.entity.Imageitem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ClassifierActivity extends CameraActivity implements OnImageAvailableListener {
    private static final Logger LOGGER = new Logger();
    //  private static final Size DESIRED_PREVIEW_SIZE = new Size(640, 480);
    private static final Size DESIRED_PREVIEW_SIZE = new Size(1360, 960);
    private static final float TEXT_SIZE_DIP = 10;
    private Bitmap rgbFrameBitmap = null;
    private long lastProcessingTimeMs;
    private Integer sensorOrientation;
    private Classifier classifier;
    private BorderedText borderedText;
    /**
     * Input image size of the model along x axis.
     */
    private int imageSizeX;
    /**
     * Input image size of the model along y axis.
     */
    private int imageSizeY;
    public static boolean isVideoRecordStarted = false;
    String mode = "";
    ImageView startVideo;
    ImageView ivClose;
    boolean isSent = false;
    public byte[] monrooneyImage = null;
    public boolean isDetect = false;
    public String vinNumber = "";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            vinNumber = bundle.getString("vin_number");
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.tfe_ic_camera_connection_fragment;
    }

    @Override
    protected Size getDesiredPreviewFrameSize() {
        return DESIRED_PREVIEW_SIZE;
    }

    @Override
    public void onPreviewSizeChosen(final Size size, final int rotation) {
        final float textSizePx =
                TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, TEXT_SIZE_DIP, getResources().getDisplayMetrics());
        borderedText = new BorderedText(textSizePx);
        borderedText.setTypeface(Typeface.MONOSPACE);

        recreateClassifier(getModel(), getDevice(), getNumThreads());
        if (classifier == null) {
            LOGGER.e("No classifier on preview!");
            return;
        }

        previewWidth = size.getWidth();
        previewHeight = size.getHeight();

        sensorOrientation = rotation - getScreenOrientation();
        LOGGER.i("Camera orientation relative to screen canvas: %d", sensorOrientation);

        LOGGER.i("Initializing at size %dx%d", previewWidth, previewHeight);
        rgbFrameBitmap = Bitmap.createBitmap(previewWidth, previewHeight, Config.ARGB_8888);
    }

    @Override
    protected void processImage() {
        rgbFrameBitmap.setPixels(getRgbBytes(), 0, previewWidth, 0, 0, previewWidth, previewHeight);
        final int cropSize = Math.min(previewWidth, previewHeight);

        runInBackground(
                new Runnable() {
                    @Override
                    public void run() {
                        if (classifier != null) {
                            final long startTime = SystemClock.uptimeMillis();
                            final List<Classifier.Recognition> results = classifier.recognizeImage(rgbFrameBitmap, sensorOrientation);
                            lastProcessingTimeMs = SystemClock.uptimeMillis() - startTime;
                            LOGGER.v("Detect: %s", results);

                            runOnUiThread(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            //  Toast.makeText(ClassifierActivity.this,String.format("%.2f", (100 * results.get(0).getConfidence())) + "%",Toast.LENGTH_SHORT).show();


                                            if (ClassifierActivity.isVideoRecordStarted && !isSent) {
                                                resultTextView.setText(results.get(0).getTitle() + " " + String.format("%.2f", (100 * results.get(0).getConfidence())) + "%");
                                                showResultsInBottomSheet(results);
                                                showFrameInfo(previewWidth + "x" + previewHeight);
                                                showCropInfo(imageSizeX + "x" + imageSizeY);
                                                showCameraResolution(cropSize + "x" + cropSize);
                                                showRotationInfo(String.valueOf(sensorOrientation));
                                                showInference(lastProcessingTimeMs + "ms");
                                                int confident;
                                                final float confidence = results.get(0).getConfidence();
                                                final String label = results.get(0).getTitle();
                                                String strConf = String.format("%.2f", confidence * 100f);
                                                float fConf = (Float.parseFloat(strConf)*100);
                                                if (label.equals("Green"))
                                                    confident = CustomVisionStrConstant.CONFIDENCERANGE;
                                                else
                                                    confident = CustomVisionStrConstant.CONFIDENCERANGEBLUE;
                                                if ((fConf) > confident/*MyApplication.getInstance().confidenceRange*/) {
                                                    if (!label.equals("Negative")) {

                                                        System.out.println("Confidence is: label:" + label + ", Confidence:" + fConf);
                                                        System.out.println("Confidence is: non negative label:" + label + ", Confidence:" + fConf);
//                convertBitmapToByte(rotatedBitmap);
                                                        Imageitem imageitem = new Imageitem();
                                                        imageitem.setCarViewName(label);
                                                        imageitem.setConfidence(fConf);
                                                        imageitem.setByteImage(convertBitmapToByte(rgbFrameBitmap));
                                                        imageitem.setBitmap(rgbFrameBitmap);
                                                        if (label.equals("Green") /*|| label.equals("VGreen")*/) {
                                                            imageitem.setCode("100");
                                                            imageitem.setColor(label);
                                                            imageitem.setStatus("PASS");
                                                        } else {
                                                            imageitem.setCode("200");
                                                            imageitem.setColor(label);
                                                            imageitem.setStatus("FAIL");
                                                        }
                                                        isSent = true;
                                                      processResponse(imageitem);
                                                        //  EventBus.getDefault().post(imageitem);
                                                    }
                                                }
//            }else{
//
                                            }
                                        }
                                    });
                        }
                        readyForNextImage();
                    }
                });
    }

    @Override
    protected void onInferenceConfigurationChanged() {
        if (rgbFrameBitmap == null) {
            // Defer creation until we're getting camera frames.
            return;
        }
        final Classifier.Device device = getDevice();
        final Classifier.Model model = getModel();
        final int numThreads = getNumThreads();
        runInBackground(() -> recreateClassifier(model, device, numThreads));
    }

    private void recreateClassifier(Classifier.Model model, Classifier.Device device, int numThreads) {
        if (classifier != null) {
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
            classifier = Classifier.create(this, model, device, numThreads, "new");
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
        imageSizeY = classifier.getImageSizeY();
    }

    void initViewAction() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mode = extras.getString("capturemode");
        }
        startVideo = findViewById(R.id.startCapture);
        ivClose = findViewById(R.id.image_close);
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
//                    chronometer.stop();
//                    stopVideo.setVisibility(View.GONE);
                    startVideo.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                closeActivity();
            }
        });

    }

    private void closeActivity() {
        Intent intent = new Intent(ClassifierActivity.this, VinNumberActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }

    public static byte[] convertBitmapToByte(Bitmap image) {
        Bitmap bmp = image;
//        bmp = getResizedBitmap(bmp);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        encryptImage(byteArray);
//        bmp.recycle();
        return byteArray;
    }

    public static String encryptImage(byte[] imageArray) {
        Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageArray, 0, imageArray.length);
//        imageBitmap = ImageUtils.resize(imageBitmap,250,1000);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        imageBitmap.recycle();
        String s = Base64.encodeToString(byteArray, Base64.NO_WRAP).trim();
        return s;
    }

    public void processResponse(Imageitem imageItem) {
        if (imageItem != null && !isDetect) {
            try {
                System.out.println("highestConfidence " + imageItem.getConfidence() + " " + imageItem.getCarViewName());
                monrooneyImage = imageItem.getByteImage();
                JSONObject jsonObject = new JSONObject();
                JSONObject jsonObjectMetadata = new JSONObject();
                jsonObjectMetadata.put("Name", "Monroney");
                jsonObjectMetadata.put("Version", "1.0");
                jsonObjectMetadata.put("URI", "Erffad23354F");
                JSONObject jsonObjectResult = new JSONObject();
                jsonObjectResult.put("Code", imageItem.getCode());
                jsonObjectResult.put("Color", imageItem.getColor());
                jsonObjectResult.put("QCheck", imageItem.getStatus());
                jsonObjectResult.put("VIN", vinNumber);
                jsonObject.put("MetaData", jsonObjectMetadata);
                jsonObject.put("Results", jsonObjectResult);
                isDetect = true;
                Details.result = jsonObjectMetadata.toString() + "\n" + jsonObjectResult.toString();
                if (Details.getInstance().getCallback() != null) {
                    Details.getInstance().getCallback().onSuccess(jsonObject.toString());
                } else {
                    Utility.exceptionLog(ClassifierActivity.this,
                            Utility.getLog("Parent application call back is null"));
                }
                /*Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageItem.getByteImage(), 0, imageItem.getByteImage().length);
//        imageBitmap = ImageUtils.resize(imageBitmap,250,1000);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                monrooneyImage = stream.toByteArray();
                imageBitmap.recycle();*/
//                Toast.makeText(ctx, "Completed", Toast.LENGTH_SHORT).show();
                callPushMonroneyDataApi(vinNumber, imageItem.getStatus(), jsonObject.toString());
                finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void callPushMonroneyDataApi(String vin, String status, String jsonObject) {
        System.out.println("PushMonroneyMethodCalling" + "afer");

        String imgString = encryptImage(monrooneyImage);
        System.out.println("base64string " + imgString);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(80, TimeUnit.SECONDS);
        httpClient.connectTimeout(80, TimeUnit.SECONDS);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        //logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(new LoggingInterceptor());

        if (status.equals("PASS")) {
            status = "1";
        } else {
            status = "0";
        }
        Call<PushMonroneyDataModel> call = ApiClient.getInstance().getApi().
                pushMonroneyDataV3(StrConstant.API_KEY,
                        vin,
                        VinNumberActivity.empId,
                        VinNumberActivity.userId,
                        VinNumberActivity.parentId,
                        VinNumberActivity.modelID,
                        VinNumberActivity.conString,
                        status,
                        imgString,
                        jsonObject,
                        VinNumberActivity.siteCode,
                        VinNumberActivity.siteEmployeeCode);
        call.enqueue(new Callback<PushMonroneyDataModel>() {
            @Override
            public void onResponse(Call<PushMonroneyDataModel> call, Response<PushMonroneyDataModel> response) {
                System.out.println("pushMonroneyApi success " + response.code());

            }

            @Override
            public void onFailure(Call<PushMonroneyDataModel> call, Throwable t) {
                System.out.println("pushMonroneyApi error " + t.getMessage());
                Utility.exceptionLog(ClassifierActivity.this,
                        Utility.getLog(t.getMessage()));
                   /* String errorData = t.getMessage() + "  " + "Server is Timed out";
                    Utility.exceptionLog(ClassifierActivity.this, errorData);
                    Toast.makeText(ClassifierActivity.this, "Looks like something went wrong. Try again or contact support", Toast.LENGTH_SHORT).show();*/
            }
        });

    }

    @Override
    public synchronized void onResume() {
        super.onResume();
        // EventBus.getDefault().register(this);
        initViewAction();
    }


}
