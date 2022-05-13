package com.v_chek_host.vcheckhost.V2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;

import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.v_chek_host.vcheckhost.BaseActivity;
import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.SharedPreferenceManager;
import com.v_chek_host.vcheckhost.V3.V3PassAndFailActivity;
import com.v_chek_host.vcheckhost.V3.models.MultipleImageUploadModel;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by pc on 2017/3/20.
 *
 * @author liuzhongjun
 */

public class CustomRecordActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "CustomRecordActivity";

    private Executor executor = Executors.newFixedThreadPool(1);

    //UI
    private ImageView mRecordControl;
    private ImageView mPauseRecord;
    private SurfaceView surfaceView;
    private SurfaceHolder mSurfaceHolder;
    private Chronometer mRecordTime;
    private ImageView overLayImage;
    private ImageView flashImage;

    //DATA

    //Recorder status identification
    private int mRecorderState;

    public static final int STATE_INIT = 0;
    public static final int STATE_RECORDING = 1;
    public static final int STATE_PAUSE = 2;


    //    private boolean isRecording;// Mark to determine whether it is currently recording
//    private boolean isPause; //Pause flag
    private long mPauseTime = 0;           //Recording pause interval

    // 存储文件
    private File mVecordFile;
    private Camera mCamera;
    Camera.Parameters parameters;
    private MediaRecorder mediaRecorder;
    private String currentVideoFilePath;
    private String saveVideoPath = "";
    private boolean isFlashOn = false;


    private MediaRecorder.OnErrorListener OnErrorListener = new MediaRecorder.OnErrorListener() {
        @Override
        public void onError(MediaRecorder mediaRecorder, int what, int extra) {
            try {
                if (mediaRecorder != null) {
                    mediaRecorder.reset();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        initView();
    }

    private void initView() {
        surfaceView = (SurfaceView) findViewById(R.id.record_surfaceView);
        mRecordControl = (ImageView) findViewById(R.id.record_control);
        ImageView ivClose = (ImageView) findViewById(R.id.image_close);
        mRecordTime = (Chronometer) findViewById(R.id.record_time);
        mPauseRecord = (ImageView) findViewById(R.id.record_pause);
        overLayImage = (ImageView) findViewById(R.id.overlay_image);
        flashImage = (ImageView) findViewById(R.id.img_flash_light);
        SwitchMaterial switchVideo = findViewById(R.id.video_switch);
        ImageView ivPhoto = findViewById(R.id.ivPhoto);
        mRecordControl.setOnClickListener(this);
        mPauseRecord.setOnClickListener(this);
        flashImage.setOnClickListener(this);
        mPauseRecord.setEnabled(false);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });
        switchVideo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    closeActivity();
                }
            }
        });
        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });

        //SurfaceHolder
        mSurfaceHolder = surfaceView.getHolder();
        // Surface
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        //
        mSurfaceHolder.setFixedSize(SharedPreferenceManager.getModelVideoWidth(this),
                SharedPreferenceManager.getModelVideoHeight(this));
        //
        mSurfaceHolder.setKeepScreenOn(true);
        //
        mSurfaceHolder.addCallback(mSurfaceCallBack);
        Picasso.get()
                .load(SharedPreferenceManager.getOverlayUrl(this))
                .into(overLayImage, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                    }
                });
    }


    private SurfaceHolder.Callback mSurfaceCallBack = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            initCamera();
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
            if (mSurfaceHolder.getSurface() == null) {
                return;
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            releaseCamera();
        }
    };


    /**
     * @throws IOException
     * @author liuzhongjun
     */
    private void initCamera() {

        if (mCamera != null) {
            releaseCamera();
        }

        mCamera = Camera.open();
        if (mCamera == null) {
            Toast.makeText(this, "Failed to get the camera！", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            //SurfaceHolder
            mCamera.setPreviewDisplay(mSurfaceHolder);
            //CameraParams
            configCameraParams();
            //
            mCamera.startPreview();
        } catch (IOException e) {

            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }


    /**
     * @author lip
     * @date 2015-3-16
     */
    private void configCameraParams() {
        Camera.Parameters params = mCamera.getParameters();
        //
        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            params.set("orientation", "portrait");
            mCamera.setDisplayOrientation(90);
        } else {
            params.set("orientation", "landscape");
            mCamera.setDisplayOrientation(0);
        }
        //
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        //Recording
        params.setRecordingHint(true);
        //
        if (params.isVideoStabilizationSupported())
            params.setVideoStabilization(true);
        mCamera.setParameters(params);
    }


    /**
     * @author liuzhongjun
     * @date 2016-2-5
     */
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    /**
     *
     */
    public boolean startRecord() {

        initCamera();
        //Camera
        mCamera.unlock();
        configMediaRecorder();
        try {
            //
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     *
     */
    public void stopRecord() {
        //
        mediaRecorder.setOnErrorListener(null);
        mediaRecorder.setPreviewDisplay(null);
        //
        mediaRecorder.stop();
        mediaRecorder.reset();
        //
        mediaRecorder.release();
        mediaRecorder = null;
    }

    public void pauseRecord() {


    }

    /**
     *
     */
   /* private void mergeRecordVideoFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String[] str = new String[]{saveVideoPath, currentVideoFilePath};
                    //将2个视频文件合并到 append.mp4文件下
                    VideoUtils.appendVideo(CustomRecordActivity.this, getSDPath(CustomRecordActivity.this) + "append.mp4", str);
                    File reName = new File(saveVideoPath);
                    File f = new File(getSDPath(CustomRecordActivity.this) + "append.mp4");
                    //再将合成的append.mp4视频文件 移动到 saveVideoPath 路径下
                    f.renameTo(reName);
                    if (reName.exists()) {
                        f.delete();
                        new File(currentVideoFilePath).delete();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }*/

    /**
     * UI
     */
    private void refreshControlUI() {
        if (mRecorderState == STATE_INIT) {
            //
            mRecordTime.setBase(SystemClock.elapsedRealtime());
            mRecordTime.start();

            mRecordControl.setImageResource(R.drawable.camera_stop_icon);
            //1s
            mRecordControl.setEnabled(false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRecordControl.setEnabled(true);
                }
            }, 1000);
            //     mPauseRecord.setVisibility(View.VISIBLE);
            //   mPauseRecord.setEnabled(true);


        } else if (mRecorderState == STATE_RECORDING) {
            mPauseTime = 0;
            mRecordTime.stop();

            mRecordControl.setImageResource(R.drawable.camera_start_icon2);
            mPauseRecord.setVisibility(View.GONE);
            mPauseRecord.setEnabled(false);
        }

    }

    /**
     * UI
     */
    private void refreshPauseUI() {
        if (mRecorderState == STATE_RECORDING) {
            mPauseRecord.setImageResource(R.drawable.camera_start_icon2);

            mPauseTime = SystemClock.elapsedRealtime();
            mRecordTime.stop();
        } else if (mRecorderState == STATE_PAUSE) {
            mPauseRecord.setImageResource(R.drawable.camera_stop_icon);

            if (mPauseTime == 0) {
                mRecordTime.setBase(SystemClock.elapsedRealtime());
            } else {
                mRecordTime.setBase(SystemClock.elapsedRealtime() - (mPauseTime - mRecordTime.getBase()));
            }
            mRecordTime.start();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.record_control: {
                if (mRecorderState == STATE_INIT) {

                    if (saveImageInLocalStorage() == null)
                        return;

                    //configMediaRecorder
                    currentVideoFilePath = saveImageInLocalStorage() + getVideoName();
                    //
                    if (!startRecord())
                        return;

                    refreshControlUI();

                    mRecorderState = STATE_RECORDING;
                    // flashImage.setVisibility(View.VISIBLE);

                } else if (mRecorderState == STATE_RECORDING) {
                    //
                    stopRecord();
                    //Camera
                    mCamera.lock();
                    releaseCamera();

                    refreshControlUI();

                    //
                    if ("".equals(saveVideoPath)) {
                        saveVideoPath = currentVideoFilePath;
                    } else {
                        //  mergeRecordVideoFile();
                    }
                    mRecorderState = STATE_INIT;


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(CustomRecordActivity.this, PlayVideoActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("videoPath", saveVideoPath);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }
                    }, 1000);
                } else if (mRecorderState == STATE_PAUSE) {

                    Intent intent = new Intent(CustomRecordActivity.this, PlayVideoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("videoPath", saveVideoPath);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
                break;
            }

            case R.id.record_pause: {
                if (mRecorderState == STATE_RECORDING) {
                    mCamera.autoFocus(new Camera.AutoFocusCallback() {
                        @Override
                        public void onAutoFocus(boolean success, Camera camera) {
                            if (success)
                                CustomRecordActivity.this.mCamera.cancelAutoFocus();
                        }
                    });

                    stopRecord();

                    refreshPauseUI();


                    if ("".equals(saveVideoPath)) {
                        saveVideoPath = currentVideoFilePath;
                    } else {
                        //   mergeRecordVideoFile();
                    }

                    mRecorderState = STATE_PAUSE;

                } else if (mRecorderState == STATE_PAUSE) {

                    if (saveImageInLocalStorage() == null)
                        return;

                    //configMediaRecorder
                    currentVideoFilePath = saveImageInLocalStorage() + getVideoName();

                    //
                    if (!startRecord()) {
                        return;
                    }
                    refreshPauseUI();

                    mRecorderState = STATE_RECORDING;
                }
            }

            case R.id.img_flash_light: {
                boolean hasFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
                if (hasFlash && mRecorderState == STATE_RECORDING) {

                    parameters = mCamera.getParameters();

                    if (!isFlashOn) {
                        isFlashOn = true;
                        flashImage.setImageResource(R.drawable.ic_baseline_flash_on_24);
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        //     mCamera.setDisplayOrientation(0);
                        //   mCamera.setParameters(parameters);
                    } else {
                        isFlashOn = false;
                        flashImage.setImageResource(R.drawable.ic_baseline_flash_off_24);
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    }
                    // camera.setDisplayOrientation(0);
                    mCamera.setParameters(parameters);

                }
            }
        }

    }


    /**
     * MediaRecorder()
     */

    private void configMediaRecorder() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.reset();
        mediaRecorder.setCamera(mCamera);
        mediaRecorder.setOnErrorListener(OnErrorListener);

        //SurfaceView
        mediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());

        //1.
        //  mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        //2. mp4
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        //3.
        //mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        //
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        //
//        mediaRecorder.setAudioChannels(2);
        //
