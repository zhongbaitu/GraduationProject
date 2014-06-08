package com.bookingSystem.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class MyListView extends ListView{
    public MyListView(Context context) {
            super(context);
    }
    public MyListView(Context context, AttributeSet attrs) {
            super(context, attrs);
    }
    public MyListView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                            MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
    }
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		System.out.println("onScroll");
		
	}
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		System.out.println("onScrollStateChanged");
		
	}
}