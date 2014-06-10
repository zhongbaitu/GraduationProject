package com.bookingSystem.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.bookingsystem.R;
/**
 * 自定义字母listview
 * @author 钟买能
 * @version 1.0
 * @since 2014-5-31
 *
 */
public class AlphabetListView extends View {
	
	private String[] alphabet = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	//是否触摸到此控件
	private boolean isTouch=false;
	private Paint paint=new Paint();
	private OnTouchAlphabetChangedListener onTouchAlphabetChangedListener;
	private int oldY=-1;
	
	public AlphabetListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public AlphabetListView(Context context, AttributeSet attrs){
		super(context, attrs);
	}
	public AlphabetListView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle); 
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		//触摸到此控件，更改背景颜色
		if(isTouch){
			canvas.drawColor(R.color.dark_gray);
		}
		int width=getWidth();
		int height=getHeight();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		//每一个字幕占的高度
		int itemHeight=height/alphabet.length;
		for(int i=0;i<alphabet.length;i++){
			//measureText方法计算每个字母的长度
			float x=width/2-paint.measureText(alphabet[i])/2;
			float y=itemHeight*(i+1);
			canvas.drawText(alphabet[i], x, y, paint);
		}
		super.onDraw(canvas);
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		
		float mY=event.getY();
		int num=(int) (mY/getHeight()*alphabet.length);
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if(oldY!=num && num>=0 && num<alphabet.length){
				oldY=num;
				//改变背景颜色
				isTouch=true;
				onTouchAlphabetChangedListener.onTouchAlphabetChanged(alphabet[num]);
			}

			
			invalidate();
			break;

		case MotionEvent.ACTION_MOVE:
			if(oldY!=num && num>=0 && num<alphabet.length){
				oldY=num;
				isTouch=true;
				onTouchAlphabetChangedListener.onTouchAlphabetChanged(alphabet[num]);
			}
			
			
			break;
		case MotionEvent.ACTION_UP:
			isTouch=false;
			invalidate();
			break;
		}
		return true;
	}

	public interface OnTouchAlphabetChangedListener{
		public void onTouchAlphabetChanged(String alphabet);
	}
	
	public void setOnTouchAlphabetChangedListener(OnTouchAlphabetChangedListener listener){
		this.onTouchAlphabetChangedListener=listener;
	}

}
