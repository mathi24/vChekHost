package com.v_chek_host.vcheckhost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.v_chek_host.vcheckhost.services.ApiClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataCollectionActivity extends BaseActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private List<AllImagesDataModel.Message> images;
    private GallaryAdapter mAdapter;
    private ProgressBar progressBar;
    private Context ctx;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private int recordCount = 50;
    private int startingIndex = 0;
    private boolean isCallingApi = false;
    boolean isToolBarVisible = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_collection);
        ctx = DataCollectionActivity.this;
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        toolbar = findViewById(R.id.tool_bar);
        toolbar.setTitle("Images");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        images = new ArrayList<>();
        mAdapter = new GallaryAdapter(images, ctx);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ctx, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataCollectionActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        recyclerView.addOnItemTouchListener(new GallaryAdapter.RecyclerTouchListener(ctx, recyclerView, new GallaryAdapter.ClickListerner() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", (Serializable) images);
                bundle.putInt("position", position);
                //  MyApplication.countPageAdapter=images.size();
                //  System.out.println("AGS-count: "+MyApplication.countPageAdapter);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    Log.v("visibleItemCount", visibleItemCount + "");
                    Log.v("totalItemCount", totalItemCount + "");
                    // pastVisiblesItems = mLayoutManager.fin;
                    Log.v("pastVisiblesItems", linearLayoutManager.findLastVisibleItemPosition() + "");


                    if (linearLayoutManager.findLastVisibleItemPosition() == totalItemCount - 1 && !isCallingApi) {
                        Log.v("pastVisiblesItems", linearLayoutManager.findLastVisibleItemPosition() + "");
                        Log.v("startingIndex", startingIndex + "");
                        isCallingApi = true;
                        startingIndex = startingIndex + recordCount;
                        if (Utility.isConnectingToInternet(DataCollectionActivity.this, ctx)) {
                            refreshApi("4", String.valueOf(recordCount), String.valueOf(startingIndex));
                        }
                    }

                /*    if (totalItemCount == Integer.parseInt(recordCount)) {
                        refreshStartIndex = totalItemCount;
                        startingIndex = String.valueOf(refreshStartIndex);
                        // first time calling
                        refreshApi("1", recordCount, startingIndex);
                        Log.v("freshApi", "FirstTime");
                    } else {
                        refreshStartIndex = refreshStartIndex + Integer.parseInt(recordCount);
                        startingIndex = String.valueOf(refreshStartIndex);
                        refreshApi("1", recordCount, startingIndex);
                        Log.v("freshApi", "SecondTime");
                    }*/

                }
            }
        });
        boolean isToolBarVisible = false;
        startingIndex = 0;
        recordCount = 50;
        images.clear();
        mAdapter.notifyDataSetChanged();
        if (Utility.isConnectingToInternet(DataCollectionActivity.this, ctx)) {
            loadAllImages();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DataCollectionActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void loadAllImages() {
   /*     images.add("https://api.androidhive.info/images/glide/small/deadpool.jpg");
        images.add("https://api.androidhive.info/images/glide/small/bvs.jpg");
        images.add("https://api.androidhive.info/images/glide/small/cacw.jpg");
        images.add("https://api.androidhive.info/images/glide/small/bourne.jpg");
        images.add("https://api.androidhive.info/images/glide/small/squad.jpg");
        images.add("https://api.androidhive.info/images/glide/small/doctor.jpg");
        images.add("https://api.androidhive.info/images/glide/small/dory.jpg");
        images.add("https://api.androidhive.info/images/glide/small/hunger.jpg");
        images.add("https://api.androidhive.info/images/glide/small/hours.jpg");
        images.add("https://api.androidhive.info/images/glide/small/ipman3.jpg");
        images.add("https://api.androidhive.info/images/glide/small/book.jpg");
        images.add("https://api.androidhive.info/images/glide/small/xmen.jpg");*/

        progressBar.setVisibility(View.VISIBLE);
        CollectionImagesBody collectionImagesBody = new CollectionImagesBody("4", String.valueOf(recordCount), String.valueOf(startingIndex));
        Call<AllImagesDataModel> call = ApiClient.getInstance().getApi().loadAllImages(StrConstants.APIvalue, "application/json", collectionImagesBody);
        call.enqueue(new Callback<AllImagesDataModel>() {
            @Override
            public void onResponse(Call<AllImagesDataModel> call, Response<AllImagesDataModel> response) {
                int statusCode = response.code();
                progressBar.setVisibility(View.GONE);
                if (statusCode == StrConstants.STATUS_CODE_200) {
                    if (response.body().getResponseCode().equals(String.valueOf(StrConstants.SERVER_CODE_200))) {
                        AllImagesDataModel allImagesDataModel = response.body();
                        if (!isToolBarVisible) {
                            toolbar.setTitle("Images" + "(" + allImagesDataModel.getGetImageCollectionCount() + ")");
                        }
                        if (allImagesDataModel.getMessage() != null) {
                            for (int i = 0; i < allImagesDataModel.getMessage().size(); i++) {
                                AllImagesDataModel.Message message = allImagesDataModel.getMessage().get(i);
                                images.add(message);
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(ctx, R.string.txt_something_went_wrong_please_try_again_later, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ctx, R.string.txt_something_went_wrong_please_try_again_later, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AllImagesDataModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                if (t.getMessage().equals("timeout")) {
                    Toast.makeText(ctx, R.string.txt_poor_internet_connection_please_check_your_internet_and_try_again, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ctx, R.string.txt_something_went_wrong_please_try_again_later, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void refreshApi(String userId, String reCount, String stIndex) {
        progressBar.setVisibility(View.VISIBLE);
        CollectionImagesBody collectionImagesBody = new CollectionImagesBody(userId, reCount, stIndex);
        Call<AllImagesDataModel> call = ApiClient.getInstance().getApi().loadAllImages(StrConstants.APIvalue, "application/json", collectionImagesBody);
        call.enqueue(new Callback<AllImagesDataModel>() {
            @Override
            public void onResponse(Call<AllImagesDataModel> call, Response<AllImagesDataModel> response) {
                isCallingApi = false;
                int statusCode = response.code();
                progressBar.setVisibility(View.GONE);
                if (statusCode == StrConstants.STATUS_CODE_200) {
                    if (response.body().getResponseCode().equals(String.valueOf(StrConstants.SERVER_CODE_200))) {
                        AllImagesDataModel allImagesDataModel = response.body();
                        if (allImagesDataModel.getMessage() != null) {
                            for (int i = 0; i < allImagesDataModel.getMessage().size(); i++) {
                                AllImagesDataModel.Message message = allImagesDataModel.getMessage().get(i);
                                images.add(message);
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(ctx, R.string.txt_something_went_wrong_please_try_again_later, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ctx, R.string.txt_something_went_wrong_please_try_again_later, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AllImagesDataModel> call, Throwable t) {
                isCallingApi = false;
                progressBar.setVisibility(View.GONE);
                if (t.getMessage().equals("timeout")) {
                    Toast.makeText(ctx, R.string.txt_poor_internet_connection_please_check_your_internet_and_try_again, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ctx, R.string.txt_something_went_wrong_please_try_again_later, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_data_collection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.camera_icon:
                Intent intent = new Intent(DataCollectionActivity.this, DataCollectionCamaraActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }


}