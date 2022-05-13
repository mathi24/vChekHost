package com.v_chek_host.vcheckhost.V2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.v_chek_host.vcheckhost.BaseActivity;
import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.SharedPreferenceManager;
import com.v_chek_host.vcheckhost.services.entity.ParentData;
import com.v_chek_host.vcheckhostsdk.Details;
import com.v_chek_host.vcheckhostsdk.ModelCheckActivity;
import com.v_chek_host.vcheckhostsdk.utils.PreferenceStorage;
import com.v_chek_host.vcheckhostsdk.utils.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class VinInputActivity extends BaseActivity {
    private TextInputEditText etVinNumber,etAccCode,etAccDesc;
    public TextInputLayout textInputVinNo, textInputAccCode, textInputAccDesc;
    public Button btnTest;
    public static String activityModelString = "";
    Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
    private Context ctx;
    private Activity activity;
    int vinMaxLength;
    String vinMandateStr;
    String activityNameStr,activityIdStr;
    private ParentData parentData;
    private ParentData.PrimaryParams primaryParams;
    private List<ParentData.PrimaryParams> primaryParamsList;
    String ACC_CODE,ACC_DESC;
    Gson metaGson;
    public ImageView imageViewBack;
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

    private TextView txtData,txtActivityTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            activityNameStr =intent.getString("activity_name");
            activityIdStr =intent.getString("activity_id");
            vinMandateStr =intent.getString("vin_mandate");
            ACC_CODE =intent.getString("acc_code");
            ACC_DESC =intent.getString("acc_desc");
            vinMaxLength = Integer.parseInt(intent.getString("vin_max_lenght"));
        }
        ctx = VinInputActivity.this;
        activity = VinInputActivity.this;
        imageViewBack = (ImageView) findViewById(R.id.image_back);
        etVinNumber = (TextInputEditText) findViewById(R.id.txt_edit_vin_no);
        etAccCode = (TextInputEditText) findViewById(R.id.txt_edit_acc_code);
        etAccDesc = (TextInputEditText) findViewById(R.id.txt_edit_acc_desc);
        etAccCode.setText(ACC_CODE);
        etAccDesc.setText(ACC_DESC);
        textInputVinNo = (TextInputLayout) findViewById(R.id.txt_input_vin_no);
        textInputAccCode = (TextInputLayout) findViewById(R.id.txt_input_acc_code);
        textInputAccDesc = (TextInputLayout) findViewById(R.id.txt_input_acc_desc);
        txtData = (TextView) findViewById(R.id.txt_data);
        txtActivityTitle = (TextView) findViewById(R.id.txt_activity_name);
        btnTest = (Button) findViewById(R.id.btn_test);
        txtActivityTitle.setText(activityNameStr);
        activityModelString = PreferenceStorage.getInstance(VinInputActivity.this).getActivityModel();
//        vinMaxLength = PreferenceStorage.getInstance(this).getVinMaxLenght();
      //    vinMaxLength = 17;
