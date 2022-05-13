package com.v_chek_host.vcheckhost.V2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;


import androidx.annotation.Nullable;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;


import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.v_chek_host.vcheckhost.BaseActivity;
import com.v_chek_host.vcheckhost.R;

import java.io.IOException;
import java.util.Formatter;
import java.util.Locale;



/*
 * Created by: Jens Klingenberg (jensklingenberg.de)
 * GPLv3
 *
 *
 * */

public class PlayerActivity extends BaseActivity {


    /* SurfaceView svPlayer;
     ImageButton prev;
     ImageButton rew;
     ImageButton btnPlay;
     ImageButton ffwd;
     ImageButton next;
     TextView timeCurrent;
     SeekBar mediacontrollerProgress;
     TextView playerEndTime;
     ImageButton fullscreen;
     LinearLayout linMediaController;
     FrameLayout playerFrameLayout;

     private SimpleExoPlayer exoPlayer;
     private boolean bAutoplay = true;
     private boolean bIsPlaying = false;
     private boolean bControlsActive = true;

     private Handler handler;
     private StringBuilder mFormatBuilder;
     private Formatter mFormatter;
     private DataSource.Factory dataSourceFactory;

     private String HLSurl = "http://walterebert.com/playground/video/hls/sintel-trailer.m3u8";
     private String mp4URL = "https://vchkcollectionproduction.blob.core.windows.net/vchekmlmodelimagesdevelopment/225ded18-a00b-402a-be91-a7cbe3d17352_19052021_134709_369059.mp4";
     private String dash= "http://www.youtube.com/api/manifest/dash/id/3aa39fa2cc27967f/source/youtube?as=fmp4_audio_clear,fmp4_sd_hd_clear&sparams=ip,ipbits,expire,source,id,as&ip=0.0.0.0&ipbits=0&expire=19000000000&signature=A2716F75795F5D2AF0E88962FFCD10DB79384F29.84308FF04844498CE6FBCE4731507882B8307798&key=ik0";

     private String userAgent =
         "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:40.0) Gecko/20100101 Firefox/40.0";*/
    private String mp4URL = "";
    // creating a variable for exoplayerview.
    SimpleExoPlayerView exoPlayerView;

