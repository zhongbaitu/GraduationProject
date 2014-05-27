package com.bookingSystem.listener;

import com.example.bookingsystem.R;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;

/**
 * ViewPage的监听器
 * 
 * @author 钟买能
 * @version 1.0
 * @created 2014-5-15
 * 
 */

public class MyOnPageChangeListener implements OnPageChangeListener {
	/**
	 * 对应的activity
	 */
	private int activityNumber;
	private Handler handler;

	public MyOnPageChangeListener(int activityNumber, Handler handler) {
		super();
		this.activityNumber = activityNumber;
		this.handler = handler;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int num) {
		Message message = new Message();
		switch (activityNumber) {
		case 0:
			message.what = num;
			handler.sendMessage(message);
			break;
		}

	}

}
