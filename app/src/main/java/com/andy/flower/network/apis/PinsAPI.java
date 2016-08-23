package com.andy.flower.network.apis;

import com.andy.flower.Constants;
import com.andy.flower.bean.POJO.PinDetailWrapper;
import com.andy.flower.bean.POJO.PinsBean;
import com.andy.flower.bean.POJO.PinsListBean;
import com.andy.flower.bean.POJO.Weeklies;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by andy on 16-6-15.
 */
public interface PinsAPI {
    //https//api.huaban.com/favorite/food_drink?limit=20
    @GET("favorite/{type}")
    Observable<PinsListBean> getPinsByCategory(@Header(Constants.Authorization) String authorization, @Path("type") String type, @Query("limit") int limit);

    //https//api.huaban.com/favorite/food_drink?max=5445324325&limit=20
    @GET("favorite/{type}")
    Observable<PinsListBean> getPinsByCategoryANDLimit(@Header(Constants.Authorization) String authorization, @Path("type") String type, @Query("max") int max, @Query("limit") int limit);

    //https://api.huaban.com/weekly?max=2016-07-21&limit=3
    @GET("weekly?")
    Observable<Weeklies> getWeekliesByLimit(@Query("max") String releaseNo, @Query("limit") int limit);

    @GET("pins/{pinId}")
    Observable<PinDetailWrapper> getPinDetailByPinId(@Header(Constants.Authorization) String authorization, @Path("pinId") int pinId);
}
