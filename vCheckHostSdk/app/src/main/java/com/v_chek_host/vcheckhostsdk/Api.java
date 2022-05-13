package com.v_chek_host.vcheckhostsdk;



import com.v_chek_host.vcheckhostsdk.model.PushMonroneyDataModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Api {

    @POST("CheckMonroney")
    Call<MonroneyModel> insertCollectionImage(
            @Header("apikey") String apikey,
            @Body RequestBody photo
    );

    @FormUrlEncoded
    @POST("GlobalExceptionLog")
    Call<ExceptionLog> exceptionLogAPI(
            @Header("apikey") String apikey,
            @Field("app_id") int app_id,
            @Field("user_id") String user_id,
            @Field("device_details") String device_details,
            @Field("exception_details") String exception_details);


    @FormUrlEncoded
    @POST("PushMonroneyData")
    Call<PushMonroneyDataModel> pushMonroneyData(
            @Header("apikey") String apikey,
            @Field("VIN") String vin,
            @Field("employee_id") String employee_id,
            @Field("user_id") String user_id,
            @Field("parent_id") String parent_id,
            @Field("ml_model_id") String ml_model_id,
            @Field("ConnectionString") String ConnectionString,
            @Field("qc_result") String qcResult,
            @Field("Image") String image,
            @Field("qc_json_result") String qcJsonResult
    );

    @FormUrlEncoded
    @POST("PushMonroneyDataV3")
    Call<PushMonroneyDataModel> pushMonroneyDataV3(
            @Header("apikey") String apikey,
            @Field("VIN") String vin,
            @Field("employee_id") String employee_id,
            @Field("user_id") String user_id,
            @Field("parent_id") String parent_id,
            @Field("ml_model_id") String ml_model_id,
            @Field("ConnectionString") String ConnectionString,
            @Field("qc_result") String qcResult,
            @Field("Image") String image,
            @Field("qc_json_result") String qcJsonResult,
            @Field("SiteCode") String siteCode,
            @Field("SiteEmployeeCode") String siteEmployeeCode
    );

}
