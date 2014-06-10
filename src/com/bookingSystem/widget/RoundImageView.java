package com.bookingSystem.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
/**
 * 自定义圆形ImageView
 * @author 钟买能
 * @version 1.0
 * @since 2014-5-30
 */
public class RoundImageView extends ImageView {
	
	public RoundImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public RoundImageView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		
		//获取当前控件的drawable
		Drawable drawable = getDrawable();

		if (drawable == null) {
			return;
		}

		//这里get回来的宽度和高度是当前控件相对应的宽度和高度（在xml设置）
		if (getWidth() == 0 || getHeight() == 0) {
			return; 
		}
	
		//画笔
		Paint paint = new Paint();
		//颜色设置
		paint.setColor(0xff424242);
		//抗锯齿
		paint.setAntiAlias(true);
		//Paint的Xfermode，PorterDuff.Mode.SRC_IN 取两层图像的交集部门,只显示上层图像。 
		PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
		//获取bitmap，即传入imageview的bitmap
		Bitmap bitmap =  ((BitmapDrawable)drawable).getBitmap() ;

		//标志
		int saveFlags = Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG;
		canvas.saveLayer(0, 0, getWidth(), getHeight(), null, saveFlags);
		
		//画遮罩，画出来就是一个和空间大小相匹配的圆
		canvas.drawCircle(getWidth()/2, getHeight()/2, getWidth()/2, paint);
		paint.setXfermode(xfermode);
		
		//空间的大小/bitmap的大小=bitmap缩放的倍数
		float scaleWidth = ((float) getWidth()) / bitmap.getWidth();
		float scaleHeight = ((float) getHeight()) / bitmap.getHeight();
		
	    Matrix matrix = new Matrix();
	    matrix.postScale(scaleWidth, scaleHeight); 
	    
	    //bitmap缩放
	    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	    
	    //draw上去
		canvas.drawBitmap(bitmap, 0, 0, paint);
//		paint.setXfermode(null);
		canvas.restore();

	}

}