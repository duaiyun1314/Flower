package com.andy.commons.model.http;

import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.greenrobot.eventbus.EventBus;

import retrofit2.HttpException;


/**
 * Created by wanglu on 2016/12/19.
 */

public class Errors {

    public static final String CODE_PASSWORD_ERROR = "100001";
    public static final String CODE_USER_NOT_FOUND = "100002";
    public static final String CODE_USERNAME_ERROR = "100003";
    public static final String CODE_PASSWORD_TIMES = "100004";
    public static final String CODE_LOGIN_TIMES = "100005";
    public static final String CODE_SINGN_TIMEOUT = "100102";
    public static final String CODE_TOKEN_INVALID = "100202";
    public static final String CODE_VERIFICATION_CODE_INVALIDED = "100018";

    public static String errorMessage(Throwable throwable) {
        try {
            if (throwable instanceof ErrorException) {
                return getErrorMessage(new Gson().fromJson(throwable.getMessage(), ErrorResponse.class));
            } else if (throwable instanceof HttpException) {
                return getErrorMessage(errorResponse((HttpException) throwable));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return throwable.getMessage();
    }

    public static ErrorResponse errorResponse(HttpException throwable) throws Exception {
        String errorInfo = throwable.response().errorBody().string();
        JsonElement jsonElement = new JsonParser().parse(errorInfo);
        return new Gson().fromJson(jsonElement.getAsJsonObject().get("error"), ErrorResponse.class);
    }

    private static String getErrorMessage(ErrorResponse errorResponse) {
        String message = "获取数据错误";
        String code = errorResponse.getCode();
        switch (code) {
            case CODE_PASSWORD_ERROR:
            case CODE_USERNAME_ERROR:
                message = "用户名或者密码错误";
                break;
            case CODE_USER_NOT_FOUND:
                message = "用户名不存在";
                break;
            case CODE_PASSWORD_TIMES:
                message = "密码错误次数超过当天限制";
                break;
            case CODE_LOGIN_TIMES:
                message = "登录过于频繁";
                break;
            case CODE_SINGN_TIMEOUT:
                message = "请求超时";
                break;
            case CODE_TOKEN_INVALID:
                message = "身份验证失败，请重新登录";
                break;
            case CODE_VERIFICATION_CODE_INVALIDED:
                message = "手机验证码无效";
                break;
        }
        return message;
    }

}
