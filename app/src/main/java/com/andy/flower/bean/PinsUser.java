package com.andy.flower.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;

import com.andy.flower.BR;

import java.io.Serializable;

/**
 * Created by andy on 16-6-7.
 */
public class PinsUser extends BaseObservable implements Serializable {
    private int user_id;
    private String username;
    private String urlname;
    private Avatar avatar;
    private String email;
    private int created_at;
    private int like_count;
    private int boards_like_count;
    private int following_count;
    private int commodity_count;
    private int board_count;
    private int follower_count;
    private int creations_count;
    private int pin_count;
    private String avatarUrl;
    private String authorization;


    public boolean isLogin() {
        return user_id > 0 && !TextUtils.isEmpty(getAuthorization());
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    @Bindable
    public String getAvatarUrl() {
        if (!TextUtils.isEmpty(avatarUrl)) {
            return avatarUrl;
        } else if (getAvatar() != null) {
            return getAvatar().getKey();
        }
        return "";
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        notifyPropertyChanged(BR.avatarUrl);
    }

    //该用户是否已经关注 关注为1 否则没有对应的网络字段 int默认值为0
    private int following;

    private ProfileBean profile;

    @Bindable
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
        notifyPropertyChanged(BR.user_id);
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }

    @Bindable
    public String getUrlname() {
        return urlname;
    }

    public void setUrlname(String urlname) {
        this.urlname = urlname;
        notifyPropertyChanged(BR.urlname);
    }

    @Bindable
    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
        notifyPropertyChanged(BR.avatar);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getBoards_like_count() {
        return boards_like_count;
    }

    public void setBoards_like_count(int boards_like_count) {
        this.boards_like_count = boards_like_count;
    }

    public int getFollowing_count() {
        return following_count;
    }

    public void setFollowing_count(int following_count) {
        this.following_count = following_count;
    }

    public int getCommodity_count() {
        return commodity_count;
    }

    public void setCommodity_count(int commodity_count) {
        this.commodity_count = commodity_count;
    }

    public int getBoard_count() {
        return board_count;
    }

    public void setBoard_count(int board_count) {
        this.board_count = board_count;
    }

    public int getFollower_count() {
        return follower_count;
    }

    public void setFollower_count(int follower_count) {
        this.follower_count = follower_count;
    }

    public int getCreations_count() {
        return creations_count;
    }

    public void setCreations_count(int creations_count) {
        this.creations_count = creations_count;
    }

    public int getPin_count() {
        return pin_count;
    }

    public void setPin_count(int pin_count) {
        this.pin_count = pin_count;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public ProfileBean getProfile() {
        return profile;
    }

    public void setProfile(ProfileBean profile) {
        this.profile = profile;
    }

    public static class ProfileBean {
        private String location;
        private String sex;
        private String birthday;
        private String job;
        private String url;
        private String about;

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }
    }

    public void set(PinsUser user) {
        setUser_id(user.getUser_id());
        setUsername(user.getUsername());
        setUrlname(user.getUrlname());
        setAvatar(user.getAvatar());
        setEmail(user.getEmail());
        setLike_count(user.getLike_count());
        setBoards_like_count(user.getBoards_like_count());
        setFollowing_count(user.getFollowing_count());
        setCreated_at(user.getCreated_at());
        setCommodity_count(user.getCommodity_count());
        setFollower_count(user.getFollower_count());
        setCreations_count(user.getCreations_count());
        setBoard_count(user.getBoard_count());
        setPin_count(user.getPin_count());
        setAvatarUrl(user.getAvatarUrl());
    }

    public void clear() {
        setUser_id(0);
        setUsername("");
        setUrlname("");
        setAvatar(null);
        setEmail("");
        setLike_count(0);
        setBoards_like_count(0);
        setFollowing_count(0);
        setCreated_at(0);
        setCommodity_count(0);
        setFollower_count(0);
        setCreations_count(0);
        setBoard_count(0);
        setPin_count(0);
        setAvatarUrl("");
        setAuthorization("");
    }

}
