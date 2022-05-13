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
import com.v_chek_host.vcheckhost.V2.V2ModelActivity;
import com.v_chek_host.vcheckhost.V2.V2ModelTrainingDataSetActivity;
import com.v_chek_host.vcheckhost.V2.V2ModelVideoTrainingDataSetActivity;
import com.v_chek_host.vcheckhost.V2.VinInputActivity;
import com.v_chek_host.vcheckhost.V2.adapters.ActivityDataAdapter;
import com.v_chek_host.vcheckhost.V2.adapters.TrainDataAdapter;
import com.v_chek_host.vcheckhost.V2.adapters.TrainModelAdapter;
import com.v_chek_host.vcheckhost.V2.models.ModelData;
import com.v_chek_host.vcheckhost.V2.models.TrainDataActivity;
import com.v_chek_host.vcheckhost.V2.models.TrainDataModel;
import com.v_chek_host.vcheckhost.services.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModelFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<ModelData.ApiModelData> mlModelData;
    private TrainModelAdapter mAdapter;
    private ProgressBar progressBar;
    public int lastItemPosition = -1;
    public LinearLayout linearLayout;
    public Button  btnTrainPhoto,btnTrainVideo;
    public static int modelId = -1;
    public static int modelImageWidth = 1280;
    public static int modelImageHeight = 960;
    public static int modelBottomCropHeight = 100;
    public static String modelOverlayUrl;
    private Gson modelGson;
    private String model,activityName;
    public ModelFragment() {
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
        View view = inflater.inflate(R.layout.fragment_model, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        linearLayout = (LinearLayout) view.findViewById(R.id.linear_layout);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        btnTrainPhoto = (Button) view.findViewById(R.id.btn_train_photo);
        btnTrainVideo = (Button) view.findViewById(R.id.btn_train_video);
        mlModelData = new ArrayList<>();
        mAdapter = new TrainModelAdapter(mlModelData, getContext());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (lastItemPosition != -1 && lastItemPosition != position) {
                    mlModelData.get(lastItemPosition).setSelected(false);
                }
                linearLayout.setVisibility(View.VISIBLE);
                lastItemPosition = position;
                mlModelData.get(position).setSelected(true);
                mAdapter.notifyDataSetChanged();
                modelId = mlModelData.get(lastItemPosition).getMlModelId();
                modelImageWidth = mlModelData.get(lastItemPosition).getTrainingImageWidth();
                modelImageHeight = mlModelData.get(lastItemPosition).getTrainingImageHeight();
                modelBottomCropHeight = mlModelData.get(lastItemPosition).getBottomCropHeight();
                modelOverlayUrl = mlModelData.get(lastItemPosition).getTrain_overlay_url();
                SharedPreferenceManager.setModelId(getContext(),modelId);
                SharedPreferenceManager.setModelImageWidth(getContext(),modelImageWidth);
                SharedPreferenceManager.setModelImageHeight(getContext(),modelImageHeight);
                SharedPreferenceManager.setModelBottomCropHeight(getContext(),modelBottomCropHeight);
                SharedPreferenceManager.setOverlayUrl(getContext(),modelOverlayUrl);
                SharedPreferenceManager.setModelVideoHeight(getContext(),mlModelData.get(lastItemPosition).getTrainingVideoHeight());
                SharedPreferenceManager.setModelVideoWidth(getContext(),mlModelData.get(lastItemPosition).getTrainingVideoWidth());
                SharedPreferenceManager.setModelVideoTime(getContext(),mlModelData.get(lastItemPosition).getTrainingVideoTime());
               /* Bundle bundle = new Bundle();
                bundle.putString("ModelName", mlModelData.get(lastItemPosition).getModelName());
                Intent intent = new Intent(getContext(), V2ModelTrainingDataSetActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);*/
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        btnTrainPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastItemPosition != -1) {
                    Bundle bundle = new Bundle();
                    bundle.putString("ModelName", mlModelData.get(lastItemPosition).getModelName());
                    Intent intent = new Intent(getContext(), V2ModelTrainingDataSetActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        btnTrainVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastItemPosition != -1) {
                    Bundle bundle = new Bundle();
                    bundle.putString("ModelName", mlModelData.get(lastItemPosition).getModelName());
                    Intent intent = new Intent(getContext(), V2ModelVideoTrainingDataSetActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        /*btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastItemPosition != -1) {
                    Bundle bundle = new Bundle();
                    activityName = images.get(lastItemPosition).getActivityName();
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
        });*/
      //  callAllModelData();
        return view;
    }

    private void callModelData() {
        if (Utility.isConnectingToInternet(getActivity(), getContext())) {
          //  images.clear();
            progressBar.setVisibility(View.VISIBLE);
            Call<ModelData> call = ApiClient.getInstance().getApi().
                    getModelData(StrConstants.API_VALUE, SharedPreferenceManager.getTenantId(getContext()),
                            SharedPreferenceManager.getSiteId(getContext()));

            call.enqueue(new Callback<ModelData>() {
                @Override
                public void onResponse(Call<ModelData> call, Response<ModelData> response) {
                    progressBar.setVisibility(View.GONE);
                    int statusCode = response.code();
                    if (statusCode == 200) {
                        mlModelData = response.body().getMessage().getModelData();
                        if(mlModelData.size()>0){
                            recyclerView.setVisibility(View.VISIBLE);
                            mAdapter = new TrainModelAdapter(mlModelData, getContext());
                            recyclerView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                        }
                        /*images.clear();
                        if (response.body().getMessage() != null) {
                            List<TrainDataModel.Message> trainDataModels = response.body().getMessage();
                            for (int i = 0; i < trainDataModels.size(); i++) {
                                images.add(trainDataModels.get(i));
                            }
                            mAdapter.notifyDataSetChanged();
                        }*/
                    } else {
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ModelData> call, Throwable t) {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        linearLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        callModelData();
    }
}
