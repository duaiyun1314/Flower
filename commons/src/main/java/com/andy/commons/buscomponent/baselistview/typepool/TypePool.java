package com.andy.commons.buscomponent.baselistview.typepool;

import android.support.annotation.NonNull;


import com.andy.commons.buscomponent.baselistview.adapter.ItemViewAdapter;

import java.util.ArrayList;

/**
 * Created by wanglu on 2017/1/3.
 */

public interface TypePool {

    void register(@NonNull Class<?> clazz, @NonNull ItemViewAdapter provider);

    /**
     * For getting index of the item class.
     * If the subclass is already registered, the registered mapping is used.
     * If the subclass is not registered, then look for the parent class is
     * registered, if the parent class is registered,
     * the subclass is regarded as the parent class.
     *
     * @param clazz the item class.
     * @return the index of the first occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     */
    int indexOf(@NonNull Class<?> clazz);

    @NonNull
    ArrayList<Class<?>> getContents();

    @NonNull
    ArrayList<ItemViewAdapter> getProviders();

    @NonNull
    ItemViewAdapter getProviderByIndex(int index);

    @NonNull
    <T extends ItemViewAdapter> T getProviderByClass(@NonNull Class<?> clazz);
}
