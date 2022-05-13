package com.v_chek_host.vcheckhost.services;



import com.v_chek_host.vcheckhost.BuildConfig;
import com.v_chek_host.vcheckhost.StrConstants;
import com.v_chek_host.vcheckhostsdk.utils.StrConstant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    private static ApiClient mInstance;
    private Retrofit retrofit;

    private ApiClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(80, TimeUnit.SECONDS);
        httpClient.connectTimeout(80, TimeUnit.SECONDS);
        httpClient.addInterceptor(createUserAgentInterceptor());
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        retrofit = new Retrofit.Builder()
                .baseUrl(StrConstants.BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Interceptor createUserAgentInterceptor() {
        return new Interceptor() {

            private static final String HEADER_USER_AGENT = "apikey";
            private static final String HEADER_USER_TYPE = "Content-Type";
            private static final String HEADER_USER_DEVICE = "device";
            private static final String HEADER_USER_VERSION = "version";


            @Override
            public Response intercept(Chain chain) throws IOException {
                return chain.proceed(chain.request().newBuilder()
                        .header(HEADER_USER_AGENT, StrConstant.API_KEY)
                        .header(HEADER_USER_TYPE, "application/json")
                        .header(HEADER_USER_DEVICE, StrConstant.APP_DEVICE)
                        .header(HEADER_USER_VERSION, BuildConfig.VERSION_CODE+"")
                        .build());
            }
        };
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

    public static synchronized ApiClient getInstance() {
        if (mInstance == null) {
            mInstance = new ApiClient();
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
