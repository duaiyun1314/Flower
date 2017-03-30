package com.andy.flower.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andy.autoscrollbanner.switchadapter.SwitchAdapter;
import com.andy.commons.utils.imageloader.ImageLoadFresco;
import com.andy.flower.Constants;
import com.andy.flower.R;
import com.andy.flower.bean.Weekly;
import com.andy.flower.utils.StringUtil;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by andy on 16-7-21.
 */
public class BannerAdapter extends SwitchAdapter<Weekly> {
    public BannerAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        View view = null;
        Weekly weekly = datas.get(getPosition(position));
        String title = weekly.getTitle();
        HomePageHolder homeholder = null;
        BannerItemHolder itemHolder = null;
        if (convertView == null) {
            if (title.equals(context.getString(R.string.weekly_label))) {
                homeholder = new HomePageHolder();
                view = View.inflate(context, R.layout.layout_banner_home, null);
                homeholder.img = (SimpleDraweeView) view.findViewById(R.id.img);
                homeholder.tv = (TextView) view.findViewById(R.id.title);
                view.setTag(homeholder);
            } else {
                itemHolder = new BannerItemHolder();
                view = View.inflate(context, R.layout.layout_banner_item, null);
                itemHolder.img = (SimpleDraweeView) view.findViewById(R.id.img);
                itemHolder.tv = (TextView) view.findViewById(R.id.title);
                itemHolder.tv_date = (TextView) view.findViewById(R.id.release_no);
                view.setTag(itemHolder);
            }
        } else {
            view = convertView;
            if (title.equals(context.getString(R.string.weekly_label))) {
                homeholder = (HomePageHolder) convertView.getTag();
            } else {
                itemHolder = (BannerItemHolder) convertView.getTag();
            }
        }
        if (title.equals(context.getString(R.string.weekly_label))) {
            homeholder.tv.setText(title);
            homeholder.img.setImageResource(R.drawable.ic_weekly_home);
        } else {
            itemHolder.tv.setText(title);
            itemHolder.tv_date.setText(StringUtil.getWeeklyReleaseNo(weekly.getRelease_no()));
            String imageUrl = Constants.BannerImgUrl + weekly.getCover();
            itemHolder.img.setAspectRatio(430 / 230f);
            new ImageLoadFresco.LoadImageFrescoBuilder(context, itemHolder.img, imageUrl)
                    .build();
        }
        return view;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        Weekly weekly = datas.get(getPosition(position));
        int type = weekly.getTitle().equals(context.getString(R.string.weekly_label)) ? 0 : 1;
        return type;
    }

    private static class HomePageHolder {
        public SimpleDraweeView img;
        public TextView tv;
    }

    private static class BannerItemHolder {
        public SimpleDraweeView img;
        public TextView tv;
        public TextView tv_date;
    }
}
