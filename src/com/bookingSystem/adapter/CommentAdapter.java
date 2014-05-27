package com.bookingSystem.adapter;

import java.util.List;

import com.bookingSystem.Entity.CommentEntity;
import com.example.bookingsystem.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentAdapter extends BaseAdapter {

	private Context context;
	private List<CommentEntity> commentList;
	private LayoutInflater inflater;

	public CommentAdapter(Context context, List<CommentEntity> commentList) {
		super();
		this.context = context;
		this.commentList = commentList;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return commentList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return commentList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CommentHolder holder = null;
		if (convertView == null) {
			holder = new CommentHolder();
			convertView = inflater.inflate(R.layout.comment_from_style, null);
			holder.userImgae = (ImageView) convertView
					.findViewById(R.id.comment_from_image);
			holder.commentText = (TextView) convertView
					.findViewById(R.id.comment_from_text);
			holder.commentTime = (TextView) convertView
					.findViewById(R.id.comment_from_time);
			convertView.setTag(holder);
		} else {
			holder = (CommentHolder) convertView.getTag();
		}
		holder.userImgae.setImageResource(commentList.get(position)
				.getUserImage());
		holder.commentText.setText(commentList.get(position).getComment());
		holder.commentTime.setText(commentList.get(position).getCommentTime());
		return convertView;
	}

	private class CommentHolder {
		private ImageView userImgae;
		private TextView commentText;
		private TextView commentTime;
	}

}
