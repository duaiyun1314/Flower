package com.andy.commons.buscomponent.baselistview.presenter;

import android.content.Context;


/**
 * Created by andy on 16-6-6.
 */
public class BasePresenter<UI> {


    protected Context mContext;
    protected UI iView;

    public BasePresenter(Context context, UI ui) {
        this.mContext = context;
        this.iView = ui;
    }

}
