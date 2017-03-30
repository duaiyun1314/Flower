package com.andy.flower.utils;

import android.databinding.BindingAdapter;
import android.net.Uri;
import android.text.TextUtils;

import com.andy.commons.utils.imageloader.ImageLoadFresco;
import com.andy.flower.Constants;
import com.andy.flower.R;
import com.andy.flower.app.FlowerApplication;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by wanglu on 2017/1/10.
 */

public class BindingImageLoader {

    @BindingAdapter({"bind:avatar"})
    public static void avatarLoader(SimpleDraweeView imageView, String url) {
        if (TextUtils.isEmpty(url)) {
            Uri uri = Uri.parse("res:///" + R.drawable.ic_avatar);
            imageView.setImageURI(uri);
        } else {
            String imageUrl = Constants.ImgRootUrl + url;
            new ImageLoadFresco.LoadImageFrescoBuilder(FlowerApplication.from(), imageView, imageUrl)
                    .setIsCircle(true, true)
                    .build();
        }
    }
}
