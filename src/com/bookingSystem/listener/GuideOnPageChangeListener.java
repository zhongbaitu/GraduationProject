package com.bookingSystem.listener;

import com.example.bookingsystem.Guide;
import com.example.bookingsystem.R;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;

/**
 * ViewPageµÄ¼àÌýÆ÷
 * 
 * @author ÖÓÂòÄÜ
 * @version 1.0
 * @created 2014-5-15
 * 
 */

public class GuideOnPageChangeListener implements OnPageChangeListener {


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
		switch (num) {
			case 0:
				Guide.tips1.setTextColor(Color.WHITE);
				Guide.tips2.setTextColor(Color.BLACK);
				Guide.tips3.setTextColor(Color.BLACK); 
				break;
			case 1:
				Guide.tips1.setTextColor(Color.BLACK);
				Guide.tips2.setTextColor(Color.WHITE);
				Guide.tips3.setTextColor(Color.BLACK);
				break;
			case 2:
				Guide.tips1.setTextColor(Color.BLACK);
				Guide.tips2.setTextColor(Color.BLACK);
				Guide.tips3.setTextColor(Color.WHITE);
				break;
		}

	}

}
