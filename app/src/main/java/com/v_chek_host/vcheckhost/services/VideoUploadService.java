package com.v_chek_host.vcheckhost.services;

import android.Manifest;
import android.app.DownloadManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.v_chek_host.vcheckhost.SharedPreferenceManager;
import com.v_chek_host.vcheckhost.StrConstants;
import com.v_chek_host.vcheckhost.V2.PlayVideoActivity;
import com.v_chek_host.vcheckhost.V2.V2ModelVideoTrainingDataSetActivity;
import com.v_chek_host.vcheckhost.V2.models.UploadVideoData;
import com.v_chek_host.vcheckhost.database.UploadSQLiteHandler;
import com.v_chek_host.vcheckhostsdk.downloadutil.CheckForSDCard;
import com.v_chek_host.vcheckhostsdk.downloadutil.Utils;
import com.v_chek_host.vcheckhostsdk.service.ModelDownloadService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Timer;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class VideoUploadService extends Service {
    int counter = 0;
    public String[] uris;
    File outputFile = null;
    static final int UPDATE_INTERVAL = 1000;
    private Timer timer = new Timer();
    public Handler mHandler;
    public Runnable runnable;
    UploadSQLiteHandler sqLiteHandler;
    private final IBinder binder = new MyBinder();

    public class MyBinder extends Binder {
        VideoUploadService getService() {
            return VideoUploadService.this;
        }
    }

    @Override
    public void onCreate() {
        sqLiteHandler = new UploadSQLiteHandler(this);
        mHandler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                File videoFile = null;
                List<UploadVideoData> uploadVideoDataList = sqLiteHandler.getVideoUploadData();
                if (uploadVideoDataList.size() > 0) {
                    videoFile = new File(uploadVideoDataList.get(0).getVideoLocation());
                    if (videoFile.exists())
                        callMlModelIMageInsert(uploadVideoDataList);
                    else {
                        sqLiteHandler.updateUploadStatus(uploadVideoDataList.get(0).getKeyId());
                        mHandler.postDelayed(runnable, 1000 * 60);
                    }
                } else {
                    mHandler.postDelayed(runnable, 1000 * 60);
                }
            }
        };
        mHandler.postDelayed(runnable, 1000);
    }


    public void callMlModelIMageInsert(List<UploadVideoData> uploadVideoDataList) {
        String mVideoPath = uploadVideoDataList.get(0).getVideoLocation();
        File file = new File(mVideoPath);
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("video/mp4"), file);

        Call<ResponseBody> call = ApiClient.getInstance().getApi().insertMlModelVideo(StrConstants.API_VALUE,
                SharedPreferenceManager.getModelId(this), uploadVideoDataList.get(0).getUploadType(), requestBody1);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    deleteVideo(mVideoPath);
                    sqLiteHandler.updateUploadStatus(uploadVideoDataList.get(0).getKeyId());
                    mHandler.postDelayed(runnable, 1000 * 60);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mHandler.postDelayed(runnable, 1000 * 60);
            }
        });

    }

    public void deleteVideo(String mVideoPath) {
        File fdelete = new File(mVideoPath);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                System.out.println("file Deleted :" + mVideoPath);
            } else {
                System.out.println("file not Deleted :" + mVideoPath);
            }
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        //return null;
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //	 Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(runnable);
        //  Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

}