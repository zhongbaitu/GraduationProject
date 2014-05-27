package com.example.bookingsystem;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.bookingSystem.Entity.CommentEntity;
import com.bookingSystem.adapter.CommentAdapter;

public class Comment {
	private Context context;
	private View comment;
	private Button sendButton;
	private EditText commentEditText;
	private ListView commentListView;
	private List<CommentEntity> commentList;
	private CommentAdapter commentAdapter;
	private CommentEntity commentEntity = null;

	public Comment(Context context, View comment) {
		super();
		this.context = context;
		this.comment = comment;
		init();
		bind();
	}

	private void init() {
		sendButton = (Button) comment.findViewById(R.id.comment_send_button);
		commentEditText = (EditText) comment
				.findViewById(R.id.comment_edittext);
		commentListView = (ListView) comment
				.findViewById(R.id.comment_listview);

		commentEntity = new CommentEntity();

		commentList = new ArrayList<CommentEntity>();

		commentEntity.setIsMyMessage(false);
		commentEntity.setComment("º””Õº””Õ~~£°£°£°");
		commentEntity.setCommentTime("2014-5-21");

		commentList.add(commentEntity);

		commentAdapter = new CommentAdapter(context, commentList);

		commentListView.setAdapter(commentAdapter);

	}

	private void bind() {
		sendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				send();

			}
		});
	}

	private void send() {
		commentEntity = new CommentEntity();
		commentEntity.setCommentTime("2012-09-20 15:16:34");
		commentEntity.setComment(commentEditText.getText().toString());
		commentEntity.setIsMyMessage(false);
		commentList.add(commentEntity);
		commentAdapter.notifyDataSetChanged();
		commentListView.setSelection(commentList.size() - 1);
		commentEditText.setText("");
	}

}
