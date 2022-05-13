package com.v_chek_host.vcheckhost.V3.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.SharedPreferenceManager;
import com.v_chek_host.vcheckhost.StrConstants;
import com.v_chek_host.vcheckhost.V3.adapters.V3TrainHorizontalModelDataGridAdapter;
import com.v_chek_host.vcheckhost.V3.adapters.V3TrainHorizontalTitleAdapter;
import com.v_chek_host.vcheckhost.V3.adapters.V3TrainRecentHorizontalAdapter;
import com.v_chek_host.vcheckhost.V3.models.ModelRequestBody;
import com.v_chek_host.vcheckhost.V3.models.V3GetAllActivitiesDataModel;
import com.v_chek_host.vcheckhost.V3.models.V3TrainModelData;
import com.v_chek_host.vcheckhost.services.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class V3TrainFragment extends Fragment implements V3TrainHorizontalTitleAdapter.TitleActivityClick {
    public RecyclerView recyclerViewHorizontal;
    public RecyclerView recyclerViewActivitiesTitleHorizontal;
    public RecyclerView recyclerViewActivitiesDataGrid;
    public View view;
    public V3TrainRecentHorizontalAdapter v3TrainRecentHorizontalAdapter;
    public List<V3TrainModelData.RecentMlModel> recentMlModelList;

    public V3TrainHorizontalTitleAdapter v3TrainHorizontalTitleAdapter;
    public List<V3TrainModelData.MlModelTypeMasterDatum> mlModelTypeMasterDatumList;

    public V3TrainHorizontalModelDataGridAdapter v3TrainHorizontalModelDataGridAdapter;
    public List<V3TrainModelData.MlModelsDatum> mlModelsDatumList;

    public V3GetAllActivitiesDataModel v3GetAllActivitiesDataModel;

    private SearchView searchView;
    private TextView tvRecent, tvNoDataFound;
    boolean isRecentTextVisible = false;

    public V3TrainModelData v3TrainModelData;


    public V3TrainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_v3_train, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewHorizontal = view.findViewById(R.id.recycler_view_horizontal);
        recyclerViewActivitiesTitleHorizontal = view.findViewById(R.id.recycler_view_activites_title);
        recyclerViewActivitiesDataGrid = view.findViewById(R.id.recycler_view_activites_data);
        searchView = view.findViewById(R.id.search_view);
        tvRecent = view.findViewById(R.id.tvRecent);
        tvNoDataFound = view.findViewById(R.id.tvNoData);
        recentMlModelList = new ArrayList<>();
        mlModelTypeMasterDatumList = new ArrayList<>();
        mlModelsDatumList = new ArrayList<>();
        v3TrainRecentHorizontalAdapter = new V3TrainRecentHorizontalAdapter(recentMlModelList, getContext());
        v3TrainHorizontalTitleAdapter = new V3TrainHorizontalTitleAdapter(mlModelTypeMasterDatumList, getContext(), this);
        v3TrainHorizontalModelDataGridAdapter = new V3TrainHorizontalModelDataGridAdapter(mlModelsDatumList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHorizontal.setLayoutManager(mLayoutManager);
        recyclerViewHorizontal.setItemAnimator(new DefaultItemAnimator());
        recyclerViewHorizontal.setAdapter(v3TrainRecentHorizontalAdapter);

        RecyclerView.LayoutManager mLayoutManagerTitle = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewActivitiesTitleHorizontal.setLayoutManager(mLayoutManagerTitle);
        recyclerViewActivitiesTitleHorizontal.setItemAnimator(new DefaultItemAnimator());
        recyclerViewActivitiesTitleHorizontal.setAdapter(v3TrainHorizontalTitleAdapter);

        RecyclerView.LayoutManager mLayoutManagerGridData = new GridLayoutManager(getContext(),
                3);
        recyclerViewActivitiesDataGrid.setLayoutManager(mLayoutManagerGridData);
        recyclerViewActivitiesDataGrid.setItemAnimator(new DefaultItemAnimator());
        recyclerViewActivitiesDataGrid.setAdapter(v3TrainHorizontalModelDataGridAdapter);
        loadModelData();
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRecentTextVisible) {
                    isRecentTextVisible = true;
                    tvRecent.setVisibility(View.GONE);
                }
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if (isRecentTextVisible) {
                    isRecentTextVisible = false;
                    tvRecent.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                v3TrainRecentHorizontalAdapter.getFilter().filter(query);
                v3TrainHorizontalModelDataGridAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                v3TrainRecentHorizontalAdapter.getFilter().filter(newText);
                v3TrainHorizontalModelDataGridAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public void titleActivityClick(int position) {
        for (int i = 0; i < mlModelTypeMasterDatumList.size(); i++) {
            if (mlModelTypeMasterDatumList.get(i).isSelected() && v3TrainModelData.getMlModelsData().size() > 0) {
                mlModelsDatumList.clear();
                if (mlModelTypeMasterDatumList.get(i).getMlModelTypeId() == 0) {
                    mlModelsDatumList.addAll(v3TrainModelData.getMlModelsData());
                    v3TrainHorizontalModelDataGridAdapter.notifyDataSetChanged();
                } else {
                    for (int j = 0; j < v3TrainModelData.getMlModelsData().size(); j++) {
                        if (mlModelTypeMasterDatumList.get(i).getMlModelTypeId().equals(v3TrainModelData.getMlModelsData().get(j).getMlModelTypeId())) {
                            mlModelsDatumList.add(v3TrainModelData.getMlModelsData().get(j));
                        }
                    }
                               /* if (testHorizontalGridList.size() > 0) {
                                    tvNoDataFound.setVisibility(View.GONE);
                                } else {
                                    tvNoDataFound.setVisibility(View.VISIBLE);
                                }*/
                    v3TrainHorizontalModelDataGridAdapter.notifyDataSetChanged();
                }
                break;
            }
        }
    }

    public void loadModelData() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ModelRequestBody modelRequestBody = new ModelRequestBody(Integer.parseInt(SharedPreferenceManager.getTenantId(getContext())),
                Integer.parseInt(SharedPreferenceManager.getSiteId(getContext())),
                1);
        Call<V3TrainModelData> call = ApiClient.getInstance().getApi().getAllModelDataV2(StrConstants.API_VALUE, modelRequestBody);
        call.enqueue(new Callback<V3TrainModelData>() {
            @Override
            public void onResponse(Call<V3TrainModelData> call, Response<V3TrainModelData> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getResponseCode() == 200) {
                        v3TrainModelData = response.body();
                        recentMlModelList.addAll(v3TrainModelData.getRecentMlModels());
                        v3TrainRecentHorizontalAdapter.notifyDataSetChanged();
                        mlModelTypeMasterDatumList.addAll(v3TrainModelData.getMlModelTypeMasterData());
                        if (mlModelTypeMasterDatumList.size() > 0) {
                            mlModelTypeMasterDatumList.get(0).setSelected(true);
                        }
                        v3TrainHorizontalTitleAdapter.notifyDataSetChanged();
                        mlModelsDatumList.addAll(v3TrainModelData.getMlModelsData());
                        v3TrainHorizontalModelDataGridAdapter.notifyDataSetChanged();
                        SharedPreferenceManager.setLogModelId(getActivity(), 0);
                        SharedPreferenceManager.setActivityId(getActivity(), "0");
                    /*    for (int i = 0; i < mlModelTypeMasterDatumList.size(); i++) {
                            if (mlModelTypeMasterDatumList.get(i).isSelected() && v3TrainModelData.getMlModelsData().size() > 0) {
                                mlModelsDatumList.clear();
                                for (int j = 0; j < v3TrainModelData.getMlModelsData().size(); j++) {
                                    if (mlModelTypeMasterDatumList.get(i).getMlModelTypeId().equals(v3TrainModelData.getMlModelsData().get(j).getMlModelTypeId())) {
                                        mlModelsDatumList.add(v3TrainModelData.getMlModelsData().get(j));
                                    }
                                }
                               *//* if (testHorizontalGridList.size() > 0) {
                                    tvNoDataFound.setVisibility(View.GONE);
                                } else {
                                    tvNoDataFound.setVisibility(View.VISIBLE);
                                }*//*
                                v3TrainHorizontalModelDataGridAdapter.notifyDataSetChanged();
                                break;
                            }
                        }*/

                    }
                }
            }

            @Override
            public void onFailure(Call<V3TrainModelData> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}