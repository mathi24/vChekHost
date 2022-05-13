package com.v_chek_host.vcheckhost.V2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.v_chek_host.vcheckhost.BaseActivity;
import com.v_chek_host.vcheckhost.ParentDetailsDataActivity;
import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.Utility;
import com.v_chek_host.vcheckhost.V2.fragment.ActivityFragment;
import com.v_chek_host.vcheckhost.V2.fragment.ModelFragment;
import com.v_chek_host.vcheckhost.services.VideoUploadService;
import com.v_chek_host.vcheckhostsdk.Details;
import com.v_chek_host.vcheckhostsdk.JSONObjectCallBack;

import java.util.ArrayList;
import java.util.List;

//import com.v_chek_host.vcheckhostsdk.service.ModelDownloadService;

public class V3MainActivity extends BaseActivity {
    private Toolbar toolbar;
    private Gson modelGson;
    public ImageView ivBack, ivLogOut;
    public static String activityName;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    JSONObjectCallBack jsonObjectCallBack = new JSONObjectCallBack() {
        @Override
        public void onSuccess(String jsonObject) {
            if (jsonObject != null) {
                Intent intent = new Intent(V3MainActivity.this, ParentDetailsDataActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("parentdata", jsonObject);
                startActivity(intent);
                finishAffinity();
                // Log.v("kjhjhjk",jsonObject+"");
            } else {
                Toast.makeText(V3MainActivity.this, "No Data", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(String s) {

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v3_main);
        Details.getInstance().setCallback(jsonObjectCallBack);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Dashboard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        startService(new Intent(V3MainActivity.this, VideoUploadService.class));

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

       /* this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ActivityFragment(), "ACTIVITY");
        adapter.addFragment(new ModelFragment(), "MODEL");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Utility.clearData(V3MainActivity.this);
                stopService(new Intent(V3MainActivity.this,VideoUploadService.class));
                Intent intent = new Intent(V3MainActivity.this, V2LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent start = new Intent(Intent.ACTION_MAIN);
        start.addCategory(Intent.CATEGORY_HOME);
        start.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(start);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //  callAllModelData();
        //  linearLayout.setVisibility(View.GONE);
     /*   if (images.size() > 0) {
            for (int i = 0; i < images.size(); i++) {
                if (images.get(i).isSelected()) {
                    images.get(i).setSelected(false);
                    linearLayout.setVisibility(View.GONE);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }*/
    }
}