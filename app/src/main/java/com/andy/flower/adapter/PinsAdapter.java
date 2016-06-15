package com.andy.flower.adapter;

import android.content.Context;
import android.graphics.Matrix;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andy.flower.Constants;
import com.andy.flower.R;
import com.andy.flower.bean.POJO.PinsBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andy on 16-6-15.
 */
public class PinsAdapter extends BaseRecyclerAdapter<PinsBean> {
    public PinsAdapter(Context context, List<PinsBean> datas) {
        super(context, datas);
    }

    @Override
    public void getView(RecyclerView.ViewHolder holder, int position) {
        PinsBean bean = mDatas.get(position);
        ((PinsItemViewHolder) holder).tv.setText(bean.getRaw_text());
        String imageUrl = Constants.ImgRootUrl + bean.getFile().getKey();
        ImageView iv = ((PinsItemViewHolder) holder).img;
        Matrix matrix = new Matrix();
        int i = iv.getLayoutParams().width / bean.getFile().getWidth();
        matrix.postScale(i, i);
        iv.setImageMatrix(matrix);
        ImageLoader.getInstance().displayImage(imageUrl, iv);
    }

    @Override
    public BaseRecyclerViewHolder createHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.layout_pins_item, null);
        return new PinsItemViewHolder(view);
    }


    public class PinsItemViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.tv_card_title)
        TextView tv;

        public PinsItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
