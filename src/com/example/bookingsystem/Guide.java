package com.example.bookingsystem;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Scroller;
import android.widget.TextView;

import com.bookingSystem.adapter.MyPagerAdapter;
import com.bookingSystem.effect.DepthPageTransformer;
import com.bookingSystem.effect.ZoomOutPageTransformer;
import com.bookingSystem.listener.GuideOnPageChangeListener;

/**
 * 引导页面
 * 
 * @author 钟买能
 * @version 1.0
 * @created 2014-5-14
 */
public class Guide extends Activity {

	private final int ACTIVITY_NUMBER = 0;

	private ViewPager viewPager;

	private List<View> list; // 用于放置介绍的界面

	public static TextView tips1, tips2, tips3;

	private Button loginButton, registerButton;
	/**
	 * 单一实例
	 */
	public static Guide guide;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.guide);
		init();
		bind();
	}

	private void init() {
		list = new ArrayList<View>();
		loginButton = (Button) this.findViewById(R.id.login_button);
		registerButton = (Button) this.findViewById(R.id.register_button);
		viewPager = (ViewPager) this.findViewById(R.id.viewPager);
		tips1 = (TextView) this.findViewById(R.id.guide_tips1);
		tips2 = (TextView) this.findViewById(R.id.guide_tips2);
		tips3 = (TextView) this.findViewById(R.id.guide_tips3);
		list.add(getLayoutInflater().inflate(R.layout.guide_pic1, null));
		list.add(getLayoutInflater().inflate(R.layout.guide_pic2, null));
		list.add(getLayoutInflater().inflate(R.layout.guide_pic3, null));

	}

	private void bind() {
		viewPager.setAdapter(new MyPagerAdapter(list));
		viewPager.setOnPageChangeListener(new GuideOnPageChangeListener());
		loginButton.setOnClickListener(new MyOnClickListener());
		registerButton.setOnClickListener(new MyOnClickListener());
	}

	class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.login_button:
				new Login(Guide.this);
				break;
			case R.id.register_button:
				startActivity(new Intent(Guide.this, Register.class));
				overridePendingTransition(R.anim.right_push_in, R.anim.blank);
				break;
			}

		}

	}

}
