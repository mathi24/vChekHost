package com.v_chek_host.vcheckhost.V2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.v_chek_host.vcheckhost.BaseActivity;
import com.v_chek_host.vcheckhost.PreviewImageActivity;
import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.SharedPreferenceManager;
import com.v_chek_host.vcheckhost.StrConstants;
import com.v_chek_host.vcheckhost.V2.models.UploadVideoData;
import com.v_chek_host.vcheckhost.V2.utils.MyVideoView;
import com.v_chek_host.vcheckhost.V2.utils.mMediaController;
import com.v_chek_host.vcheckhost.V3.V3PassAndFailActivity;
import com.v_chek_host.vcheckhost.database.UploadSQLiteHandler;
import com.v_chek_host.vcheckhost.services.ApiClient;
import com.v_chek_host.vcheckhost.services.VideoUploadService;
import com.v_chek_host.vcheckhostsdk.customview.Classifier;
import com.v_chek_host.vcheckhostsdk.database.SQLiteHandler;
import com.v_chek_host.vcheckhostsdk.model.api.ApiService;
import com.v_chek_host.vcheckhostsdk.model.entity.Imageitem;
import com.v_chek_host.vcheckhostsdk.model.entity.InspktResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class PlayVideoActivity extends BaseActivity implements MediaPlayer.OnPreparedListener, mMediaController.MediaPlayerControl {
    public static final String TAG = "PlayVideo";
    private MyVideoView videoView;
    private mMediaController controller;
    private String mVideoPath;
    private ImageView btnCancel, btnSave, ivClose;
    private ProgressBar progressBar;
    UploadSQLiteHandler sqLiteHandler;
    public ImageView btnAdd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.now_playvideo);
        mVideoPath = getIntent().getExtras().getString("videoPath");

        File sourceVideoFile = new File(mVideoPath);
        videoView = (MyVideoView) findViewById(R.id.videoView);
        btnCancel = (ImageView) findViewById(R.id.btn_cancel);
        btnSave = (ImageView) findViewById(R.id.btn_save);
        ivClose = (ImageView) findViewById(R.id.image_close);
        progressBar = findViewById(R.id.progress_bar);
        btnAdd = findViewById(R.id.ivSubmit);
        int screenW = getWindowManager().getDefaultDisplay().getWidth();
        int screenH = getWindowManager().getDefaultDisplay().getHeight();
        videoView.setVideoScale(screenW, screenH);
        // I have commentted
        /* FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) videoView.getLayoutParams();
         *//* params.width = screenW;
        params.height = screenW * 4 / 3;*//*
        params.width = SharedPreferenceManager.getModelVideoWidth(this);
        params.height = SharedPreferenceManager.getModelVideoHeight(this);
        params.gravity = Gravity.TOP;
        videoView.setLayoutParams(params);*/
        sqLiteHandler = new UploadSQLiteHandler(this);
        videoView.setOnPreparedListener(this);
        controller = new mMediaController(this);

        ;


        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            videoView.setVideoURI(Uri.fromFile(sourceVideoFile));
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSave.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                callMlModelIMageInsert(mVideoPath);
                /*sqLiteHandler.addUploadVideo(new UploadVideoData(SharedPreferenceManager.getModelId(PlayVideoActivity.this),
                        V2ModelVideoTrainingDataSetActivity.vVideoResult, mVideoPath, 0));*/
                //finish();
               /* List<UploadVideoData> uploadVideoDataList = sqLiteHandler.getVideoUploadData();
             int updated =   sqLiteHandler.updateUploadStatus(uploadVideoDataList.get(0).getKeyId());
                if(uploadVideoDataList.size()>0){
                    Toast.makeText(PlayVideoActivity.this,"Data Size "+ uploadVideoDataList.size(),Toast.LENGTH_SHORT).show();
                }*/
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAdd.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                callMlModelIMageInsert(mVideoPath);
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void deleteVideo() {
        File fdelete = new File(mVideoPath);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                System.out.println("file Deleted :" + mVideoPath);
            } else {
                System.out.println("file not Deleted :" + mVideoPath);
            }
        }
    }

    public void callMlModelIMageInsert(String mVideoPath) {

        File file = new File(mVideoPath);
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("video/mp4"), file);

        /*Call<ResponseBody> call = ApiClient.getInstance().getApi().insertMlModelVideo(StrConstants.API_VALUE,
                SharedPreferenceManager.getModelId(this), V2ModelVideoTrainingDataSetActivity.vVideoResult, requestBody1);*/
        Call<ResponseBody> call = ApiClient.getInstance().getApi().insertMlModelVideo(StrConstants.API_VALUE,
                V3PassAndFailActivity.modelID, V3PassAndFailActivity.isPassAndFail ? 1 : 0, requestBody1);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                btnAdd.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                int statusCode = response.code();
                if (statusCode == 200) {
                    deleteVideo();
                    //finish();
                    sqLiteHandler.addUploadVideo(new UploadVideoData(SharedPreferenceManager.getModelId(PlayVideoActivity.this),
                            V2ModelVideoTrainingDataSetActivity.vVideoResult, mVideoPath, 0));
                    /*Intent intent = new Intent(PlayVideoActivity.this, V3PassAndFailActivity.class);
                    startActivity(intent);*/
                    finish();
                } else {
                    Toast.makeText(PlayVideoActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                btnAdd.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(PlayVideoActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        deleteVideo();
        startActivity(new Intent(getApplicationContext(), CustomRecordActivity.class));
        finish();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        controller.setMediaPlayer(this);
        controller.setAnchorView((ViewGroup) findViewById(R.id.fl_videoView_parent));
        controller.show();

    }

    @Override
    public void start() {
        videoView.start();
    }

    @Override
    public void pause() {
        if (videoView.isPlaying()) {
            videoView.pause();
        }
    }

    @Override
    public int getDuration() {
        return videoView.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return videoView.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        videoView.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return videoView.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return videoView.canPause();
    }

    @Override
    public boolean canSeekBackward() {
        return videoView.canSeekBackward();
    }

    @Override
    public boolean canSeekForward() {
        return videoView.canSeekForward();
    }

    @Override
    public boolean isFullScreen() {
        return false;
    }

    @Override
    public void toggleFullScreen() {

    }
}
