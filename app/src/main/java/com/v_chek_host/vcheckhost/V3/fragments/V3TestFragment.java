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
import com.v_chek_host.vcheckhost.V3.adapters.V3TestHorizontalActivitiesDataGridAdapter;
import com.v_chek_host.vcheckhost.V3.adapters.V3TestHorizontalTitleAdapter;
import com.v_chek_host.vcheckhost.V3.adapters.V3TestRecentHorizotalAdapter;
import com.v_chek_host.vcheckhost.V3.models.V3GetAllActivitiesDataModel;
import com.v_chek_host.vcheckhost.services.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class V3TestFragment extends Fragment implements V3TestHorizontalTitleAdapter.TitleActivityClick {
    public RecyclerView recyclerViewHorizontal;
    public RecyclerView recyclerViewActivitiesTitleHorizontal;
    public RecyclerView recyclerViewActivitiesDataGrid;
    public View view;
    public V3TestRecentHorizotalAdapter v3TestRecentHorizotalAdapter;
    public List<V3GetAllActivitiesDataModel.RecentActivity> testHorizontalList;

    public V3TestHorizontalTitleAdapter v3TestHorizontalTitleAdapter;
    public List<V3GetAllActivitiesDataModel.ActivityTypeMasterDatum> testHorizontalTitleList;

    public V3TestHorizontalActivitiesDataGridAdapter v3TestHorizontalActivitiesDataGridAdapter;
    public List<V3GetAllActivitiesDataModel.AllActivity> testHorizontalGridList;

    public V3GetAllActivitiesDataModel v3GetAllActivitiesDataModel;

    private SearchView searchView;
    private TextView tvRecent, tvNoDataFound;
    boolean isRecentTextVisible = false;

    public V3TestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_v3_test, container, false);
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
        testHorizontalList = new ArrayList<>();
        testHorizontalTitleList = new ArrayList<>();
        testHorizontalGridList = new ArrayList<>();
        v3TestRecentHorizotalAdapter = new V3TestRecentHorizotalAdapter(testHorizontalList, getContext());
        v3TestHorizontalTitleAdapter = new V3TestHorizontalTitleAdapter(testHorizontalTitleList, getContext(), this);
        v3TestHorizontalActivitiesDataGridAdapter = new V3TestHorizontalActivitiesDataGridAdapter(testHorizontalGridList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHorizontal.setLayoutManager(mLayoutManager);
        recyclerViewHorizontal.setItemAnimator(new DefaultItemAnimator());
        recyclerViewHorizontal.setAdapter(v3TestRecentHorizotalAdapter);

        RecyclerView.LayoutManager mLayoutManagerTitle = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewActivitiesTitleHorizontal.setLayoutManager(mLayoutManagerTitle);
        recyclerViewActivitiesTitleHorizontal.setItemAnimator(new DefaultItemAnimator());
        recyclerViewActivitiesTitleHorizontal.setAdapter(v3TestHorizontalTitleAdapter);

        RecyclerView.LayoutManager mLayoutManagerGridData = new GridLayoutManager(getContext(),
                3);
        recyclerViewActivitiesDataGrid.setLayoutManager(mLayoutManagerGridData);
        recyclerViewActivitiesDataGrid.setItemAnimator(new DefaultItemAnimator());
        recyclerViewActivitiesDataGrid.setAdapter(v3TestHorizontalActivitiesDataGridAdapter);

        loadHorizontalData();

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
                v3TestRecentHorizotalAdapter.getFilter().filter(query);
                v3TestHorizontalActivitiesDataGridAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                v3TestRecentHorizotalAdapter.getFilter().filter(newText);
                v3TestHorizontalActivitiesDataGridAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    public void loadHorizontalData() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<V3GetAllActivitiesDataModel> call = ApiClient.getInstance().getApi().getAllActivityV2(StrConstants.API_VALUE, SharedPreferenceManager.getTenantId(getContext()), SharedPreferenceManager.getSiteId(getContext()));
        call.enqueue(new Callback<V3GetAllActivitiesDataModel>() {
            @Override
            public void onResponse(Call<V3GetAllActivitiesDataModel> call, Response<V3GetAllActivitiesDataModel> response) {

                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getResponseCode() == 200) {
                        v3GetAllActivitiesDataModel = response.body();
                        testHorizontalList.addAll(v3GetAllActivitiesDataModel.getRecentActivity());
                        v3TestRecentHorizotalAdapter.notifyDataSetChanged();
                        testHorizontalTitleList.addAll(v3GetAllActivitiesDataModel.getActivityTypeMasterData());
                        if (testHorizontalTitleList.size() > 0) {
                            testHorizontalTitleList.get(0).setSelected(true);
                        }
                        v3TestHorizontalTitleAdapter.notifyDataSetChanged();
                        testHorizontalGridList.addAll(v3GetAllActivitiesDataModel.getAllActivity());
                        v3TestHorizontalActivitiesDataGridAdapter.notifyDataSetChanged();
                        SharedPreferenceManager.setActivityId(getActivity(), "0");
                        SharedPreferenceManager.setLogModelId(getActivity(), 0);
                        /*for (int i = 0; i < testHorizontalTitleList.size(); i++) {
                            if (testHorizontalTitleList.get(i).isSelected() && v3GetAllActivitiesDataModel.getAllActivity().size() > 0) {
                                testHorizontalGridList.clear();
                                for (int j = 0; j < v3GetAllActivitiesDataModel.getAllActivity().size(); j++) {
                                    if (testHorizontalTitleList.get(i).getActivityTypeId().equals(v3GetAllActivitiesDataModel.getAllActivity().get(j).getActivityTypeId())) {
                                        testHorizontalGridList.add(v3GetAllActivitiesDataModel.getAllActivity().get(j));
                                    }
                                }
                               *//* if (testHorizontalGridList.size() > 0) {
                                    tvNoDataFound.setVisibility(View.GONE);
                                } else {
                                    tvNoDataFound.setVisibility(View.VISIBLE);
                                }*//*
                                v3TestHorizontalActivitiesDataGridAdapter.notifyDataSetChanged();
                                break;
                            }
                        }*/

                    }
                }
            }

            @Override
            public void onFailure(Call<V3GetAllActivitiesDataModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }


    @Override
    public void titleActivityClick(int position) {
        for (int i = 0; i < testHorizontalTitleList.size(); i++) {
            if (testHorizontalTitleList.get(i).isSelected() && v3GetAllActivitiesDataModel.getAllActivity().size() > 0) {
                testHorizontalGridList.clear();
                if (testHorizontalTitleList.get(i).getActivityTypeId() == 0) {
                    testHorizontalGridList.addAll(v3GetAllActivitiesDataModel.getAllActivity());
                    v3TestHorizontalActivitiesDataGridAdapter.notifyDataSetChanged();
                } else {
                    for (int j = 0; j < v3GetAllActivitiesDataModel.getAllActivity().size(); j++) {
                        if (testHorizontalTitleList.get(i).getActivityTypeId().equals(v3GetAllActivitiesDataModel.getAllActivity().get(j).getActivityTypeId())) {
                            testHorizontalGridList.add(v3GetAllActivitiesDataModel.getAllActivity().get(j));
                        }
                    }
                /*if (testHorizontalGridList.size() > 0) {
                    tvNoDataFound.setVisibility(View.GONE);
                } else {
                    tvNoDataFound.setVisibility(View.VISIBLE);
                }*/
                    v3TestHorizontalActivitiesDataGridAdapter.notifyDataSetChanged();
                }
                break;
            }
        }
    }
}