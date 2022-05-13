package com.v_chek_host.vcheckhostsdk.model.api;






import com.v_chek_host.vcheckhostsdk.Api;
import com.v_chek_host.vcheckhostsdk.BuildConfig;
import com.v_chek_host.vcheckhostsdk.LoggingInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public final class ApiClient {

    private static ApiClient mInstance;
    private Retrofit retrofit;
    public static final ApiService service = new Retrofit.Builder()
            .baseUrl(ApiDefine.BASE_URL)
            .client(new OkHttpClient.Builder()
                    .addInterceptor(createUserAgentInterceptor())
                    .addInterceptor(createHttpLoggingInterceptor())
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build())
           // .addConverterFactory(GsonConverterFactory.create(EntityUtils.gson))
           // .addConverterFactory(ScalarsConverterFactory.create())
           // .addConverterFactory(LoganSquareConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);

    private static Interceptor createUserAgentInterceptor() {
        return new Interceptor() {

            private static final String HEADER_USER_AGENT = "apikey";
            private static final String HEADER_USER_TYPE = "Content-Type";


            @Override
            public Response intercept(Chain chain) throws IOException {
                return chain.proceed(chain.request().newBuilder()
                        .header(HEADER_USER_AGENT, "c345d6a0-8a2d-11e6-aaad-7365a675f33")
                        .header(HEADER_USER_TYPE, "application/json")
                        .build());
            }
        };
    }
    private ApiClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(80, TimeUnit.SECONDS);
        httpClient.connectTimeout(80, TimeUnit.SECONDS);
        LoggingInterceptor logging = new LoggingInterceptor();
        //logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL) //StrConstant.BASE_URL
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized ApiClient getInstance() {
        if (mInstance == null) {
            mInstance = new ApiClient();
        }
        return mInstance;
    }
    private static Interceptor createHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return loggingInterceptor;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }

}
