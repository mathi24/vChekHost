package com.v_chek_host.vcheckhost.V2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.SharedPreferenceManager;
import com.v_chek_host.vcheckhostsdk.utils.Utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import cat.ereza.customactivityoncrash.config.CaocConfig;

public class HostErrorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_error);
        System.out.println("Host Exception log arrived");
        TextView errorDetailsText = findViewById(R.id.error_details);
        errorDetailsText.setText(CustomActivityOnCrash.getStackTraceFromIntent(getIntent()));
        String errors= CustomActivityOnCrash.getStackTraceFromIntent(getIntent());
        errors=errors!=null?errors:"";

        System.out.println("Host Exception log arrived");
        Utility.exceptionLog(this, Utility.getLog(errors),
                SharedPreferenceManager.getUserid(this), SharedPreferenceManager.getSiteCode(this),
                SharedPreferenceManager.getActivityId(this), SharedPreferenceManager.getModelId(this)+"","1");
//        new SaveErrorLog(CustomErrorActivity.this).execute(logData);

        Button restartButton = findViewById(R.id.restart_button);
        final CaocConfig config = CustomActivityOnCrash.getConfigFromIntent(getIntent());

        if (config == null) {
            //This should never happen - Just finish the activity to avoid a recursive crash.
            finish();
            return;
        }
        if (config.isShowRestartButton() && config.getRestartActivityClass() != null) {
            restartButton.setText("Restart app");
            restartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomActivityOnCrash.restartApplication(HostErrorActivity.this, config);
                }
            });
        } else {
            restartButton.setText("Restart app");
            restartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(getPackageName());
                    startActivity(LaunchIntent);
                }
            });
        }
    }

    private static class SaveErrorLog extends AsyncTask<String, Integer, String> {
        private WeakReference<HostErrorActivity> activityRef;

        private SaveErrorLog(HostErrorActivity activity) {
            this.activityRef = new WeakReference<>(activity);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... params) {
            activityRef.get().writeFileOnInternalStorage(params[0]);
            return null;
        }
    }

    String error_message="";
    String error_caused="";
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void writeFileOnInternalStorage(String sBody) {
        System.out.println("Error log content: " + sBody);
        if (storagePermission()) {
//            closeDialogStorage();
            System.out.println("Storage permission given");
            try {
                boolean isAvail = true;
                File externalStorageDir = Environment.getExternalStorageDirectory();
                File myFile = new File(externalStorageDir, "8_Bolts.txt");
                if (!myFile.exists()) {
                    isAvail = myFile.createNewFile();
                }
                if (isAvail && sBody != null && !sBody.isEmpty()) {
                    try {
                        Writer writer = new BufferedWriter(new OutputStreamWriter(
                                new FileOutputStream(myFile, true), StandardCharsets.UTF_8));
                        writer.append(sBody);
                        writer.close();
                        error_message = "";
                        error_caused = "";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        } else {
            System.out.println("Storage permission not given");
        }
    }

    private boolean storagePermission() {
        boolean isStoragePermitGiven;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager
                .PERMISSION_GRANTED) {
            isStoragePermitGiven = false;
            //You can show permission rationale if shouldShowRequestPermissionRationale() returns true.
            //I will skip it for this demo
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (PermissionUtils.neverAskAgainSelected(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                    displayAskAndNeverAskAgainDialog(true);
//                } else {
//                    if (PermissionUtils.getRatinaleDisplayStatus(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                        displayAskAndNeverAskAgainDialog(false);
//                    } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        122);
//                    }
//                }
            }
        } else {
            isStoragePermitGiven = true;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            isStoragePermitGiven = true;
        }
        return isStoragePermitGiven;
    }
}