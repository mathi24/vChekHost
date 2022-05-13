package com.v_chek_host.vcheckhost.services;


import com.v_chek_host.vcheckhost.AllImagesDataModel;
import com.v_chek_host.vcheckhost.CollectionImagesBody;
import com.v_chek_host.vcheckhost.UploadingPhotoImageModel;
import com.v_chek_host.vcheckhost.V2.models.LoginModel;
import com.v_chek_host.vcheckhost.V2.models.ModelData;
import com.v_chek_host.vcheckhost.V2.models.ModelTrainingSetDataModel;
import com.v_chek_host.vcheckhost.V2.models.TrainDataActivity;
import com.v_chek_host.vcheckhost.V2.models.TrainDataModel;
import com.v_chek_host.vcheckhost.V3.models.ModelRequestBody;
import com.v_chek_host.vcheckhost.V3.models.MultipleImageModelResponce;
import com.v_chek_host.vcheckhost.V3.models.V3GetAllActivitiesDataModel;
import com.v_chek_host.vcheckhost.V3.models.V3LoginModel;
import com.v_chek_host.vcheckhost.V3.models.V3MlModelDatasetResponce;
import com.v_chek_host.vcheckhost.V3.models.V3MobileDashBoardDataResponce;
import com.v_chek_host.vcheckhost.V3.models.V3MobileDashboardActivityMasterResponce;
import com.v_chek_host.vcheckhost.V3.models.V3SingleTrainingModelResponce;
import com.v_chek_host.vcheckhost.V3.models.V3SwitchDataModelRespone;
import com.v_chek_host.vcheckhost.V3.models.V3TrainModelData;
import com.v_chek_host.vcheckhost.V3.models.V3UpdateSiteAccessModelResponce;
import com.v_chek_host.vcheckhostsdk.model.entity.InspktResult;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {


    @POST("FetchCollectionImage")
    Call<AllImagesDataModel> loadAllImages(
            @Header("apikey") String apikey,
            @Header("Content-Type") String contentType,
            @Body CollectionImagesBody imagesBody
    );

    @POST("InsertCollectionImage")
    Call<UploadingPhotoImageModel> insertCollectionImage(
            @Header("apikey") String apikey,
            @Body RequestBody photo
    );

    @FormUrlEncoded
    @POST("sp_get_all_activity")
    Call<TrainDataActivity> allActivityData(
            @Header("apikey") String apikey,
            @Field("v_tenant_id") String tenantId,
            @Field("v_site_id") String siteId
    );

    @FormUrlEncoded
    @POST("sp_get_all_model_ready_for_training")
    Call<ModelData> getModelData(
            @Header("apikey") String apikey,
            @Field("v_tenant_id") String tenantId,
            @Field("v_site_id") String siteId

    );


    @FormUrlEncoded
    @POST("sp_get_ml_models_data_all_for_mobile")
    Call<TrainDataModel> allModelData(
            @Header("apikey") String apikey,
            @Field("v_site_id") String siteId
    );

    @FormUrlEncoded
    @POST("sp_get_ml_model_image_by_model_id_for_mobile")
    Call<ModelTrainingSetDataModel> allModelTrainedDataSet(
            @Header("apikey") String apikey,
            @Field("v_ml_model_id") String mlModelId
    );

    @FormUrlEncoded
    @POST("sp_get_ml_model_dataset_by_model_id_for_mobile")
    Call<ModelTrainingSetDataModel> modelTrainedDataSet(
            @Header("apikey") String apikey,
            @Field("v_ml_model_id") String mlModelId,
            @Field("v_dataset_type") String type
    );

    @FormUrlEncoded
    @POST("sp_insert_ml_model_image")
    Call<ResponseBody> insertMlModelImage(
            @Header("apikey") String apikey,
            @Field("v_ml_model_id") String mlModelId,
            @Field("v_ml_model_image") String mlModelImage,
            @Field("v_image_result") Integer vImageResult
    );


    @Headers({"Content-Type: video/mp4"})
    @POST("sp_insert_ml_model_video")
    Call<ResponseBody> insertMlModelVideo(@Header("apikey") String apikey,
                                          @Header("v_ml_model_id") int mlModelId,
                                          @Header("v_video_result") int mlUploadType,
                                          @Body RequestBody sendVideo);


    @FormUrlEncoded
    @POST("sp_login_check")
    Call<LoginModel> loginCheck(
            @Header("apikey") String apikey,
            @Header("appVer") String appVer,
            @Field("v_email") String mEmail,
            @Field("v_password") String mPassword
    );

    @FormUrlEncoded
    @POST("sp_login_check_v2")
    Call<V3LoginModel> loginCheckV2(
            @Header("apikey") String apikey,
            @Field("v_email") String mEmail,
            @Field("v_password") String mPassword
    );

    @FormUrlEncoded
    @POST("sp_get_all_activity_v2")
    Call<V3GetAllActivitiesDataModel> getAllActivityV2(
            @Header("apikey") String apikey,
            @Field("v_tenant_id") String vTenantId,
            @Field("v_site_id") String vSiteId
    );

    @POST("sp_get_all_model_ready_for_training_v2")
    Call<V3TrainModelData> getAllModelDataV2(
            @Header("apikey") String apikey,
            @Body ModelRequestBody modelRequestBody
    );

    @FormUrlEncoded
    @POST("insert_ml_model_image_multiple")
    Call<MultipleImageModelResponce> getMultipleImageUpload(
            @Header("apikey") String apikey,
            @Field("v_ml_model_id") String vMlmodelId,
            @Field("v_ml_model_image") String vMlmodelImage,
            @Field("v_site_user_id") String v_site_user_id
    );

    @FormUrlEncoded
    @POST("delete_training_model_image")
    Call<MultipleImageModelResponce> deleteTrainingImages(
            @Header("apikey") String apikey,
            @Field("ml_model_image_ids") String imageIDS
    );

    @FormUrlEncoded
    @POST("update_training_model_image_status")
    Call<MultipleImageModelResponce> updateTrainedModel(
            @Header("apikey") String apikey,
            @Field("ml_model_image_ids") String imageIDS,
            @Field("image_result") String imageResult
    );


    @FormUrlEncoded
    @POST("sp_insert_ml_model_image_v2")
    Call<V3SingleTrainingModelResponce> singleInsertTrainingModel(
            @Header("apikey") String apikey,
            @Field("v_ml_model_id") String imageIDS,
            @Field("v_site_user_id") String v_site_user_id,
            @Field("v_ml_model_image") String v_ml_model_image,
            @Field("v_image_result") String v_image_result
    );

    @FormUrlEncoded
    @POST("get_mobile_dashboard_activity_master_data")
    Call<V3MobileDashboardActivityMasterResponce> getMobileDashboardActivityMasterData(
            @Header("apikey") String apikey,
            @Field("v_site_id") String v_site_id
    );

    @FormUrlEncoded
    @POST("mobile_dashboard_data")
    Call<V3MobileDashBoardDataResponce> mobileDashboardData(
            @Header("apikey") String apikey,
            @Field("v_site_id") String v_site_id,
            @Field("v_site_user_id") String v_site_user_id,
            @Field("v_activity_id") String v_activity_id,
            @Field("v_filter_id") String v_filter_id
    );

    @FormUrlEncoded
    @POST("get_switch_sites_data")
    Call<V3SwitchDataModelRespone> getSwitchSitesData(
            @Header("apikey") String apikey,
            @Field("site_user_id") String site_user_id
    );

    @FormUrlEncoded
    @POST("update_last_site_access")
    Call<V3UpdateSiteAccessModelResponce> updateLastSiteAccess(
            @Header("apikey") String apikey,
            @Field("site_id") String site_id,
            @Field("site_user_id") String site_user_id
    );

    @FormUrlEncoded
    @POST("sp_get_ml_model_dataset_by_model_id_for_mobile_v2")
    Call<V3MlModelDatasetResponce> getMlModelDatasetByModelIdForMobileV2(
            @Header("apikey") String apikey,
            @Field("v_ml_model_id") int v_ml_model_id
    );


}
