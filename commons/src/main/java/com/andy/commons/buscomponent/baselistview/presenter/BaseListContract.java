package com.andy.commons.buscomponent.baselistview.presenter;

import java.util.List;

/**
 * Created by andy on 16-6-6.
 */
public class BaseListContract {
    public interface IView {

        void update(Object... args);

        void updateData(List items);

        void updateDataAdd(List items);

        void setFootStatus(int status, boolean showView);


    }

    public interface IPresenter<T> {
        void loadNew(Object... args);


        void loadNext(Object... args);

    }
}
