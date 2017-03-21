package com.andy.commons.model.http;

import android.text.TextUtils;

import com.andy.commons.BaseApplication;
import com.andy.commons.utils.Constants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * okhttp头部验证
 */
public class AuthorizationInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        Request.Builder builder = oldRequest.newBuilder();
        String authorization = BaseApplication.getBaseApplication().getAuthorization();
        if (!TextUtils.isEmpty(authorization))
            builder.addHeader(Constants.Authorization, authorization);
        Request request = builder.build();
        return chain.proceed(request);
    }

}
