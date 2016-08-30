package com.andy.flower.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andy.flower.Constants;
import com.andy.flower.R;
import com.andy.flower.bean.POJO.PinsBean;
import com.andy.flower.bean.POJO.PinsBoard;
import com.andy.flower.utils.ImageLoadFresco;
import com.andy.flower.utils.ImageUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andy.wang on 2016/8/30.
 */
public class BoardsAdapter extends BaseRecyclerAdapter<PinsBoard> {


    public BoardsAdapter(Context context, RecyclerView recyclerView) {
        super(context, recyclerView);
    }

    @Override
    public void getView(RecyclerView.ViewHolder holder, int position) {
        PinsBoard board = mDatas.get(position);
        PinsBean firstPin = board.getPins().get(0);
        String imageUrl = Constants.ImgRootUrl + firstPin.getFile().getKey() + Constants.GENERAL_IMG_SUFFIX;
        BoardsItemViewHolder viewHolder = (BoardsItemViewHolder) holder;
        viewHolder.img.setAspectRatio(ImageUtils.setImageLayoutParams(firstPin.getFile().getWidth(), firstPin.getFile().getHeight()));
        new ImageLoadFresco.LoadImageFrescoBuilder(mContext, viewHolder.img, imageUrl)
                .setFailureImage(failDrawable)
                .setProgressiveRender(true)
                .build();
        viewHolder.boardName.setText(board.getTitle());
        viewHolder.pinsCount.setText(board.getPin_count() + "");

    }

    @Override
    public BaseRecyclerViewHolder createHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_board_item, parent, false);
        return new BoardsItemViewHolder(view);
    }

    public class BoardsItemViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.img)
        SimpleDraweeView img;
        @BindView(R.id.board_name)
        TextView boardName;
        @BindView(R.id.pins_count)
        TextView pinsCount;

        public BoardsItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public void addItemTop(List<PinsBoard> datas) {
        if (datas.get(0).getPins() == null || datas.get(0).getPin_count() == 0) {
            datas.remove(0);
        }
        super.addItemTop(datas);
    }
}
