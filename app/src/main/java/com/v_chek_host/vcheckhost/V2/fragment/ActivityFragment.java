package com.v_chek_host.vcheckhost.V2.fragment;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.SharedPreferenceManager;
import com.v_chek_host.vcheckhost.StrConstants;
import com.v_chek_host.vcheckhost.Utility;
import com.v_chek_host.vcheckhost.V2.RecyclerTouchListener;
import com.v_chek_host.vcheckhost.V2.V2DashboardActivity;
import com.v_chek_host.vcheckhost.V2.V2ModelActivity;
import com.v_chek_host.vcheckhost.V2.VinInputActivity;
import com.v_chek_host.vcheckhost.V2.adapters.ActivityDataAdapter;
import com.v_chek_host.vcheckhost.V2.models.TrainDataActivity;
import com.v_chek_host.vcheckhost.services.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityFragment extends Fragment {

    private RecyclerView recyclerView;
    private ActivityDataAdapter mAdapter;
    private ProgressBar progressBar;
    public int lastItemPosition = -1;
    public LinearLayout linearLayout;
    public Button btnTest, btnTrain;
    private List<TrainDataActivity.Message> images;
    public static String activityName;
    private Gson modelGson;
    public ActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activity, container, false);
        images = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        linearLayout = (LinearLayout) view.findViewById(R.id.linear_layout);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        btnTest = (Button) view.findViewById(R.id.btn_test);
        btnTrain = (Button) view.findViewById(R.id.btn_train);
        mAdapter = new ActivityDataAdapter(images, getContext());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (lastItemPosition != -1 && lastItemPosition != position) {
                    images.get(lastItemPosition).setSelected(false);
                }
                linearLayout.setVisibility(View.VISIBLE);
                lastItemPosition = position;
                images.get(position).setSelected(true);
                mAdapter.notifyDataSetChanged();
                if (images.get(position).getIsLive() == 1) {
                    btnTest.setEnabled(true);
                } else {
                    btnTest.setEnabled(false);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        btnTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastItemPosition != -1) {
                   /* modelId = images.get(lastItemPosition).getActivityId();
                    Bundle bundle = new Bundle();
                    bundle.putString("ModelName", images.get(lastItemPosition).getActivityName());
                    Intent intent = new Intent(V2DashboardActivity.this, V2ModelTrainingDataSetActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);*/
                    modelGson=new Gson();
                    String modelString = modelGson.toJson(images.get(lastItemPosition).getModelData());
                    Bundle bundle = new Bundle();
                    bundle.putString("Model", modelString);
                    bundle.putString("ModelName",images.get(lastItemPosition).getActivityName());
                    Intent intent = new Intent(getActivity(), V2ModelActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastItemPosition != -1) {
                    Bundle bundle = new Bundle();
                    activityName = images.get(lastItemPosition).getActivityName();
                    SharedPreferenceManager.setActivityName(getContext(),activityName);
                    bundle.putString("activity_name", images.get(lastItemPosition).getActivityName());
                    bundle.putString("activity_id", Integer.toString(images.get(lastItemPosition).getActivityId()));
                    bundle.putString("vin_mandate", Integer.toString(images.get(lastItemPosition).getVinMandate()));
                    bundle.putString("vin_max_lenght", Integer.toString(images.get(lastItemPosition).getVinLength()));
                    bundle.putString("acc_code", images.get(lastItemPosition).getModelParams().get(0).getValue());
                    bundle.putString("acc_desc", images.get(lastItemPosition).getModelParams().get(1).getValue());
                    Intent intent= new Intent(getContext(), VinInputActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
      //  callAllModelData();
        return view;
    }

    private void callAllModelData() {
        if (Utility.isConnectingToInternet(getActivity(), getContext())) {
            images.clear();
            progressBar.setVisibility(View.VISIBLE);
            Call<TrainDataActivity> call = ApiClient.getInstance().getApi().
                    allActivityData(StrConstants.API_VALUE, SharedPreferenceManager.getTenantId(getContext()),
                            SharedPreferenceManager.getSiteId(getContext()));
        /*progressDialog = new ProgressDialog(V2DashboardActivity.this);
        progressDialog.setMessage("Loading.");
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }*/
            call.enqueue(new Callback<TrainDataActivity>() {
                @Override
                public void onResponse(Call<TrainDataActivity> call, Response<TrainDataActivity> response) {
                    progressBar.setVisibility(View.GONE);
                    int statusCode = response.code();
                    if (statusCode == 200) {
                        images.clear();
                        if (response.body().getMessage() != null) {
                            List<TrainDataActivity.Message> trainDataModels = response.body().getMessage();
                            for (int i = 0; i < trainDataModels.size(); i++) {
                                images.add(trainDataModels.get(i));
                            }
                          //  images = trainDataModels;
                            recyclerView.setVisibility(View.VISIBLE);
                            mAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TrainDataActivity> call, Throwable t) {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
        callAllModelData();
    }
}
