package com.andy.commons.buscomponent.baselistview.typepool;

import android.support.annotation.NonNull;


import com.andy.commons.buscomponent.baselistview.adapter.ItemViewAdapter;

import java.util.ArrayList;

/**
 * Created by wanglu on 2017/1/3.
 */

public class MultiTypePool implements TypePool {
    private final String TAG = MultiTypePool.class.getSimpleName();
    private ArrayList<Class<?>> contents;
    private ArrayList<ItemViewAdapter> providers;


    public MultiTypePool() {
        this.contents = new ArrayList<>();
        this.providers = new ArrayList<>();
    }


    public MultiTypePool(int initialCapacity) {
        this.contents = new ArrayList<>(initialCapacity);
        this.providers = new ArrayList<>(initialCapacity);
    }


    public void register(@NonNull Class<?> clazz, @NonNull ItemViewAdapter provider) {
        if (!contents.contains(clazz)) {
            contents.add(clazz);
            providers.add(provider);
        } else {
            int index = contents.indexOf(clazz);
            providers.set(index, provider);
        }
    }


    @Override
    public int indexOf(@NonNull final Class<?> clazz) {
        int index = contents.indexOf(clazz);
        if (index >= 0) {
            return index;
        }
        for (int i = 0; i < contents.size(); i++) {
            if (contents.get(i).isAssignableFrom(clazz)) {
                return i;
            }
        }
        return index;
    }


    @NonNull
    @Override
    public ArrayList<Class<?>> getContents() {
        return contents;
    }


    @NonNull
    @Override
    public ArrayList<ItemViewAdapter> getProviders() {
        return providers;
    }


    @NonNull
    @Override
    public ItemViewAdapter getProviderByIndex(int index) {
        return providers.get(index);
    }


    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ItemViewAdapter> T getProviderByClass(@NonNull final Class<?> clazz) {
        return (T) getProviderByIndex(indexOf(clazz));
    }
}
