package com.andy.flower.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.andy.commons.utils.imageloader.ImageLoadFresco;
import com.andy.flower.Constants;
import com.andy.flower.R;
import com.facebook.drawee.view.SimpleDraweeView;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andy.wang on 2016/8/23.
 */
public class PinRankSmallView extends RelativeLayout {
    @BindView(R.id.img_image_board_1)
    SimpleDraweeView board1;
    @BindView(R.id.img_image_board_2)
    SimpleDraweeView board2;
    @BindView(R.id.img_image_board_3)
    SimpleDraweeView board3;
    @BindView(R.id.img_image_board_4)
    SimpleDraweeView board4;
    private SimpleDraweeView[] draweeViews;

    public PinRankSmallView(Context context) {
        this(context, null);
    }

    public PinRankSmallView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PinRankSmallView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_pin_rank, this);
        ButterKnife.bind(this);
        draweeViews = new SimpleDraweeView[]{board1, board2, board3, board4};
    }

    public void update(String[] urls) {
        for (int i = 0; i < urls.length; i++) {
            String key = urls[i];
            if (!TextUtils.isEmpty(key)) {
                String imgUrl = Constants.ImgRootUrl + key + Constants.SMALL_IMG_SUFFIX;
                new ImageLoadFresco.LoadImageFrescoBuilder(getContext(), draweeViews[i], imgUrl)
                        .setIsRadius(true, 3)
                        .build();
            }
        }
    }

}
