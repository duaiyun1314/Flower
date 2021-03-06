package com.andy.flower.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andy.commons.buscomponent.baselistview.adapter.ItemViewAdapter;
import com.andy.commons.utils.imageloader.ImageLoadFresco;
import com.andy.commons.utils.imageloader.ImageUtils;
import com.andy.flower.Constants;
import com.andy.flower.R;
import com.andy.flower.app.PinDetailActivity;
import com.andy.flower.bean.PinsBean;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andy on 17-3-27.
 */

public class PinsItemAdapter extends ItemViewAdapter<PinsBean, PinsItemAdapter.PinsItemViewHolder> {
    public PinsItemAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    protected PinsItemViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        //不能使用View.inflate(mContext,R.layout.layout_pins_item,null);
        //详细原因请见 http://stackoverflow.com/questions/25632756/cardview-has-lost-margin-when-inflating
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_pins_item, parent, false);
        return new PinsItemViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull PinsItemViewHolder holder, @NonNull PinsBean bean) {
        ((PinsItemViewHolder) holder).tv.setText(bean.getRaw_text());
        String imageUrl = Constants.ImgRootUrl + bean.getFile().getKey() + Constants.GENERAL_IMG_SUFFIX;
        String ownerImgUrl = Constants.ImgRootUrl + bean.getUser().getAvatarUrl() + Constants.SMALL_IMG_SUFFIX;
        SimpleDraweeView img = ((PinsItemViewHolder) holder).img;
        SimpleDraweeView ownerImg = ((PinsItemViewHolder) holder).owner_img;
        img.setAspectRatio(ImageUtils.setImageLayoutParams(bean.getFile().getWidth(), bean.getFile().getHeight()));
        new ImageLoadFresco.LoadImageFrescoBuilder(mContext, img, imageUrl)
                .setProgressiveRender(true)
                .build();
        new ImageLoadFresco.LoadImageFrescoBuilder(mContext, ownerImg, ownerImgUrl)
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

    public class PinsItemViewHolder extends RecyclerView.ViewHolder {
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
