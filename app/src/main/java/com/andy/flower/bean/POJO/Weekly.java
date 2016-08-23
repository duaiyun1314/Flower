package com.andy.flower.bean.POJO;

import java.io.Serializable;

/**
 * Created by andy on 16-7-21.
 */
public class Weekly implements Serializable {
    private int weekly_id;
    private int is_final;
    private String release_date;
    private String release_no;
    private String title;
    private String description;
    private String cover;

    public int getWeekly_id() {
        return weekly_id;
    }

    public void setWeekly_id(int weekly_id) {
        this.weekly_id = weekly_id;
    }

    public int getIs_final() {
        return is_final;
    }

    public void setIs_final(int is_final) {
        this.is_final = is_final;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getRelease_no() {
        return release_no;
    }

    public void setRelease_no(String release_no) {
        this.release_no = release_no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
