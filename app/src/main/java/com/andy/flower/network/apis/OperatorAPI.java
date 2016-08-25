package com.andy.flower.network.apis;

import com.andy.flower.Constants;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by andy.wang on 2016/8/24.
 */
public interface OperatorAPI {
    @POST("pins/{pinId}/{operate}")
    Observable<Response<ResponseBody>> httpsLikeOperate(@Header(
            Constants.Authorization) String authorization, @Path("pinId") int pinsId, @Path("operate") String operate);

    @GET
    @Streaming
    Observable<Response<ResponseBody>> downloadImg(@Url String url);
}
