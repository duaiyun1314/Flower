package com.andy.flower.presenter;

/**
 * Created by andy on 16-6-6.
 */
public interface BaseIView<T extends BasePresenter> {
    void setPresenter(T presenter);
}
