package com.andy.commons.buscomponent.baselistview.typepool;

import android.support.annotation.NonNull;


import com.andy.commons.buscomponent.baselistview.adapter.ItemViewAdapter;

import java.util.ArrayList;

/**
 * Created by wanglu on 2017/1/3.
 */

public class GlobalMultiTypePool {

    private static MultiTypePool pool = new MultiTypePool();


    public static void register(@NonNull Class<?> clazz, @NonNull ItemViewAdapter provider) {
        pool.register(clazz, provider);
    }


    public static int indexOf(@NonNull Class<?> clazz) {
        return pool.indexOf(clazz);
    }


    @NonNull
    public static ArrayList<Class<?>> getContents() {
        return pool.getContents();
    }


    @NonNull
    public static ArrayList<ItemViewAdapter> getProviders() {
        return pool.getProviders();
    }


    @NonNull
    public static ItemViewAdapter getProviderByIndex(int index) {
        return pool.getProviderByIndex(index);
    }


    @NonNull
    public static <T extends ItemViewAdapter> T getProviderByClass(
            @NonNull Class<?> clazz) {
        return pool.getProviderByClass(clazz);
    }


    @NonNull
    public static MultiTypePool getPool() {
        return pool;
    }
}
