package com.bookingSystem.widget;

import com.example.bookingsystem.Main;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.widget.ListView;

public class MySwipeRefreshLayout extends SwipeRefreshLayout {

	public MySwipeRefreshLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
    public MySwipeRefreshLayout(Context context, AttributeSet attrs) {   
        super(context, attrs);   
    }   

	@Override
	public boolean canChildScrollUp() {
		// TODO Auto-generated method stub
		return First.listView.getFirstVisiblePosition()!=0;
	}

}
