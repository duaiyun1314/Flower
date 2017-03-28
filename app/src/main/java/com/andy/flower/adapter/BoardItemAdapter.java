package com.andy.flower.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andy.commons.buscomponent.baselistview.adapter.ItemViewAdapter;
import com.andy.flower.Constants;
import com.andy.flower.R;
import com.andy.flower.bean.PinsBean;
import com.andy.flower.bean.PinsBoard;
import com.andy.flower.utils.ImageLoadFresco;
import com.andy.flower.utils.ImageUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andy on 17-3-27.
 */

public class BoardItemAdapter extends ItemViewAdapter<PinsBoard, BoardItemAdapter.BoardsItemViewHolder> {

    public BoardItemAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    protected BoardsItemViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_board_item, parent, false);
        return new BoardsItemViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull BoardsItemViewHolder holder, @NonNull PinsBoard board) {
        BoardsItemViewHolder viewHolder = (BoardsItemViewHolder) holder;
        viewHolder.boardName.setText(board.getTitle());
        viewHolder.pinsCount.setText(board.getPin_count() + "");
        try {
            PinsBean firstPin = board.getPins().get(0);
            String imageUrl = Constants.ImgRootUrl + firstPin.getFile().getKey() + Constants.GENERAL_IMG_SUFFIX;
            viewHolder.img.setAspectRatio(ImageUtils.setImageLayoutParams(firstPin.getFile().getWidth(), firstPin.getFile().getHeight()));
            new ImageLoadFresco.LoadImageFrescoBuilder(mContext, viewHolder.img, imageUrl)
                    .setProgressiveRender(true)
                    .build();
        } catch (Exception e) {
        }

    }

    public class BoardsItemViewHolder extends RecyclerView.ViewHolder {
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
}
