package com.andy.flower.network.apis;

import com.andy.flower.Constants;
import com.andy.flower.bean.Comments;
import com.andy.flower.bean.PinDetailWrapper;
import com.andy.flower.bean.PinsListBean;
import com.andy.flower.bean.Weeklies;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by andy on 16-6-15.
 */
public interface PinsAPI {
    public static final String BASE_URL = "https://api.huaban.com/";

    //https//api.huaban.com/favorite/food_drink?limit=20
    @GET("favorite/{type}")
    Observable<PinsListBean> getPinsByCategory(@Path("type") String type, @Query("limit") int limit);

    //https//api.huaban.com/favorite/food_drink?max=5445324325&limit=20
    @GET("favorite/{type}")
    Observable<PinsListBean> getPinsByCategoryANDLimit(@Path("type") String type, @Query("max") int max, @Query("limit") int limit);

    //https://api.huaban.com/weekly?max=2016-07-21&limit=3
    @GET("weekly?")
    Observable<Weeklies> getWeekliesByLimit(@Query("max") String releaseNo, @Query("limit") int limit);

    @GET("pins/{pinId}")
    Observable<PinDetailWrapper> getPinDetailByPinId(@Path("pinId") int pinId);

    @POST("pins/{pinId}/comments")
    Observable<Response<ResponseBody>> postCommentForPin(@Path("pinId") int pinId, @Body Map<String, String> map);

    @GET("pins/{pinId}/comments")
    Observable<Comments> getPinComments(@Path("pinId") int pinId);

    @GET("users/{userId}/pins")
    Observable<PinsListBean> getPinsByUserId(@Path("userId") int userId, @Query("limit") int limit);

    @GET("users/{userId}/pins")
    Observable<PinsListBean> getPinsByUserIdANDLimit(@Path("userId") int userId, @Query("max") int max, @Query("limit") int limit);

    @GET("users/{userId}/likes")
    Observable<PinsListBean> getLikesPinsByUserId(@Path("userId") int userId, @Query("limit") int limit);

    @GET("users/{userId}/likes")
    Observable<PinsListBean> getLikesPinsByUserIdANDLimit(@Path("userId") int userId, @Query("max") int max, @Query("limit") int limit);

}
