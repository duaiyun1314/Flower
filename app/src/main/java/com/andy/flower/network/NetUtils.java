package com.andy.flower.network;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.andy.flower.R;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by andy on 16-6-7.
 */
public class NetUtils {


    public static void checkHttpException(Context mContext, Throwable mThrowable) {
        String message = "";
        if ((mThrowable instanceof UnknownHostException)) {
            message = mContext.getString(R.string.message_net_error);
        } else if (mThrowable instanceof JsonSyntaxException) {
            message = mContext.getString(R.string.message_data_error);
        } else if (mThrowable instanceof SocketTimeoutException) {
            message = mContext.getString(R.string.message_timeout_error);
        } else if (mThrowable instanceof ConnectException) {
            message = mContext.getString(R.string.message_net_error);
        } else if (mThrowable instanceof HttpException) {
            try {
                String errorMessage = ((HttpException) mThrowable).response().errorBody().string();
                if (errorMessage.contains("invalid_grant")) {
                    message = mContext.getString(R.string.message_password_error);
                }
            } catch (IOException e) {
                e.printStackTrace();
                message = mContext.getString(R.string.message_server_error);
            }
        } else {
            message = mContext.getString(R.string.message_unknown_error);
        }
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }
}
