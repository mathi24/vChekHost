package com.v_chek_host.vcheckhostsdk.model.api;



import com.v_chek_host.vcheckhostsdk.model.entity.ApiRequest;
import com.v_chek_host.vcheckhostsdk.model.entity.Result;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ApiService {

    @POST("vTallyOnStop/")
    Call<Result.QRUploadResponse> setUpload(@Body ApiRequest.QrUpload jsonObjectTitle);



//
//    @GET("topics")
//    Call<Result.Data<List<Topic>>> getTopicList(
//            @Query("tab") TabType tab,
//            @Query("page") Integer page,
//            @Query("limit") Integer limit,
//            @Query("mdrender") Boolean mdrender
//    );
}