//        txtData.setText(Details.result);
//        gotoCaptureScreen("");
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(vinMandateStr.equals("0")){
            textInputVinNo.setVisibility(View.VISIBLE);
        }else {
            textInputVinNo.setVisibility(View.GONE);
        }
        etVinNumber.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.
                LengthFilter(vinMaxLength), vinFilter});
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
                if (s.toString().length() == vinMaxLength) {
                    String vinNumber = etVinNumber.getText().toString();
                    if (vinNumber.length() == vinMaxLength) {
                        Utility.hideKeyboard(activity);
                      //  gotoCaptureScreen(vinNumber);
                       // checkPermissionsAgin(vinNumber);
                    }
                }
            }
        });

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if( validateFileds()){
                   SubmitActivity();
               }
            }
        });


        /*etVinNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                }
            }
        });*/

        etAccCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (TextUtils.isEmpty(etVinNumber.getText().toString().trim()) && vinMandateStr.equals("0")) {
                        textInputVinNo.setErrorEnabled(true);
                        textInputVinNo.setError(getString(R.string.error_enter_vin));
                    } else {
                        textInputVinNo.setErrorEnabled(false);
                        textInputVinNo.setError(null);
                    }

                }
            }
        });

        etAccDesc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (TextUtils.isEmpty(etAccCode.getText().toString().trim())) {
                        textInputAccCode.setErrorEnabled(true);
                        textInputAccCode.setError(getString(R.string.error_acc_code));
                    } else {
                        textInputAccCode.setErrorEnabled(false);
                        textInputAccCode.setError(null);
                    }
                }
            }
        });

        etAccDesc.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideKeyboard(VinInputActivity.this);
                    if (validateFileds()) {
                        SubmitActivity();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public  void SubmitActivity(){
        String VIN_NO=etVinNumber.getText().toString();
        ACC_CODE=etAccCode.getText().toString();
        ACC_DESC=etAccDesc.getText().toString();
        primaryParamsList = new ArrayList<>();
        primaryParams = new ParentData.PrimaryParams("Acc.Cd.",ACC_CODE);
        primaryParamsList.add(primaryParams);
        primaryParams = new ParentData.PrimaryParams("Acc.Desc.",ACC_DESC);
        primaryParamsList.add(primaryParams);
        parentData = new ParentData(new ParentData.MetaData(activityIdStr,
                SharedPreferenceManager.getLanguagecode(VinInputActivity.this),SharedPreferenceManager.getConnString(VinInputActivity.this),
                SharedPreferenceManager.getSiteCode(VinInputActivity.this),"000","Demo",
                SharedPreferenceManager.getUserid(VinInputActivity.this),
                SharedPreferenceManager.getUsername(VinInputActivity.this),
                "VIN",VIN_NO,SharedPreferenceManager.getParentId(VinInputActivity.this),
                SharedPreferenceManager.getTenantId(VinInputActivity.this),
                /*SharedPreferenceManager.getSiteCode(VinInputActivity.this)*/"CAN"),
                new ParentData.PrimaryData(primaryParamsList));
        metaGson=new Gson();
        String metaString = metaGson.toJson(parentData);
        Bundle bundle = new Bundle();
        bundle.putString("input_data", metaString);
        Intent intent= new Intent(VinInputActivity.this, ModelCheckActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Details.result = "";
    }

    private boolean validateFileds() {
        boolean isValid = true;
        if (TextUtils.isEmpty(etVinNumber.getText().toString().trim()) && vinMandateStr.equals("0")) {
            textInputVinNo.setErrorEnabled(true);
            textInputVinNo.setError(getString(R.string.error_enter_vin));
            isValid = false;
        } else {
            textInputVinNo.setErrorEnabled(false);
            textInputVinNo.setError(null);
        }

        if (TextUtils.isEmpty(etAccCode.getText().toString().trim())) {
            textInputAccCode.setErrorEnabled(true);
            textInputAccCode.setError(getString(R.string.error_acc_code));
            isValid = false;
        } else {
            textInputAccCode.setErrorEnabled(false);
            textInputAccCode.setError(null);
        }

        if (TextUtils.isEmpty(etAccDesc.getText().toString().trim())) {
            textInputAccDesc.setErrorEnabled(true);
            textInputAccDesc.setError(getString(R.string.error_acc_desc));
            isValid = false;
        } else {
            textInputAccDesc.setErrorEnabled(false);
            textInputAccDesc.setError(null);
        }
        return isValid;
    }
    /*void gotoCaptureScreen(String vinNumber) {
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.key_vin_no), vinNumber);
        Intent intent = new Intent(VinInputActivity.this, ClassifierActivity.class);
        //Intent intent = new Intent(VinNumberActivity.this, DetectorActivity.class);
        intent.putExtras(bundle);
       // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
    }
}