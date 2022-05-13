package com.v_chek_host.vcheckhost.V3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.customview.widget.Openable;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.v_chek_host.vcheckhost.BaseActivity;
import com.v_chek_host.vcheckhost.ParentDetailsDataActivity;
import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.SharedPreferenceManager;
import com.v_chek_host.vcheckhostsdk.Details;
import com.v_chek_host.vcheckhostsdk.JSONObjectCallBack;

public class V3HomeActivity extends BaseActivity {
    private BottomNavigationView bottomNavigationView;
    public String addVin = "";
    private static NavController navController;
    public String sdkCheck = "";
    private NavHostFragment navHostFragment;
    private JSONObjectCallBack jsonObjectCallBack = new JSONObjectCallBack() {
        @Override
        public void onSuccess(String jsonObject) {
            if (jsonObject != null) {
                Intent intent = new Intent(V3HomeActivity.this, ParentDetailsDataActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("parentdata", jsonObject);
                startActivity(intent);
                finish();
                // Log.v("kjhjhjk",jsonObject+"");
            } else {
                Toast.makeText(V3HomeActivity.this, "No Data", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(String s) {

        }
    };

    @Override
    protected void onDestroy() {
        jsonObjectCallBack = null;
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v3_home);
        Details.getInstance().setCallback(jsonObjectCallBack);
        /*Intent intent = new Intent(HomeActivity.this, EditProfileActivity.class);
        startActivity(intent);*/
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            sdkCheck = bundle.getString("SDKCHECK");
        }
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.bottom_nav_host_fragment);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView,
                navController);
        if (sdkCheck.equalsIgnoreCase("SDKCHECK")) {
            navController.navigate(R.id.v3TestFragment);
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() != bottomNavigationView.getSelectedItemId()) {
                    NavigationUI.onNavDestinationSelected(item, navController);
                    return true;
                }
                return false;
            }
        });
        SharedPreferenceManager.setLogModelId(this, 0);
        SharedPreferenceManager.setActivityId(this, "0");
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, (Openable) null);
    }
}