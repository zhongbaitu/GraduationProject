package com.example.bookingsystem;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.AvoidXfermode.Mode;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
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
	Bitmap bitmap;

	public Comment(Context context, View comment) {
		super();
		this.context = context;
		this.comment = comment;
		init();
		bind();
	}

	private void init() {
		bitmap=BitmapFactory.decodeResource(context.getResources(), R.drawable.header);
		sendButton = (Button) comment.findViewById(R.id.comment_send_button);
		commentEditText = (EditText) comment
				.findViewById(R.id.comment_edittext);
		commentListView = (ListView) comment
				.findViewById(R.id.comment_listview);

		commentEntity = new CommentEntity();

		commentList = new ArrayList<CommentEntity>();

		commentEntity.setIsMyMessage(false);
		commentEntity.setUserImage(bitmap);
		commentEntity.setComment("加油加油~~！！！");
		commentEntity.setCommentTime("2014-5-21");

		commentList.add(commentEntity);

		commentAdapter = new CommentAdapter(context, commentList);

		commentListView.setAdapter(commentAdapter);
		
	}

	//发送按钮
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
		commentEntity.setUserImage(bitmap);
		commentEntity.setCommentTime("2012-09-20 15:16:34");
		commentEntity.setComment(commentEditText.getText().toString());
		commentEntity.setIsMyMessage(false);
		commentList.add(commentEntity);
		commentAdapter.notifyDataSetChanged();
		commentListView.setSelection(commentList.size() - 1);
		commentEditText.setText("");
	}
	
	/**
	 * 转换图片成圆形
	 * 
	 * @param bitmap
	 *            传入Bitmap对象
	 * @return
	 */
	public Bitmap toRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			left = 0;
			top = 0;
			right = width;
			bottom = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);// 设置画笔无锯齿

		canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas
		paint.setColor(color);

		// 以下有两种方法画圆,drawRounRect和drawCircle
		// canvas.drawRoundRect(rectF, roundPx, roundPx, paint);// 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
		canvas.drawCircle(roundPx, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
		canvas.drawBitmap(bitmap, src, dst, paint); //以Mode.SRC_IN模式合并bitmap和已经draw了的Circle
		
		return output;
	}

}
