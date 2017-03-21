package com.andy.flower.network.apis;

import com.andy.flower.Constants;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by andy.wang on 2016/8/24.
 */
public interface OperatorAPI {
    public static final String BASE_URL = "https://api.huaban.com/";

    @POST("pins/{pinId}/{operate}")
    Observable<Response<ResponseBody>> httpsLikeOperate(@Path("pinId") int pinsId, @Path("operate") String operate);

    @GET
    @Streaming
    Observable<Response<ResponseBody>> downloadImg(@Url String url);
}
