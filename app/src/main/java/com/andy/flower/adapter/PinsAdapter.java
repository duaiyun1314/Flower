package com.andy.flower.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andy.flower.Constants;
import com.andy.flower.R;
import com.andy.flower.bean.POJO.PinsBean;
import com.andy.flower.utils.ImageLoadFresco;
import com.andy.flower.utils.ImageUtils;
import com.andy.flower.utils.ScreenSizeUtil;
import com.facebook.drawee.drawable.AutoRotateDrawable;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andy on 16-6-15.
 */
public class PinsAdapter extends BaseRecyclerAdapter<PinsBean> {

    private Drawable progressDrawable;
    private Drawable failDrawable;

    public PinsAdapter(Context context, List<PinsBean> datas) {
        super(context, datas);
        TypedArray array = context.obtainStyledAttributes(new int[]{R.attr.colorPrimary});
        int colorPrimary = array.getColor(0, 0xFF1473AF);
        progressDrawable = new AutoRotateDrawable(context.getDrawable(R.drawable.ic_load_progress), 5000);
        failDrawable = ImageUtils.getTintDrawable(context, R.drawable.ic_load_fail, colorPrimary);

    }

    @Override
    public void getView(RecyclerView.ViewHolder holder, int position) {
        PinsBean bean = mDatas.get(position);
        ((PinsItemViewHolder) holder).tv.setText(bean.getRaw_text());
        String imageUrl = Constants.ImgRootUrl + bean.getFile().getKey();
        SimpleDraweeView iv = ((PinsItemViewHolder) holder).img;
        iv.setAspectRatio(ImageUtils.setImageLayoutParams(bean.getFile().getWidth(), bean.getFile().getHeight()));
        new ImageLoadFresco.LoadImageFrescoBuilder(mContext, iv, imageUrl)
                .setProgressBarImage(progressDrawable)
                .setFailureImage(failDrawable)
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
