package com.andy.flower.bean.POJO;

import java.util.List;

/**
 * Created by andy on 16-7-21.
 */
public class Weeklies {
    private String imgHost;
    private String newImg;
    private List<Weekly> weeklies;

    public String getImgHost() {
        return imgHost;
    }

    public void setImgHost(String imgHost) {
        this.imgHost = imgHost;
    }

    public String getNewImg() {
        return newImg;
    }

    public void setNewImg(String newImg) {
        this.newImg = newImg;
    }

    public List<Weekly> getWeeklies() {
        return weeklies;
    }

    public void setWeeklies(List<Weekly> weeklies) {
        this.weeklies = weeklies;
    }
}
