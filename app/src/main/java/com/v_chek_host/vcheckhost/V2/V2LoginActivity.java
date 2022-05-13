package com.v_chek_host.vcheckhost.V2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.v_chek_host.vcheckhost.BaseActivity;
import com.v_chek_host.vcheckhost.BuildConfig;
import com.v_chek_host.vcheckhost.DataCollectionActivity;
import com.v_chek_host.vcheckhost.LoginActivity;
import com.v_chek_host.vcheckhost.MainActivity;
import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.SharedPreferenceManager;
import com.v_chek_host.vcheckhost.StrConstants;
import com.v_chek_host.vcheckhost.Utility;
import com.v_chek_host.vcheckhost.V2.models.LoginModel;
import com.v_chek_host.vcheckhost.services.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.v_chek_host.vcheckhostsdk.utils.Utility.hideKeyboard;

public class V2LoginActivity extends BaseActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private Context ctx;
    private ImageButton imBtnVisible;
    private boolean isPasswordVisible = false;
    private ProgressBar progressBar;
    private TextView txtVersionName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v2_login);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ctx = V2LoginActivity.this;

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        imBtnVisible = findViewById(R.id.visible_icon);
        progressBar = findViewById(R.id.progress_bar);
        txtVersionName = findViewById(R.id.txt_version);
        txtVersionName.setText(BuildConfig.VERSION_NAME + "");

        //  checkUserExist();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    if (Utility.isConnectingToInternet(V2LoginActivity.this, ctx)) {
                        callLoginApi();
                    }
                }

            }
        });
        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideKeyboard(V2LoginActivity.this);
                    if (isValid()) {
                        if (Utility.isConnectingToInternet(V2LoginActivity.this, ctx)) {
                            callLoginApi();
                            return true;
                        }
                    }
                }
                return false;
            }
        });
        imBtnVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPasswordVisible) {
                    imBtnVisible.setImageDrawable(ctx.getResources().getDrawable(R.drawable.ic_baseline_visibility_24));
                    etPassword.setTransformationMethod(null);
                    isPasswordVisible = true;
                } else {
                    imBtnVisible.setImageDrawable(ctx.getResources().getDrawable(R.drawable.ic_baseline_visibility_off_24));
                    etPassword.setTransformationMethod(new PasswordTransformationMethod());
                    isPasswordVisible = false;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("asdashdasdasjd", "asdkasd");
        checkUserExist();
    }

    private void checkUserExist() {
        if (SharedPreferenceManager.getIslogged(ctx) && !TextUtils.isEmpty(SharedPreferenceManager.getEmployeeId(ctx))) {
           // Intent intent = new Intent(V2LoginActivity.this, V2DashboardActivity.class);
            Intent intent = new Intent(V2LoginActivity.this, V3MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

    private boolean isValid() {
        boolean isvalid = true;
        if (TextUtils.isEmpty(etUsername.getText().toString())) {
            etUsername.setError("Please enter username");
            etUsername.requestFocus();
            isvalid = false;
        } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError("Please enter password");
            etPassword.requestFocus();
            isvalid = false;
        }
        return isvalid;
    }

    private void callLoginApi() {
        progressBar.setVisibility(View.VISIBLE);
        String userName = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        Call<LoginModel> call = ApiClient.getInstance().getApi().loginCheck(StrConstants.API_VALUE, Integer.toString(BuildConfig.VERSION_CODE), userName, password);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                progressBar.setVisibility(View.GONE);
                int statusCode = response.code();
                if (statusCode == 200) {
                    if (response.body().getMessage().getStatusCode() == 200) {
                    LoginModel.Message message = response.body().getMessage();
                    SharedPreferenceManager.setIslogged(ctx, true);
                    SharedPreferenceManager.setConnString(ctx, response.body().getConnString());
                    SharedPreferenceManager.setLanguagecode(ctx, message.getLanguageCode());
                    SharedPreferenceManager.setUserid(ctx, Integer.toString(message.getId()));
                    SharedPreferenceManager.setSiteId(ctx, Integer.toString(message.getSiteId()));
                    SharedPreferenceManager.setTenantId(ctx, Integer.toString(message.getTenantId()));
                    SharedPreferenceManager.setParentId(ctx, Integer.toString(message.getParentId()));
                    SharedPreferenceManager.setSiteCode(ctx, message.getSiteCode());
                    SharedPreferenceManager.setUsername(ctx, message.getFirstName() + " " + message.getLastName());
                    SharedPreferenceManager.setEmployeeId(ctx, String.valueOf(message.getId()));
                    SharedPreferenceManager.setDisplayName(ctx, message.getDisplayName());
                    Intent intent = new Intent(V2LoginActivity.this, V3MainActivity.class);
                    startActivity(intent);
                    } else if (response.body().getMessage().getStatusCode() == 201) {
                        Toast.makeText(V2LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(V2LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }  else {
                    Toast.makeText(V2LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(V2LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent start = new Intent(Intent.ACTION_MAIN);
        start.addCategory(Intent.CATEGORY_HOME);
        start.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(start);
        finishAffinity();
    }
}