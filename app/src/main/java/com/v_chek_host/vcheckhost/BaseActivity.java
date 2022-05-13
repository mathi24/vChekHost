package com.v_chek_host.vcheckhost;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.v_chek_host.vcheckhost.V2.HostErrorActivity;


import java.util.Locale;

import cat.ereza.customactivityoncrash.config.CaocConfig;

public class BaseActivity extends AppCompatActivity {
    ProgressDialog progressBar;

   BaseActivity activity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
        try {
            CaocConfig.Builder.create()
                    .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT)
                    .enabled(true)
                    .showRestartButton(false)
                    .minTimeBetweenCrashesMs(1)
                    .errorActivity(HostErrorActivity.class)
                    .showErrorDetails(false)
                    .apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
