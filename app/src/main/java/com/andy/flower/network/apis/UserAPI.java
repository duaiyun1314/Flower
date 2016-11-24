package com.andy.flower.network.apis;

import com.andy.flower.Constants;
import com.andy.flower.bean.BoardListInfoBean;
import com.andy.flower.bean.PinsUser;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by andy on 16-6-7.
 */
public interface UserAPI {

    //获取我的画板集合信息 不需要显示需要保存
    //https://api.huaban.com/last_boards/?extra=recommend_tags
    @GET("last_boards/")
    Observable<BoardListInfoBean> httpsBoardListInfo(@Header(Constants.Authorization) String authorization, @Query("extra") String extra);

    @GET("users/{userId}")
    Observable<PinsUser> getUserDetail(@Header(Constants.Authorization) String authorization, @Path("userId") int userId);
}
