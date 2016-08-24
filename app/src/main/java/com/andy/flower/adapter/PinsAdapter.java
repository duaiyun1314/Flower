package com.andy.flower.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andy.flower.Constants;
import com.andy.flower.R;
import com.andy.flower.app.PinDetailActivity;
import com.andy.flower.bean.POJO.PinsBean;
import com.andy.flower.utils.ImageLoadFresco;
import com.andy.flower.utils.ImageUtils;
import com.facebook.drawee.drawable.AutoRotateDrawable;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andy on 16-6-15.
 */
public class PinsAdapter extends BaseRecyclerAdapter<PinsBean> {

    private Drawable progressDrawable;
    private Drawable failDrawable;

    public PinsAdapter(Context context, RecyclerView recyclerView) {
        super(context, recyclerView);
        TypedArray array = context.obtainStyledAttributes(new int[]{R.attr.colorPrimary});
        int colorPrimary = array.getColor(0, 0xFF1473AF);
        progressDrawable = new AutoRotateDrawable(ContextCompat.getDrawable(context, R.drawable.ic_load_progress), 5000);
        failDrawable = ImageUtils.getTintDrawable(context, R.drawable.ic_load_fail, colorPrimary);

    }

    @Override
    public void getView(RecyclerView.ViewHolder holder, int position) {
        PinsBean bean = mDatas.get(position);
        ((PinsItemViewHolder) holder).tv.setText(bean.getRaw_text());
        String imageUrl = Constants.ImgRootUrl + bean.getFile().getKey() + Constants.GENERAL_IMG_SUFFIX;
        String ownerImgUrl = Constants.ImgRootUrl + bean.getUser().getAvatar().getKey() + Constants.SMALL_IMG_SUFFIX;
        SimpleDraweeView img = ((PinsItemViewHolder) holder).img;
        SimpleDraweeView ownerImg = ((PinsItemViewHolder) holder).owner_img;
        img.setAspectRatio(ImageUtils.setImageLayoutParams(bean.getFile().getWidth(), bean.getFile().getHeight()));
        new ImageLoadFresco.LoadImageFrescoBuilder(mContext, img, imageUrl)
                .setFailureImage(failDrawable)
                .setProgressiveRender(true)
                .build();
        new ImageLoadFresco.LoadImageFrescoBuilder(mContext, ownerImg, ownerImgUrl)
                .setFailureImage(failDrawable)
                .setIsRadius(true, 4)
                .build();
        ((PinsItemViewHolder) holder).owner_des.setText(mContext.getString(R.string.owner_des, bean.getUser().getUsername(), bean.getBoard().getTitle()));
        ((PinsItemViewHolder) holder).img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PinDetailActivity.class);
                intent.putExtra(PinDetailActivity.PIN_VALUE_KEY, bean);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public BaseRecyclerViewHolder createHolder(ViewGroup parent, int viewType) {
        //不能使用View.inflate(mContext,R.layout.layout_pins_item,null);
        //详细原因请见 http://stackoverflow.com/questions/25632756/cardview-has-lost-margin-when-inflating
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_pins_item, parent, false);
        return new PinsItemViewHolder(view);
    }


    public class PinsItemViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.img)
        SimpleDraweeView img;
        @BindView(R.id.tv_card_title)
        TextView tv;
        @BindView(R.id.owner_img)
        SimpleDraweeView owner_img;
        @BindView(R.id.owner_des)
        TextView owner_des;

        public PinsItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
