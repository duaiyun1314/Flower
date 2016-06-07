package com.andy.flower.network.apis;

import com.andy.flower.Constants;
import com.andy.flower.bean.POJO.AccessToken;
import com.andy.flower.bean.POJO.UserInfoBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by andy on 16-6-6.
 */
public interface LoginAPI {

    @FormUrlEncoded
    @POST("https://huaban.com/oauth/access_token/")
    Observable<AccessToken> getAccessToken(@Header(Constants.Authorization) String authorization, @Field("grant_type") String grant,
                                           @Field("username") String username, @Field("password") String password);

    //登录第二步 用上一步结果联网
    @GET("users/me")
    Observable<UserInfoBean> getUserInfo(@Header(Constants.Authorization) String authorization);
}