    // creating a variable for exoplayer
    SimpleExoPlayer exoPlayer;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    private ImageView btnCancel, btnSave, ivClose;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_main_layout);
        mp4URL = getIntent().getExtras().getString("videoPath");
        exoPlayerView = findViewById(R.id.idExoPlayerVIew);
        progressBar = findViewById(R.id.progress_bar);
        ivClose = (ImageView) findViewById(R.id.image_close);
        progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
 /*   svPlayer = findViewById(R.id.sv_player);
    prev = findViewById(R.id.prev);
    rew = findViewById(R.id.rew);
    btnPlay = findViewById(R.id.btnPlay);
    ffwd = findViewById(R.id.ffwd);
    next = findViewById(R.id.next);
    timeCurrent = findViewById(R.id.time_current);
    mediacontrollerProgress = findViewById(R.id.mediacontroller_progress);
    playerEndTime = findViewById(R.id.player_end_time);
    fullscreen = findViewById(R.id.fullscreen);
    linMediaController = findViewById(R.id.lin_media_controller);

    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    handler=new Handler();
    initDataSource();
   // initDashPlayer(dash);
    //initHLSPlayer(HLSurl);
    initMp4Player(mp4URL);*/

    /*if (bAutoplay) {
      if (exoPlayer != null) {
        exoPlayer.setPlayWhenReady(true);
        bIsPlaying = true;
        setProgress();
      }
    }*/
        try {

            // bandwisthmeter is used for
            // getting default bandwidth
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

            // track selector is used to navigate between
            // video using a default seekbar.
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));

            // we are adding our track selector to exoplayer.
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

            // we are parsing a video url
            // and parsing its video uri.
            Uri videouri = Uri.parse(mp4URL);

            // we are creating a variable for datasource factory
            // and setting its user agent as 'exoplayer_view'
            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");

            // we are creating a variable for extractor factory
            // and setting it to default extractor factory.
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            // we are creating a media source with above variables
            // and passing our event handler as null,
            MediaSource mediaSource = new ExtractorMediaSource(videouri, dataSourceFactory, extractorsFactory, null, null);

            // inside our exoplayer view
            // we are setting our player
            exoPlayerView.setPlayer(exoPlayer);

            // we are preparing our exoplayer
            // with media source.
            exoPlayer.prepare(mediaSource);

            // we are setting our exoplayer
            // when it is ready.
            exoPlayer.addListener(new Player.EventListener() {
                @Override
                public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                }

                @Override
                public void onLoadingChanged(boolean isLoading) {

                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    switch (playbackState) {
                        case Player.STATE_ENDED:
                            Log.i("EventListenerState", "Playback ended!");
                            exoPlayer.setPlayWhenReady(false);
                            break;
                        case Player.STATE_READY:
                            Log.i("EventListenerState", "Playback State Ready!");
                            progressBar.setVisibility(View.GONE);
                            exoPlayer.setPlayWhenReady(true);
                            break;
                        case Player.STATE_BUFFERING:
                            Log.i("EventListenerState", "Playback buffering");
                            progressBar.setVisibility(View.VISIBLE);

                            break;
                        case Player.STATE_IDLE:

                            break;

                    }
                }

                @Override
                public void onRepeatModeChanged(int repeatMode) {

                }

                @Override
                public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {

                }

                @Override
                public void onPositionDiscontinuity(int reason) {

                }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                }

                @Override
                public void onSeekProcessed() {

                }
            });


        } catch (Exception e) {
            // below line is used for
            // handling our errors.
            Log.e("TAG", "Error : " + e.toString());
        }
    }

