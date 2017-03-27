package com.andy.commons.model.http;

import com.google.gson.Gson;

import retrofit2.HttpException;


/**
 * Created by wanglu on 2016/12/19.
 */

public class Errors {

    public static final String CODE_PASSWORD_ERROR = "invalid_grant";


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
        return new Gson().fromJson(errorInfo, ErrorResponse.class);
    }

    private static String getErrorMessage(ErrorResponse errorResponse) {
        String message = "获取数据错误";
        String code = errorResponse.getError();
        switch (code) {
            case CODE_PASSWORD_ERROR:
                message = "用户名或者密码错误";
                break;
        }
        return message;
    }

}
