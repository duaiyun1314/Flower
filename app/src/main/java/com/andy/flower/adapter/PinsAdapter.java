package com.andy.flower.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andy.flower.Constants;
import com.andy.flower.R;
import com.andy.flower.bean.POJO.PinsBean;
import com.andy.flower.utils.ImageLoadFresco;
import com.andy.flower.utils.ImageLoaderUtil;
import com.andy.flower.utils.Logger;
import com.andy.flower.utils.ScreenSizeUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.utils.ImageSizeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andy on 16-6-15.
 */
public class PinsAdapter extends BaseRecyclerAdapter<PinsBean> {

    private int mItemWidth;

    public PinsAdapter(Context context, List<PinsBean> datas) {
        super(context, datas);
        mItemWidth = (int) (ScreenSizeUtil.getScreenSize((Activity) context).widthPixels - context.getResources().getDimension(R.dimen.card_spacing_default_half) * 4) / 2;

    }

    @Override
    public void getView(RecyclerView.ViewHolder holder, int position) {
        PinsBean bean = mDatas.get(position);
        ((PinsItemViewHolder) holder).tv.setText(bean.getRaw_text());
        String imageUrl = Constants.ImgRootUrl + bean.getFile().getKey();
        SimpleDraweeView iv = ((PinsItemViewHolder) holder).img;
        int mItemHeight = ImageLoaderUtil.setImageLayoutParams(iv, mItemWidth, bean.getFile().getWidth(), bean.getFile().getHeight());
        ImageSize imageSize = new ImageSize(mItemWidth, mItemHeight);
        iv.setAspectRatio(mItemWidth / mItemHeight);
        Drawable drawable = mContext.getResources().getDrawable(R.mipmap.ic_launcher);
        new ImageLoadFresco.LoadImageFrescoBuilder(mContext, iv, imageUrl)
                .setProgressBarImage(drawable)
                .build();

    }

    @Override
    public BaseRecyclerViewHolder createHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.layout_pins_item, null);
        return new PinsItemViewHolder(view);
    }


    public class PinsItemViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.img)
        SimpleDraweeView img;
        @BindView(R.id.tv_card_title)
        TextView tv;

        public PinsItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
