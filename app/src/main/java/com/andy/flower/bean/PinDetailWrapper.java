package com.andy.flower.bean;

import java.io.Serializable;

/**
 * Created by andy.wang on 2016/8/23.
 */
public class PinDetailWrapper implements Serializable {
    PinsBean pin;
    /*String stores;
    boolean promotions;
    String ads;*/

    public PinsBean getPin() {
        return pin;
    }

    public void setPin(PinsBean pin) {
        this.pin = pin;
    }
/*
    public String getStores() {
        return stores;
    }

    public void setStores(String stores) {
        this.stores = stores;
    }

    public boolean isPromotions() {
        return promotions;
    }

    public void setPromotions(boolean promotions) {
        this.promotions = promotions;
    }

    public String getAds() {
        return ads;
    }

    public void setAds(String ads) {
        this.ads = ads;
    }*/
}
