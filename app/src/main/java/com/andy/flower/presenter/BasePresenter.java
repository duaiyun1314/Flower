package com.andy.flower.presenter;

import android.content.Context;

import com.andy.flower.Constants;

/**
 * Created by andy on 16-6-6.
 */
public class BasePresenter<UI extends BaseIView> {
    protected String mAuthorization = Constants.mClientInto;
    protected Context mContext;
    protected UI iView;

    public BasePresenter(Context context, UI ui) {
        this.mContext = context;
        this.iView = ui;
        iView.setPresenter(this);

    }

}
