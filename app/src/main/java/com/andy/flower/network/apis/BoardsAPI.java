package com.andy.flower.network.apis;

import com.andy.flower.Constants;
import com.andy.flower.bean.BoardsListBean;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by andy on 16-6-15.
 */
public interface BoardsAPI {

    @GET("users/{userId}/boards")
    Observable<BoardsListBean> getBoardsByUserId(@Header(Constants.Authorization) String authorization, @Path("userId") int userId, @Query("limit") int limit);

    @GET("users/{userId}/boards")
    Observable<BoardsListBean> getBoardsByUserIdANDLimit(@Header(Constants.Authorization) String authorization, @Path("userId") int userId, @Query("max") int max, @Query("limit") int limit);


}
