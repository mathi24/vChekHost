package com.v_chek_host.vcheckhost;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private Context ctx;
    private ImageButton imBtnVisible;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ctx = LoginActivity.this;

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        imBtnVisible = findViewById(R.id.visible_icon);

        //  checkUserExist();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValid()) {
                    callLoginApi();
                }

            }
        });
        imBtnVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPasswordVisible) {
                    imBtnVisible.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_visibility_24));
                    etPassword.setTransformationMethod(null);
                    isPasswordVisible = true;
                } else {
                    imBtnVisible.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_visibility_off_24));
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
        if (SharedPreferenceManager.getIslogged(ctx)) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

    private void callLoginApi() {
        String userName = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (userName.equals("Admin") && password.equals("Admin@123")) {
            SharedPreferenceManager.setIslogged(ctx, true);
            SharedPreferenceManager.setUsername(ctx, userName);
            SharedPreferenceManager.setPassword(ctx, password);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        Intent start = new Intent(Intent.ACTION_MAIN);
        start.addCategory(Intent.CATEGORY_HOME);
        start.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(start);
        finishAffinity();
    }
}