/*  private void initDataSource() {
     dataSourceFactory =
        new DefaultDataSourceFactory(this, Util.getUserAgent(this, "yourApplicationName"),
            new DefaultBandwidthMeter());
  }

  private void initMediaControls() {
    initSurfaceView();
    initPlayButton();
    initSeekBar();
    initFwd();
    initPrev();
    initRew();
    initNext();
  }

  private void initNext() {
    next.requestFocus();
    next.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        exoPlayer.seekTo(exoPlayer.getDuration());
      }
    });
  }

  private void initRew() {
    rew.requestFocus();
    rew.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        exoPlayer.seekTo(exoPlayer.getCurrentPosition() - 10000);
      }
    });
  }

  private void initPrev() {
    prev.requestFocus();
    prev.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        exoPlayer.seekTo(0);
      }
    });
  }

  private void initFwd() {
    ffwd.requestFocus();
    ffwd.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        exoPlayer.seekTo(exoPlayer.getCurrentPosition() + 10000);
      }
    });
  }



  private void initSurfaceView() {
    svPlayer.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        toggleMediaControls();
      }
    });
  }

  private String stringForTime(int timeMs) {
    mFormatBuilder = new StringBuilder();
    mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
    int totalSeconds = timeMs / 1000;

    int seconds = totalSeconds % 60;
    int minutes = (totalSeconds / 60) % 60;
    int hours = totalSeconds / 3600;

    mFormatBuilder.setLength(0);
    if (hours > 0) {
      return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
    } else {
      return mFormatter.format("%02d:%02d", minutes, seconds).toString();
    }
  }

  private void setProgress() {
    mediacontrollerProgress.setProgress(0);
    mediacontrollerProgress.setMax(0);
    mediacontrollerProgress.setMax((int) exoPlayer.getDuration() / 1000);

    handler = new Handler();
    //Make sure you update Seekbar on UI thread
    handler.post(new Runnable() {

      @Override
      public void run() {
        if (exoPlayer != null && bIsPlaying) {
          mediacontrollerProgress.setMax(0);
          mediacontrollerProgress.setMax((int) exoPlayer.getDuration() / 1000);
          int mCurrentPosition = (int) exoPlayer.getCurrentPosition() / 1000;
          mediacontrollerProgress.setProgress(mCurrentPosition);
          timeCurrent.setText(stringForTime((int) exoPlayer.getCurrentPosition()));
          playerEndTime.setText(stringForTime((int) exoPlayer.getDuration()));

          handler.postDelayed(this, 1000);
        }
      }
    });
  }

  private void initSeekBar() {
    mediacontrollerProgress.requestFocus();

    mediacontrollerProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!fromUser) {
          // We're not interested in programmatically generated changes to
          // the progress bar's position.
          return;
        }

        exoPlayer.seekTo(progress * 1000);
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {

      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {

      }
    });

    mediacontrollerProgress.setMax(0);
    mediacontrollerProgress.setMax((int) exoPlayer.getDuration() / 1000);
  }

  private void toggleMediaControls() {

    if (bControlsActive) {
      hideMediaController();
      bControlsActive = false;
    } else {
      showController();
      bControlsActive = true;
      setProgress();
    }
  }

  private void showController() {
    linMediaController.setVisibility(View.VISIBLE);
    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
  }

  private void hideMediaController() {
    linMediaController.setVisibility(View.GONE);
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
  }

  private void initPlayButton() {
    btnPlay.requestFocus();
    btnPlay.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (bIsPlaying) {
          exoPlayer.setPlayWhenReady(false);
          bIsPlaying = false;
        } else {
          exoPlayer.setPlayWhenReady(true);
          bIsPlaying = true;
          setProgress();
        }
      }
    });
  }

  private void initMp4Player(String mp4URL) {

    MediaSource sampleSource =
        new ExtractorMediaSource(Uri.parse(mp4URL), dataSourceFactory, new DefaultExtractorsFactory(),
            handler, new ExtractorMediaSource.EventListener() {
          @Override
          public void onLoadError(IOException error) {

          }
        });


    initExoPlayer(sampleSource);
  }





  private void initExoPlayer(MediaSource sampleSource) {
    if (exoPlayer == null) {
      TrackSelection.Factory videoTrackSelectionFactory =
          new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
      TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

      // 2. Create the player
      exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
    }

    exoPlayer.prepare(sampleSource);

    exoPlayer.setVideoSurfaceView(svPlayer);

    exoPlayer.setPlayWhenReady(true);

    initMediaControls();
  }*/

  /*private void initHLSPlayer(String dashUrl) {

    MediaSource sampleSource = new HlsMediaSource(Uri.parse(dashUrl), dataSourceFactory, handler,
        this);


   initExoPlayer(sampleSource);
  }*/

 /* @Override
  public void onLoadStarted(DataSpec dataSpec, int dataType, int trackType, Format trackFormat,
                            int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs,
                            long mediaEndTimeMs, long elapsedRealtimeMs) {

  }

  @Override
  public void onLoadCompleted(DataSpec dataSpec, int dataType, int trackType, Format trackFormat,
                              int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs,
                              long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded) {

  }

  @Override
  public void onLoadCanceled(DataSpec dataSpec, int dataType, int trackType, Format trackFormat,
                             int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs,
                             long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded) {

  }

  @Override
  public void onLoadError(DataSpec dataSpec, int dataType, int trackType, Format trackFormat,
                          int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs,
                          long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded,
                          IOException error, boolean wasCanceled) {

  }

  @Override
  public void onUpstreamDiscarded(int trackType, long mediaStartTimeMs, long mediaEndTimeMs) {

  }

  @Override
  public void onDownstreamFormatChanged(int trackType, Format trackFormat, int trackSelectionReason,
                                        Object trackSelectionData, long mediaTimeMs) {

  }*/

    @Override
    protected void onPause() {
        super.onPause();
        exoPlayer.setPlayWhenReady(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        exoPlayer.setPlayWhenReady(false);
    }
}
