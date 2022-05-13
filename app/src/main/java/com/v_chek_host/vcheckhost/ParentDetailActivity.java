package com.v_chek_host.vcheckhost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.v_chek_host.vcheckhostsdk.Details;
import com.v_chek_host.vcheckhostsdk.JSONObjectCallBack;


import org.json.JSONObject;

import java.io.Serializable;

public class ParentDetailActivity extends BaseActivity {

    private Button btnSubmit;
    private EditText etParentID, etModelID, etConString, etUserId, etEmpId, etSiteCode;
    public ImageView ivBack;

    JSONObjectCallBack jsonObjectCallBack = new JSONObjectCallBack() {
        @Override
        public void onSuccess(String jsonObject) {
            if (jsonObject != null) {
                Log.v("adadasdasd", jsonObject);
                Intent intent = new Intent(ParentDetailActivity.this, ParentDetailsDataActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("parentdata", jsonObject);
                startActivity(intent);
                finishAffinity();
                // Log.v("kjhjhjk",jsonObject+"");
            } else {
                Toast.makeText(ParentDetailActivity.this, "No Data", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(String s) {

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_detail);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btnSubmit = findViewById(R.id.btn_submit);
        ivBack = findViewById(R.id.image_back);
        etParentID = findViewById(R.id.et_parent_id);
        etModelID = findViewById(R.id.et_model_id);
        etConString = findViewById(R.id.et_con_string);
        etEmpId = findViewById(R.id.et_emp_id);
        etSiteCode = findViewById(R.id.et_site_code);
        etUserId = findViewById(R.id.et_user_id);
        Details.getInstance().setCallback(jsonObjectCallBack);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    Bundle bundle = new Bundle();
                    bundle.putString(StrConstants.parentID, etParentID.getText().toString().trim());
                    bundle.putString(StrConstants.modelID, etModelID.getText().toString().trim());
                    bundle.putString(StrConstants.conString, etConString.getText().toString().trim());
                    bundle.putString(StrConstants.empId, etEmpId.getText().toString().trim());
                    bundle.putString(StrConstants.userId, etUserId.getText().toString().trim());
                    bundle.putString(StrConstants.siteCode, etSiteCode.getText().toString().trim());
                    bundle.putString("siteCode", etSiteCode.getText().toString().trim());
                    Intent intent = new Intent(ParentDetailActivity.this,
                            com.v_chek_host.vcheckhostsdk.VinNumberActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private boolean isValid() {
        boolean isvalid = true;

        if (TextUtils.isEmpty(etParentID.getText().toString())) {
            etParentID.setError("Please enter parent ID");
            etParentID.requestFocus();
            isvalid = false;
        } else if (TextUtils.isEmpty(etModelID.getText().toString())) {
            etModelID.setError("Please enter model ID");
            etModelID.requestFocus();
            isvalid = false;
        } else if (TextUtils.isEmpty(etConString.getText().toString())) {
            etConString.setError("Please enter connstring");
            etConString.requestFocus();
            isvalid = false;
        }

        return isvalid;
    }
}