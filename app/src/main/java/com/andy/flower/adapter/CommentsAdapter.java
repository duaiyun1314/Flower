package com.andy.flower.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.andy.flower.Constants;
import com.andy.flower.R;
import com.andy.flower.bean.POJO.Comments;
import com.andy.flower.utils.ImageLoadFresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by andy.wang on 2016/8/26.
 */
public class CommentsAdapter extends BaseAdapter {
    private List<Comments.Comment> comments;
    private Context context;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public CommentsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return comments == null ? 0 : comments.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        CommentsViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_comment_item, parent, false);
            viewHolder = new CommentsViewHolder();
            viewHolder.commentDes = (TextView) view.findViewById(R.id.comment_des);
            viewHolder.commentTime = (TextView) view.findViewById(R.id.comment_time);
            viewHolder.commentUserName = (TextView) view.findViewById(R.id.comment_username);
            viewHolder.commentUserImg = (SimpleDraweeView) view.findViewById(R.id.comment_userimg);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (CommentsViewHolder) convertView.getTag();
        }
        Comments.Comment comment = comments.get(position);
        viewHolder.commentDes.setText(comment.getRaw_text());
        viewHolder.commentUserName.setText(comment.getUser().getUsername());
        viewHolder.commentTime.setText(format.format(new Date(comment.getCreated_at() * 1000)));
        String imgUrl = Constants.ImgRootUrl + comment.getUser().getAvatar().getKey() + Constants.SMALL_IMG_SUFFIX;
        new ImageLoadFresco.LoadImageFrescoBuilder(context, viewHolder.commentUserImg, imgUrl)
                .build();
        return view;
    }

    public void setData(List<Comments.Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    public class CommentsViewHolder {
        TextView commentDes;
        TextView commentUserName;
        TextView commentTime;
        SimpleDraweeView commentUserImg;
    }
}
