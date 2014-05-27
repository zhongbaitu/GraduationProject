package com.bookingSystem.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * 
 * @author ÷”¬Úƒ‹
 * @version 1.0
 * @created 2014-5-15
 * 
 */
public class MyPagerAdapter extends PagerAdapter {

	private List<View> myListViews;

	public MyPagerAdapter(List<View> myListViews) {
		super();
		this.myListViews = myListViews;
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView(myListViews.get(arg1));
	}

	@Override
	public int getCount() {
		return myListViews.size();
	}

	@Override
	public Object instantiateItem(View arg0, int arg1) {
		((ViewPager) arg0).addView(myListViews.get(arg1), 0);
		return myListViews.get(arg1);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == (arg1);
	}

}
