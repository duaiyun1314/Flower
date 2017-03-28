package com.andy.flower.apis;

import com.andy.flower.bean.BoardsListBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by andy on 16-6-15.
 */
public interface BoardsAPI {
    public static final String BASE_URL = "https://api.huaban.com/";

    @GET("users/{userId}/boards")
    Observable<BoardsListBean> getBoardsByUserId(@Path("userId") int userId, @Query("limit") int limit);

    @GET("users/{userId}/boards")
    Observable<BoardsListBean> getBoardsByUserIdANDLimit(@Path("userId") int userId, @Query("max") int max, @Query("limit") int limit);


}
