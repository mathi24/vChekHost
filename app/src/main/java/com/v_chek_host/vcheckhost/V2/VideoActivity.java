package com.v_chek_host.vcheckhost.V2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.v_chek_host.vcheckhost.BaseActivity;
import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.V2.models.TrainDataActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * @author Paul
 * @since 2018.10.04
 */
public class VideoActivity extends BaseActivity {

    private static final String ARG_VIDEO = "video";

   /* public static Intent createIntent(Context context, String videoUrl) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra(ARG_VIDEO, videoUrl);
        return intent;
    }*/


    PlayerView mPlayerView;

    private SimpleExoPlayer mPlayer;
    private String mVideoUrl;

    private long mCurrentMillis;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        mPlayerView=findViewById(R.id.full_screen_video);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
      //  loadArgs();
        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            mVideoUrl = intent.getString("VideoUrl");
        }else {

        }
     //   mVideoUrl ="https://vchkcollectionproduction.blob.core.windows.net/vchekmlmodelimagesdevelopment/225ded18-a00b-402a-be91-a7cbe3d17352_19052021_134709_369059.mp4";
    }

    @Override
    protected void onResume() {
        super.onResume();
        startPlayer();
    }

    @Override
    protected void onPause() {
        release();
        super.onPause();
    }

   /* private void loadArgs() {
        if (getIntent() == null) {
            finish();
        }
        mVideoUrl = getIntent().getStringExtra(ARG_VIDEO);
    }*/

    private void startPlayer() {
        if (mPlayer != null) {
            return;
        }
        mPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this),
                new DefaultTrackSelector());
        mPlayerView.setPlayer(mPlayer);

        DefaultDataSourceFactory dataSourceFactory =
                new DefaultDataSourceFactory(this, Util.getUserAgent(this, "player"));
        ExtractorMediaSource extractorMediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(mVideoUrl));

        boolean isResuming = mCurrentMillis != 0;
        mPlayer.prepare(extractorMediaSource, isResuming, false);
        mPlayer.setPlayWhenReady(true);
        if (isResuming) {
            mPlayer.seekTo(mCurrentMillis);
        }

    }

    private void release() {
        if (mPlayer == null) {
            return;
        }
        mCurrentMillis = mPlayer.getCurrentPosition();
        mPlayer.release();
        mPlayer = null;
    }
}