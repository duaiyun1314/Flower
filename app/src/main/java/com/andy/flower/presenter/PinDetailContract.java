package com.andy.flower.presenter;

import android.view.Menu;

import com.andy.flower.bean.Comments;
import com.andy.flower.bean.PinsBean;

import java.util.List;

/**
 * Created by andy on 16-6-12.
 */
public class PinDetailContract {

    public interface IView {
        void initView(boolean fromSimpleBean, boolean refreshBase);
        void initFavoriteIcon();
        void setPinBean(PinsBean pinBean);
        void initComments(List<Comments.Comment> comments);
    }

    public interface IPresenter {
        void getDetailBean();
        void getPinComments();
        void actionLike(Menu menu);
        void actionDown();
        void actionComment(String comment);
    }
}
