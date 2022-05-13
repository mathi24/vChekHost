package com.v_chek_host.vcheckhost.services;



import com.v_chek_host.vcheckhost.StrConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClientOld {
    //local
    // private static final String BASE_URL = "http://192.168.2.12:3001/";/*http://192.168.42.5:3001/sp_get_tenants*/

    //developments
  //  private static final String BASE_URL = "http://134.122.113.252:3001/";
//    private static final String BASE_URL = "https://wwwebapi.azurewebsites.net";

    // Production URL
    //private static final String BASE_URL = "https://ww.vinspkt.com/";

    // Staging URL
    //private static final String BASE_URL = "https://stagingvinspkt.azurewebsites.net/";
    private static final String BASE_URL = "https://vchkapp.azurewebsites.net/VchkDataCollection/";
    private static ApiClientOld mInstance;
    private Retrofit retrofit;

    private ApiClientOld() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(80, TimeUnit.SECONDS);
        httpClient.connectTimeout(80, TimeUnit.SECONDS);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        retrofit = new Retrofit.Builder()
                .baseUrl(StrConstants.BASE_URL_MODEL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


//    public Api ApiClientException() {
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        httpClient.readTimeout(80, TimeUnit.SECONDS);
//        httpClient.connectTimeout(80, TimeUnit.SECONDS);
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        httpClient.addInterceptor(logging);
//        Retrofit retrofit= new Retrofit.Builder()
//                .baseUrl("https://ww.vinspkt.com")//https://wwwebapi.azurewebsites.net/")
//                .client(httpClient.build())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        return retrofit.create(Api.class);
//    }

    public static synchronized ApiClientOld getInstance() {
        if (mInstance == null) {
            mInstance = new ApiClientOld();
        }
        return mInstance;
    }


    public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(80, TimeUnit.SECONDS)
            .connectTimeout(80, TimeUnit.SECONDS)
            .build();

    public Api getApi() {
        return retrofit.create(Api.class);
    }
}
