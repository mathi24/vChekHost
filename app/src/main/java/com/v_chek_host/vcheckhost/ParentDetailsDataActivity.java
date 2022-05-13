package com.v_chek_host.vcheckhost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.v_chek_host.vcheckhost.V2.V2DashboardActivity;
import com.v_chek_host.vcheckhost.V2.V3MainActivity;
import com.v_chek_host.vcheckhost.V2.adapters.ActivityDataAdapter;
import com.v_chek_host.vcheckhost.V2.adapters.ModelResultAdapter;
import com.v_chek_host.vcheckhost.V3.V3HomeActivity;
import com.v_chek_host.vcheckhost.services.entity.HostResponseData;
import com.v_chek_host.vcheckhost.services.entity.ParentData;
import com.v_chek_host.vcheckhostsdk.model.entity.ParentMetaData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.lang.reflect.Type;

public class ParentDetailsDataActivity extends BaseActivity {

    private TextView txtParentData, txtActivityName, txtActivityResult;
    private Button btnOk;
    private Toolbar toolbar;
    private Gson gson;
    private HostResponseData hostResponseData;
    private RecyclerView recyclerView;
    private ModelResultAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_details_result);
        Bundle bundle = getIntent().getExtras();
        // txtParentData = findViewById(R.id.txt_parent_data);
        txtActivityName = findViewById(R.id.activity_name);
        txtActivityResult = findViewById(R.id.activity_result);
        recyclerView = findViewById(R.id.recycler_view_model_result);
        btnOk = findViewById(R.id.btn_ok);
        toolbar = findViewById(R.id.tool_bar);
        toolbar.setTitle("Details");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(ParentDetailsDataActivity.this, V3MainActivity.class);
                startActivity(intent);
                finishAffinity();*/
                onBackPressed();
            }
        });
        if (bundle != null) {
            String parentDataStr = bundle.getString("parentdata");
            //  txtParentData.setText(formatString(parentDataStr));
            gson = new Gson();
            Type parentType = new TypeToken<HostResponseData>() {
            }.getType();
            hostResponseData = gson.fromJson(parentDataStr, parentType);
            String ActivityStatus = hostResponseData.getActivityResult().getActivityResultMsg();
            if (hostResponseData.getActivityResult().getActivityResult().equalsIgnoreCase("Fail"))
                txtActivityResult.setTextColor(ContextCompat.getColor(this, R.color.red));
            else
                txtActivityResult.setTextColor(ContextCompat.getColor(this, R.color.green));
            txtActivityResult.setText(ActivityStatus);
            txtActivityName.setText(SharedPreferenceManager.getActivityName(this));
            mAdapter = new ModelResultAdapter(hostResponseData.getActivityResult().getModelList(), this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        }
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(ParentDetailsDataActivity.this, V3MainActivity.class);
                startActivity(intent);
                finishAffinity();*/
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        //Intent intent = new Intent(ParentDetailsDataActivity.this, V3MainActivity.class);
        Intent intent = new Intent(ParentDetailsDataActivity.this, V3HomeActivity.class);
        intent.putExtra("SDKCHECK","SDKCHECK");
        startActivity(intent);
        finishAffinity();
    }

    public static String formatString(String text) {
        StringBuilder json = new StringBuilder();
        String indentString = "";
        for (int i = 0; i < text.length(); i++) {
            char letter = text.charAt(i);
            switch (letter) {
                case '{':
                case '[':
                    json.append("\n" + indentString + letter + "\n");
                    indentString = indentString + "\t";
                    json.append(indentString);
                    break;
                case '}':
                case ']':
                    indentString = indentString.replaceFirst("\t", "");
                    json.append("\n" + indentString + letter);
                    break;
                case ',':
                    json.append(letter + "\n" + indentString);
                    break;
                default:
                    json.append(letter);
                    break;
            }
        }
        return json.toString();
    }
}