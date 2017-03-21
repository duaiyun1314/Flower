package com.andy.flower.network.apis;

import com.andy.flower.Constants;
import com.andy.flower.bean.BoardListInfoBean;
import com.andy.flower.bean.PinsUser;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by andy on 16-6-7.
 */
public interface UserAPI {

    public static final String BASE_URL = "https://api.huaban.com/";

    //获取我的画板集合信息 不需要显示需要保存
    //https://api.huaban.com/last_boards/?extra=recommend_tags
    @GET("last_boards/")
    Observable<BoardListInfoBean> httpsBoardListInfo(@Query("extra") String extra);

    @GET("users/{userId}")
    Observable<PinsUser> getUserDetail(@Path("userId") int userId);
}