//        mediaRecorder.setMaxDuration(60 * 1000);

//        mediaRecorder.setMaxFileSize(1024 * 1024);

        CamcorderProfile mProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_480P);
        mediaRecorder.setAudioEncodingBitRate(44100);
        if (mProfile.videoBitRate > 2 * 1024 * 1024)
            mediaRecorder.setVideoEncodingBitRate(2 * 1024 * 1024);
        else
            mediaRecorder.setVideoEncodingBitRate(1024 * 1024);
        mediaRecorder.setVideoFrameRate(mProfile.videoFrameRate);


        mediaRecorder.setOrientationHint(0);

        mediaRecorder.setVideoSize(1280, 720);


        mediaRecorder.setOutputFile(currentVideoFilePath);
    }

    /**
     *
     */
    public static String getSDPath(Context context) {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Toast.makeText(context, "Please check if your SD card exists！", Toast.LENGTH_SHORT).show();
            return null;
        }

        File sdDir = Environment.getExternalStorageDirectory();
        File eis = new File(sdDir.toString() + "/RecordVideo/");
        if (!eis.exists()) {
            eis.mkdir();
        }
        return sdDir.toString() + "/RecordVideo/";
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
        if (filePath != null) {
            filePath = filePath + "RecordVideo";
        }

        try {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;

    }

    private String getVideoName() {
        return "VID_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".mp4";
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closeActivity();
    }

    public void closeActivity() {
        startActivity(new Intent(getApplicationContext(), CaptureActivity.class));
        finish();
    }
}
