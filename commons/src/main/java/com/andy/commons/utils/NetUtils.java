package com.andy.commons.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.andy.commons.R;
import com.andy.commons.model.http.ErrorException;
import com.andy.commons.model.http.Errors;
import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Locale;

import retrofit2.HttpException;

public class NetUtils {

    public static enum NetType {
        WIFI, CMNET, CMWAP, NONE
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = mgr.getAllNetworkInfo();
        if (info != null) {
            for (int i = 0; i < info.length; i++) {
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }

    public static NetType getAPNType(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return NetType.NONE;
        }
        int nType = networkInfo.getType();

        if (nType == ConnectivityManager.TYPE_MOBILE) {
            if (networkInfo.getExtraInfo().toLowerCase(Locale.getDefault()).equals("cmnet")) {
                return NetType.CMNET;
            } else {
                return NetType.CMWAP;
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            return NetType.WIFI;
        }
        return NetType.NONE;
    }

    public static void checkHttpException(Context mContext, Throwable mThrowable) {
        checkHttpException(mContext, mThrowable, null);
    }

    public static void checkHttpException(Context mContext, Throwable mThrowable, String outMessage) {
        String message = mContext.getResources().getString(R.string.data_get_error);
        if ((mThrowable instanceof UnknownHostException)) {
            message = mContext.getResources().getString(R.string.net_error);
        } else if (mThrowable instanceof JsonSyntaxException) {
            message = mContext.getResources().getString(R.string.data_parse_error);
        } else if (mThrowable instanceof SocketTimeoutException) {
            message = mContext.getResources().getString(R.string.net_timeout);
        } else if (mThrowable instanceof ConnectException) {
            message = mContext.getResources().getString(R.string.net_error);
        } else if (mThrowable instanceof ErrorException || mThrowable instanceof HttpException) {
            String actionMessage = Errors.errorMessage(mThrowable);
            if (!TextUtils.isEmpty(actionMessage)) {
                ToastUtil.show(mContext, actionMessage);
            }
            return;
        }
        if (!TextUtils.isEmpty(outMessage)) {
            ToastUtil.show(mContext, outMessage);
        } else {
            ToastUtil.show(mContext, message);
        }
    }
}
