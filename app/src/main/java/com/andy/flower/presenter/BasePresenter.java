package com.andy.flower.presenter;

import android.content.Context;

import com.andy.flower.Constants;
import com.andy.flower.app.FlowerApplication;

/**
 * Created by andy on 16-6-6.
 */
public class BasePresenter<UI extends BaseIView> {
    protected String mAuthorization = Constants.mClientInto;
    protected FlowerApplication mApp = FlowerApplication.from();
    protected Context mContext;
    protected UI iView;

    public BasePresenter(Context context, UI ui) {
        this.mContext = context;
        this.iView = ui;
    }

}
