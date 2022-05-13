package com.v_chek_host.vcheckhost.V3.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.v_chek_host.vcheckhost.R;
import com.v_chek_host.vcheckhost.SharedPreferenceManager;
import com.v_chek_host.vcheckhost.StrConstants;
import com.v_chek_host.vcheckhost.Utility;
import com.v_chek_host.vcheckhost.V3.V3LoginActivity;
import com.v_chek_host.vcheckhost.V3.adapters.MasterActivityFilterPopUpAdapter;
import com.v_chek_host.vcheckhost.V3.adapters.MasterActivityPopUpAdapter;
import com.v_chek_host.vcheckhost.V3.adapters.MobileDashBoardAllFilterDataAdapter;
import com.v_chek_host.vcheckhost.V3.adapters.MobileDashBoardWeeklyFilterDataAdapter;
import com.v_chek_host.vcheckhost.V3.adapters.MobileDashBoardYearlyFilterDataAdapter;
import com.v_chek_host.vcheckhost.V3.adapters.SwitchDataAdapter;
import com.v_chek_host.vcheckhost.V3.models.V3MasterActivityNameModel;
import com.v_chek_host.vcheckhost.V3.models.V3MobileDashBoardDataResponce;
import com.v_chek_host.vcheckhost.V3.models.V3MobileDashboardActivityMasterResponce;
import com.v_chek_host.vcheckhost.V3.models.V3SwitchDataModelRespone;
import com.v_chek_host.vcheckhost.V3.models.V3UpdateSiteAccessModelResponce;
import com.v_chek_host.vcheckhost.services.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class V3DashBoardFragment extends Fragment implements MasterActivityPopUpAdapter.ChangeActivityFilter, MasterActivityFilterPopUpAdapter.ScheduleFilter,
        MobileDashBoardWeeklyFilterDataAdapter.WeeklyFilterData, MobileDashBoardYearlyFilterDataAdapter.YearlyFilterData, MobileDashBoardAllFilterDataAdapter.AllWiseFilterData,
        SwitchDataAdapter.SwitchDataFilter {

    ProgressDialog progressDialog;

    V3MobileDashboardActivityMasterResponce v3MobileDashboardActivityMasterResponce;
    V3MobileDashBoardDataResponce v3MobileDashBoardDataResponce;
    TextView tvActivityName, tvTotalCount, tvPassCount, tvFailCount, tvUserName;
    ImageView ivFilter, ivUserProfile;
    View view;
    List<V3MasterActivityNameModel> masterActivityNameModelList;
    RecyclerView recyclerView;
    public static int filterID = 1;
    public MobileDashBoardWeeklyFilterDataAdapter mobileDashBoardWeeklyFilterDataAdapter;
    public MobileDashBoardYearlyFilterDataAdapter mobileDashBoardYearlyFilterDataAdapter;
    public MobileDashBoardAllFilterDataAdapter mobileDashBoardAllFilterDataAdapter;
    public List<V3MobileDashBoardDataResponce.WeeklyWiseData> weeklyWiseDataList;
    public List<V3MobileDashBoardDataResponce.YearWiseData> yearWiseDataList;
    public List<V3MobileDashBoardDataResponce.AllWiseData> allWiseDataList;
    AlertDialog activityFilterDialog;
    AlertDialog scheduleFilterDialog;
    BottomSheetDialog switchDataDialog;
    TextView tvNoDataFound;
    CardView cardView;

    public List<V3SwitchDataModelRespone.Message> switchList = new ArrayList<>();

    public V3DashBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_v3_dash_board, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
        tvActivityName = view.findViewById(R.id.tvActivityName);
        tvUserName = view.findViewById(R.id.tvUsername);
        tvTotalCount = view.findViewById(R.id.tvTotalCount);
        tvPassCount = view.findViewById(R.id.tvPassCount);
        tvFailCount = view.findViewById(R.id.tvFailCount);
        recyclerView = view.findViewById(R.id.recycler_view);
        ivFilter = view.findViewById(R.id.ivFilter);
        ivUserProfile = view.findViewById(R.id.ivUserProfile);
        tvNoDataFound = view.findViewById(R.id.tvNoDataFound);
        cardView = view.findViewById(R.id.card_view);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        masterActivityNameModelList = new ArrayList<>();
        addFilterData();
        weeklyWiseDataList = new ArrayList<>();
        yearWiseDataList = new ArrayList<>();
        allWiseDataList = new ArrayList<>();
        mobileDashBoardWeeklyFilterDataAdapter = new MobileDashBoardWeeklyFilterDataAdapter(weeklyWiseDataList, getContext(), this);
        mobileDashBoardYearlyFilterDataAdapter = new MobileDashBoardYearlyFilterDataAdapter(yearWiseDataList, getContext(), this);
        mobileDashBoardAllFilterDataAdapter = new MobileDashBoardAllFilterDataAdapter(allWiseDataList, getContext(), this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        callMobileDashBoardMasterActivityDataAPI();
        /*if (filterID == 1) {
            recyclerView.setAdapter(mobileDashBoardWeeklyFilterDataAdapter);
        } else if (filterID == 2) {
            recyclerView.setAdapter(mobileDashBoardYearlyFilterDataAdapter);
        } else {
            recyclerView.setAdapter(mobileDashBoardAllFilterDataAdapter);
        }*/
        tvActivityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v3MobileDashboardActivityMasterResponce != null &&
                        v3MobileDashboardActivityMasterResponce.getMessage() != null &&
                        v3MobileDashboardActivityMasterResponce.getMessage().size() > 0) {

                    openActivityDialog();
                }
            }
        });
        ivFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (masterActivityNameModelList != null && masterActivityNameModelList.size() > 0) {
                    openFilterDialog();
                }
            }
        });
        callSwitchDataAPI();
        ivUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchList != null && switchList.size() > 0) {
                    openBottomDialogSheetForSiteSelection();
                }
            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterID == 1 && v3MobileDashBoardDataResponce != null) {
                    if (v3MobileDashBoardDataResponce.getMessage() != null &&
                            v3MobileDashBoardDataResponce.getMessage().getWeekWiseDashboard() != null
                            && v3MobileDashBoardDataResponce.getMessage().getWeekWiseDashboard().getData() != null
                            && v3MobileDashBoardDataResponce.getMessage().getWeekWiseDashboard().getData().size() > 0) {
                        tvTotalCount.setText(v3MobileDashBoardDataResponce.getMessage().getWeekWiseDashboard().getTotalInspectionsCount() + "");
                        tvPassCount.setText(v3MobileDashBoardDataResponce.getMessage().getWeekWiseDashboard().getPassInspectionsCount() + "");
                        tvFailCount.setText(v3MobileDashBoardDataResponce.getMessage().getWeekWiseDashboard().getFailInspectionsCount() + "");
                        if (weeklyWiseDataList.size() > 0) {
                            for (int i = 0; i < weeklyWiseDataList.size(); i++) {
                                weeklyWiseDataList.get(i).setSelected(false);
                            }
                            mobileDashBoardWeeklyFilterDataAdapter.notifyDataSetChanged();
                        }
                    }
                } else if (filterID == 2 && v3MobileDashBoardDataResponce != null) {
                    if (v3MobileDashBoardDataResponce.getMessage() != null &&
                            v3MobileDashBoardDataResponce.getMessage().getYearWiseDashboard() != null
                            && v3MobileDashBoardDataResponce.getMessage().getYearWiseDashboard().getData() != null
                            && v3MobileDashBoardDataResponce.getMessage().getYearWiseDashboard().getData().size() > 0) {
                        tvTotalCount.setText(v3MobileDashBoardDataResponce.getMessage().getYearWiseDashboard().getTotalInspectionsCount() + "");
                        tvPassCount.setText(v3MobileDashBoardDataResponce.getMessage().getYearWiseDashboard().getPassInspectionsCount() + "");
                        tvFailCount.setText(v3MobileDashBoardDataResponce.getMessage().getYearWiseDashboard().getFailInspectionsCount() + "");
                        if (yearWiseDataList.size() > 0) {
                            for (int i = 0; i < yearWiseDataList.size(); i++) {
                                yearWiseDataList.get(i).setSelected(false);
                            }
                            mobileDashBoardYearlyFilterDataAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    if (v3MobileDashBoardDataResponce != null) {
                        if (v3MobileDashBoardDataResponce.getMessage() != null &&
                                v3MobileDashBoardDataResponce.getMessage().getAllWiseDashboard() != null
                                && v3MobileDashBoardDataResponce.getMessage().getAllWiseDashboard().getData() != null
                                && v3MobileDashBoardDataResponce.getMessage().getAllWiseDashboard().getData().size() > 0) {
                            tvTotalCount.setText(v3MobileDashBoardDataResponce.getMessage().getAllWiseDashboard().getTotalInspectionsCount() + "");
                            tvPassCount.setText(v3MobileDashBoardDataResponce.getMessage().getAllWiseDashboard().getPassInspectionsCount() + "");
                            tvFailCount.setText(v3MobileDashBoardDataResponce.getMessage().getAllWiseDashboard().getFailInspectionsCount() + "");
                            if (allWiseDataList.size() > 0) {
                                for (int i = 0; i < allWiseDataList.size(); i++) {
                                    allWiseDataList.get(i).setSelected(false);
                                }
                                mobileDashBoardAllFilterDataAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }
        });
    }

    public void callMobileDashBoardMasterActivityDataAPI() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        Call<V3MobileDashboardActivityMasterResponce> call = ApiClient.getInstance().getApi().getMobileDashboardActivityMasterData(StrConstants.API_VALUE,
                SharedPreferenceManager.getSiteId(getContext()));
        call.enqueue(new Callback<V3MobileDashboardActivityMasterResponce>() {
            @Override
            public void onResponse(Call<V3MobileDashboardActivityMasterResponce> call, Response<V3MobileDashboardActivityMasterResponce> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    if (response.body() != null && Integer.parseInt(response.body().getResponseCode()) == 200) {
                        v3MobileDashboardActivityMasterResponce = response.body();
                        if (v3MobileDashboardActivityMasterResponce.getMessage() != null &&
                                v3MobileDashboardActivityMasterResponce.getMessage().size() > 0) {
                            v3MobileDashboardActivityMasterResponce.getMessage().get(0).setChecked(true);
                            callMobileDashBoardDataAPI(filterID + "", String.valueOf(v3MobileDashboardActivityMasterResponce.getMessage().get(0).getActivityId()));
                        } else {
                            tvTotalCount.setText(0 + "");
                            tvPassCount.setText(0 + "");
                            tvFailCount.setText(0 + "");
                            tvActivityName.setText("");
                            progressDialog.dismiss();
                        }
                    } else {
                        progressDialog.dismiss();
                    }
                } else {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<V3MobileDashboardActivityMasterResponce> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    public void callMobileDashBoardDataAPI(String filterID, String activityID) {
        Call<V3MobileDashBoardDataResponce> call = ApiClient.getInstance().getApi().mobileDashboardData(
                StrConstants.API_VALUE,
                SharedPreferenceManager.getSiteId(getContext()),
                SharedPreferenceManager.getUserid(getContext()),
                activityID,
                filterID
        );
        call.enqueue(new Callback<V3MobileDashBoardDataResponce>() {
            @Override
            public void onResponse(Call<V3MobileDashBoardDataResponce> call, Response<V3MobileDashBoardDataResponce> response) {
                progressDialog.dismiss();
                int statusCode = response.code();
                if (statusCode == 200) {
                    assert response.body() != null;
                    if (Integer.parseInt(response.body().getResponseCode()) == 200) {
                        v3MobileDashBoardDataResponce = response.body();
                        tvActivityName.setText(v3MobileDashBoardDataResponce.getMessage().getActivityName());
                        tvUserName.setText("Hi," + v3MobileDashBoardDataResponce.getMessage().getUserData().getFirstName() + " " + v3MobileDashBoardDataResponce.getMessage().getUserData().getLastName());
                        Glide.with(getContext()).load(v3MobileDashBoardDataResponce.getMessage().getUserData().getProfileImageBlob()).placeholder(R.drawable.ic_baseline_account_circle_24).into(ivUserProfile);
                        if (Integer.parseInt(filterID) == 1) {
                            if (v3MobileDashBoardDataResponce.getMessage() != null &&
                                    v3MobileDashBoardDataResponce.getMessage().getWeekWiseDashboard() != null
                                    && v3MobileDashBoardDataResponce.getMessage().getWeekWiseDashboard().getData() != null
                                    && v3MobileDashBoardDataResponce.getMessage().getWeekWiseDashboard().getData().size() > 0) {
                                tvTotalCount.setText(v3MobileDashBoardDataResponce.getMessage().getWeekWiseDashboard().getTotalInspectionsCount() + "");
                                tvPassCount.setText(v3MobileDashBoardDataResponce.getMessage().getWeekWiseDashboard().getPassInspectionsCount() + "");
                                tvFailCount.setText(v3MobileDashBoardDataResponce.getMessage().getWeekWiseDashboard().getFailInspectionsCount() + "");
                                weeklyWiseDataList.clear();
                                if (v3MobileDashBoardDataResponce.getMessage().getWeekWiseDashboard().getTotalInspectionsCount() > 0) {
                                    weeklyWiseDataList.addAll(v3MobileDashBoardDataResponce.getMessage().getWeekWiseDashboard().getData());
                                }
                                if (weeklyWiseDataList.size() > 0) {
                                    tvNoDataFound.setVisibility(View.GONE);
                                } else {
                                    tvNoDataFound.setVisibility(View.VISIBLE);
                                }
                                recyclerView.setAdapter(mobileDashBoardWeeklyFilterDataAdapter);
                            }
                            /*for (int i = 0; i < v3MobileDashBoardDataResponce.getMessage().getWeekWiseDashboard().getData().size(); i++) {

                            }*/
                        } else if (Integer.parseInt(filterID) == 2) {
                            if (v3MobileDashBoardDataResponce.getMessage() != null &&
                                    v3MobileDashBoardDataResponce.getMessage().getYearWiseDashboard() != null
                                    && v3MobileDashBoardDataResponce.getMessage().getYearWiseDashboard().getData() != null
                                    && v3MobileDashBoardDataResponce.getMessage().getYearWiseDashboard().getData().size() > 0) {
                                tvTotalCount.setText(v3MobileDashBoardDataResponce.getMessage().getYearWiseDashboard().getTotalInspectionsCount() + "");
                                tvPassCount.setText(v3MobileDashBoardDataResponce.getMessage().getYearWiseDashboard().getPassInspectionsCount() + "");
                                tvFailCount.setText(v3MobileDashBoardDataResponce.getMessage().getYearWiseDashboard().getFailInspectionsCount() + "");
                                yearWiseDataList.clear();
                                if (v3MobileDashBoardDataResponce.getMessage().getYearWiseDashboard().getTotalInspectionsCount() > 0) {
                                    yearWiseDataList.addAll(v3MobileDashBoardDataResponce.getMessage().getYearWiseDashboard().getData());
                                }
                                if (yearWiseDataList.size() > 0) {
                                    tvNoDataFound.setVisibility(View.GONE);
                                } else {
                                    tvNoDataFound.setVisibility(View.VISIBLE);
                                }
                                recyclerView.setAdapter(mobileDashBoardYearlyFilterDataAdapter);
                            }
                        } else {
                            if (v3MobileDashBoardDataResponce.getMessage() != null &&
                                    v3MobileDashBoardDataResponce.getMessage().getAllWiseDashboard() != null
                                    && v3MobileDashBoardDataResponce.getMessage().getAllWiseDashboard().getData() != null
                                    && v3MobileDashBoardDataResponce.getMessage().getAllWiseDashboard().getData().size() > 0) {
                                tvTotalCount.setText(v3MobileDashBoardDataResponce.getMessage().getAllWiseDashboard().getTotalInspectionsCount() + "");
                                tvPassCount.setText(v3MobileDashBoardDataResponce.getMessage().getAllWiseDashboard().getPassInspectionsCount() + "");
                                tvFailCount.setText(v3MobileDashBoardDataResponce.getMessage().getAllWiseDashboard().getFailInspectionsCount() + "");
                                allWiseDataList.clear();
                                if (v3MobileDashBoardDataResponce.getMessage().getAllWiseDashboard().getTotalInspectionsCount() > 0) {
                                    allWiseDataList.addAll(v3MobileDashBoardDataResponce.getMessage().getAllWiseDashboard().getData());
                                }
                                if (allWiseDataList.size() > 0) {
                                    tvNoDataFound.setVisibility(View.GONE);
                                } else {
                                    tvNoDataFound.setVisibility(View.VISIBLE);
                                }
                                recyclerView.setAdapter(mobileDashBoardAllFilterDataAdapter);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<V3MobileDashBoardDataResponce> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    public void openActivityDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = view.findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.master_activityname_popup, viewGroup, false);
        RecyclerView recyclerView = dialogView.findViewById(R.id.recycler_view);
        List<V3MobileDashboardActivityMasterResponce.Message> masterActivityNameModelList = new ArrayList<>();
        if (v3MobileDashboardActivityMasterResponce != null && v3MobileDashboardActivityMasterResponce.getMessage().size() > 0) {
            masterActivityNameModelList.addAll(v3MobileDashboardActivityMasterResponce.getMessage());
        }
        MasterActivityPopUpAdapter masterActivityPopUpAdapter = new MasterActivityPopUpAdapter(masterActivityNameModelList, getContext(), this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(masterActivityPopUpAdapter);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        activityFilterDialog = builder.create();
        activityFilterDialog.show();
    }

    public void openFilterDialog() {
//before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = view.findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.master_activityname_popup, viewGroup, false);
        RecyclerView recyclerView = dialogView.findViewById(R.id.recycler_view);
        TextView tvTitleName = dialogView.findViewById(R.id.tvTitleName);
        tvTitleName.setText("View Activity Stats for");
        MasterActivityFilterPopUpAdapter masterActivityPopUpAdapter = new MasterActivityFilterPopUpAdapter(masterActivityNameModelList, getContext(), this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(masterActivityPopUpAdapter);
        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        scheduleFilterDialog = builder.create();
        scheduleFilterDialog.show();
    }

    public void addFilterData() {
        V3MasterActivityNameModel v3MasterActivityNameModel = new V3MasterActivityNameModel();
        v3MasterActivityNameModel.setActivityName("This Week");
        v3MasterActivityNameModel.setActivityID(1);
        v3MasterActivityNameModel.setChecked(true);
        masterActivityNameModelList.add(v3MasterActivityNameModel);
        v3MasterActivityNameModel = new V3MasterActivityNameModel();
        v3MasterActivityNameModel.setActivityName("This Year");
        v3MasterActivityNameModel.setActivityID(2);
        masterActivityNameModelList.add(v3MasterActivityNameModel);
        v3MasterActivityNameModel = new V3MasterActivityNameModel();
        v3MasterActivityNameModel.setActivityName("All");
        v3MasterActivityNameModel.setActivityID(3);
        masterActivityNameModelList.add(v3MasterActivityNameModel);
    }

    @Override
    public void changeFilterActivity(int postion) {
        if (activityFilterDialog != null && activityFilterDialog.isShowing()) {
            activityFilterDialog.dismiss();
        }
        progressDialog.show();
        callMobileDashBoardDataAPI(filterID + "", String.valueOf(v3MobileDashboardActivityMasterResponce.getMessage().get(postion).getActivityId()));
    }

    @Override
    public void scheduleFilter(int position) {
        if (scheduleFilterDialog != null && scheduleFilterDialog.isShowing()) {
            scheduleFilterDialog.dismiss();
        }
        String filterPosition = "";
        if (v3MobileDashboardActivityMasterResponce != null && v3MobileDashboardActivityMasterResponce.getMessage() != null) {
            for (int i = 0; i < v3MobileDashboardActivityMasterResponce.getMessage().size(); i++) {
                if (v3MobileDashboardActivityMasterResponce.getMessage().get(i).isChecked()) {
                    filterPosition = v3MobileDashboardActivityMasterResponce.getMessage().get(i).getActivityId() + "";
                    break;
                }
            }
        }
        if (!TextUtils.isEmpty(filterPosition) && Utility.isConnectingToInternet(getActivity(),getContext())) {
            progressDialog.show();
                callMobileDashBoardDataAPI(filterID + "", filterPosition);
        }
    }

    public void callSwitchDataAPI() {
        Call<V3SwitchDataModelRespone> call = ApiClient.getInstance().getApi().getSwitchSitesData(StrConstants.API_VALUE, SharedPreferenceManager.getUserid(getContext()));
        call.enqueue(new Callback<V3SwitchDataModelRespone>() {
            @Override
            public void onResponse(Call<V3SwitchDataModelRespone> call, Response<V3SwitchDataModelRespone> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    assert response.body() != null;
                    if (Integer.parseInt(response.body().getResponseCode()) == 200) {
                        if (response.body().getMessage() != null && response.body().getMessage().size() > 0) {
                            switchList.addAll(response.body().getMessage());
                            if (switchList.size() > 0) {
                                for (int i = 0; i < switchList.size(); i++) {
                                    if (switchList.get(i).getSiteId() == Integer.parseInt(SharedPreferenceManager.getSiteId(getContext()))) {
                                        switchList.get(i).setSelected(true);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<V3SwitchDataModelRespone> call, Throwable t) {

            }
        });
    }

 /*   public void openSwitchDataPopUp() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = view.findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.master_activityname_popup, viewGroup, false);
        RecyclerView recyclerView = dialogView.findViewById(R.id.recycler_view);
        TextView tvTitleName = dialogView.findViewById(R.id.tvTitleName);
        tvTitleName.setText("View Activity Stats for");
        SwitchDataAdapter switchDataAdapter = new SwitchDataAdapter(switchList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(switchDataAdapter);
        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        switchDataDialog = builder.create();
        switchDataDialog.show();
    }*/

    public void openBottomDialogSheetForSiteSelection() {
        View view = ((Activity) getActivity()).getLayoutInflater().inflate(R.layout.master_activityname_popup, null);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        TextView tvTitleName = view.findViewById(R.id.tvTitleName);
        Button btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setVisibility(View.VISIBLE);
        tvTitleName.setText(" ");
        SwitchDataAdapter switchDataAdapter = new SwitchDataAdapter(switchList, getContext(), this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(switchDataAdapter);
        switchDataDialog = new BottomSheetDialog(getContext(), R.style.DialogStyle);
        switchDataDialog.setContentView(view);
        switchDataDialog.show();
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = mPrefs.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(getActivity(), V3LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

    }

    @Override
    public void allWiseFilterData(int position) {
        V3MobileDashBoardDataResponce.AllWiseData allWiseData = allWiseDataList.get(position);
        tvTotalCount.setText(allWiseData.getTotalInspectionsCount() + "");
        tvPassCount.setText(allWiseData.getPassInspectionsCount() + "");
        tvFailCount.setText(allWiseData.getFailInspectionsCount() + "");
    }

    @Override
    public void weeklyFilterData(int position) {
        V3MobileDashBoardDataResponce.WeeklyWiseData weeklyWiseData = weeklyWiseDataList.get(position);
        tvTotalCount.setText(weeklyWiseData.getTotalInspectionsCount() + "");
        tvPassCount.setText(weeklyWiseData.getPassInspectionsCount() + "");
        tvFailCount.setText(weeklyWiseData.getFailInspectionsCount() + "");
    }

    @Override
    public void yearlyFilterData(int position) {
        V3MobileDashBoardDataResponce.YearWiseData yearWiseData = yearWiseDataList.get(position);
        tvTotalCount.setText(yearWiseData.getTotalInspectionsCount() + "");
        tvPassCount.setText(yearWiseData.getPassInspectionsCount() + "");
        tvFailCount.setText(yearWiseData.getFailInspectionsCount() + "");
    }

    @Override
    public void switchDataFilter(int position) {
        if (switchDataDialog != null && switchDataDialog.isShowing()) {
            switchDataDialog.dismiss();
        }
        callUpdateSwitchDataAPI(position);
    }

    private void callUpdateSwitchDataAPI(int position) {
        V3SwitchDataModelRespone.Message v3SwitchDataModelRespone = switchList.get(position);
        progressDialog.show();
        Call<V3UpdateSiteAccessModelResponce> call = ApiClient.getInstance().getApi().updateLastSiteAccess(StrConstants.API_VALUE,
                v3SwitchDataModelRespone.getSiteId() + "", SharedPreferenceManager.getUserid(getContext()));

        call.enqueue(new Callback<V3UpdateSiteAccessModelResponce>() {
            @Override
            public void onResponse(Call<V3UpdateSiteAccessModelResponce> call, Response<V3UpdateSiteAccessModelResponce> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    if (Integer.parseInt(response.body().getResponseCode()) == 200) {
                        if (response.body().getMessage().getStatusCode() == 200) {
                            SharedPreferenceManager.setSiteId(getContext(), v3SwitchDataModelRespone.getSiteId() + "");
                            callMobileDashBoardMasterActivityDataAPI();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<V3UpdateSiteAccessModelResponce> call, Throwable t) {

            }
        });

    }
}