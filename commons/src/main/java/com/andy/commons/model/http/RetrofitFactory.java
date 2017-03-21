package com.andy.commons.model.http;

import com.andy.commons.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wanglu on 2017/3/21.
 */

public class RetrofitFactory {

    private final Gson gson;

    private static final int TIMEOUT_READ = 20;
    private static final int TIMEOUT_CONNECTION = 10;
    private static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private AuthorizationInterceptor authorizationInterceptor = new AuthorizationInterceptor();


    public RetrofitFactory() {
        gson = new GsonBuilder()
                .registerTypeAdapterFactory(new CustomTypeApdaterFactory())
                .create();
    }

    private static class SingleOnHolder {
        private static final RetrofitFactory INSTANCE = new RetrofitFactory();
    }

    public static RetrofitFactory getInstance() {
        return SingleOnHolder.INSTANCE;
    }

    public <S> S createService(Class<S> serviceClass) {
        String baseUrl = "";
        try {
            Field field = serviceClass.getField("BASE_URL");
            try {
                baseUrl = (String) field.get(field);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(serviceClass);
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(authorizationInterceptor)
                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        //if (BuildConfig.ISDEBUG)
            builder.addInterceptor(interceptor);
        OkHttpClient okHttpClient = builder.build();
        return okHttpClient;
    }
}
