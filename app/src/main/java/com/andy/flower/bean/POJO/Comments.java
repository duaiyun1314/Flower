package com.andy.flower.bean.POJO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by andy.wang on 2016/8/26.
 */
public class Comments implements Serializable {
    List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public class Comment {
        String comment_id;
        int pin_id;
        int user_id;
        String raw_text;
        //String text_meta;
        int status;
        long created_at;
        PinsUser user;

        public String getComment_id() {
            return comment_id;
        }

        public void setComment_id(String comment_id) {
            this.comment_id = comment_id;
        }

        public int getPin_id() {
            return pin_id;
        }

        public void setPin_id(int pin_id) {
            this.pin_id = pin_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getRaw_text() {
            return raw_text;
        }

        public void setRaw_text(String raw_text) {
            this.raw_text = raw_text;
        }

       /* public String getText_meta() {
            return text_meta;
        }

        public void setText_meta(String text_meta) {
            this.text_meta = text_meta;
        }*/

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getCreated_at() {
            return created_at;
        }

        public void setCreated_at(long created_at) {
            this.created_at = created_at;
        }

        public PinsUser getUser() {
            return user;
        }

        public void setUser(PinsUser user) {
            this.user = user;
        }
    }
}
