package com.v_chek_host.vcheckhostsdk;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;


import java.util.List;
import java.util.regex.Pattern;

public class VinNumberActivity extends AppCompatActivity {
    private EditText etVinNumber;
    public static String parentId = "";
    public static String modelID = "";
    public static String conString = "";
    public static String userId = "";
    public static String empId = "";
    public static String siteCode = "";
    public static String siteEmployeeCode = "";
    Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
    private Context ctx;
    private Activity activity;
    public InputFilter vinFilter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            boolean keepOriginal = true;
            StringBuilder sb = new StringBuilder(end - start);
            for (int i = start; i < end; i++) {
                char c = source.charAt(i);
                if (!regex.matcher(String.valueOf(c)).matches()) // put your condition here
                    sb.append(c);
                else
                    keepOriginal = false;
            }
            if (keepOriginal)
                return null;
            else {
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(sb);
                    TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                    return sp;
                } else {
                    return sb;
                }
            }
        }
    };

    private TextView txtData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vin_number);
        ctx = VinNumberActivity.this;
        activity = VinNumberActivity.this;
        checkPermissions();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            parentId = bundle.getString(StrConstant.parentID);
            modelID = bundle.getString(StrConstant.modelID);
            conString = bundle.getString(StrConstant.conString);
            userId = bundle.getString(StrConstant.userId);
            empId = bundle.getString(StrConstant.empId);
            siteCode = bundle.getString(StrConstant.siteCode);
            siteEmployeeCode = empId;
        }
        etVinNumber = (EditText) findViewById(R.id.et_vin_number);
        txtData = (TextView) findViewById(R.id.txt_data);

//        txtData.setText(Details.result);
//        gotoCaptureScreen("");

        etVinNumber.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(17), vinFilter});
        etVinNumber.requestFocus();
        etVinNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 17) {
                    String vinNumber = etVinNumber.getText().toString();
                    if (vinNumber.length() == 17) {
                        Utility.hideKeyboard(activity);
                        checkPermissionsAgin(vinNumber);
                    }
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Details.result = "";
    }

    void gotoCaptureScreen(String vinNumber) {
        Bundle bundle = new Bundle();
        bundle.putString("vin_number", vinNumber);
        Intent intent = new Intent(VinNumberActivity.this, ClassifierActivity.class);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

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
                            //Navigation.findNavController(view).navigate(R.id.photoBasedDataCameraFragment);
                            // Intent intent = new Intent(MainActivity.this, CamaraActivity.class);
                            // startActivity(intent);

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

    private void checkPermissionsAgin(String vinNumberData) {
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
                            //Navigation.findNavController(view).navigate(R.id.photoBasedDataCameraFragment);
                            // Intent intent = new Intent(MainActivity.this, CamaraActivity.class);
                            // startActivity(intent);
                            gotoCaptureScreen(vinNumberData);

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
}