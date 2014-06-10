package com.example.bookingsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.bookingSystem.adapter.LocationAdapter;
import com.bookingSystem.listener.LocationListViewOnItemClickListener;
import com.bookingSystem.widget.AlphabetListView;
import com.bookingSystem.widget.AlphabetListView.OnTouchAlphabetChangedListener;

/**
 * 定位或选择地区的activity
 * 
 * @author 钟买能
 * @version 1.0
 * @since 2014-5-24
 * 
 */
public class Location extends Activity {

	private ListView listview;
	private ActionBar actionBar;
	private List<Map<String, String>> list;
	private LocationAdapter locationAdapter;
	private String city;
	private AlphabetListView alphabetListView;
	private Map<String, String> map;
	private String[] alphabet = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	private TextView tipsText;
	private Handler handler;
	private LocationTips locationTips;
	private Map<String, Integer> markMap;
	/**
	 * 单一静态实例
	 */
	public static Location location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location);
		location = this;
		initTips();
		init();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.location_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		}
		return true;
	}

	@SuppressLint("NewApi")
	private void init() {
		city=getIntent().getStringExtra("city");
		listview = (ListView) this.findViewById(R.id.location_listview);
		list = new ArrayList<Map<String, String>>();
		//用于记录字母的位置
		markMap=new HashMap<String, Integer>();
		locationAdapter = new LocationAdapter(Location.this, initData());
		listview.setAdapter(locationAdapter);
		listview.setOnItemClickListener(new LocationListViewOnItemClickListener(list));

		actionBar = getActionBar();
		actionBar.setBackgroundDrawable(this.getResources().getDrawable(
				R.drawable.actionbar_background));
		actionBar.setHomeButtonEnabled(true);
		actionBar.setIcon(R.drawable.ic_action_back);
		actionBar.setTitle("");
		
		handler=new Handler();
		locationTips=new LocationTips();
		
		
		alphabetListView=(AlphabetListView) this.findViewById(R.id.alphabet_listView);
		alphabetListView.setOnTouchAlphabetChangedListener(new OnTouchAlphabetChangedListener() {
			
			@Override
			public void onTouchAlphabetChanged(String alphabet) {
	
				int position=markMap.get(alphabet);
				listview.setSelection(position);
				tipsText.setText(alphabet);
				tipsText.setVisibility(View.VISIBLE);
				//remove后重新加载一个Runnable
				handler.removeCallbacks(locationTips); 
				//1秒钟后消失
				handler.postDelayed(locationTips, 1000);
				
			}
		});
	}
	
	private void initTips(){
		LayoutInflater inflater = LayoutInflater.from(this);
		tipsText= (TextView) inflater.inflate(R.layout.location_tips, null);
//		tipsText=(TextView) tipsView.findViewById(R.id.location_tips_text);
		tipsText.setVisibility(View.GONE);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_APPLICATION, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
				PixelFormat.TRANSLUCENT);
		WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		windowManager.addView(tipsText, lp);
	}

	private List<Map<String, String>> initData() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", "1");
		map.put("title", "根据定位：");
		list.add(map);
		
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("type", "2");
		map2.put("place", city);
		list.add(map2);
		
		for(int i=0;i<alphabet.length;i++){
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("type", "1");
			map1.put("title", alphabet[i]);
			list.add(map1);
			markMap.put(alphabet[i], i+2);
		}

		return list;

	}
	// 让 window 显示一秒钟后消失
	private class LocationTips implements Runnable
	{

		@Override
		public void run()
		{
			tipsText.setVisibility(View.GONE);
		}

	}
	
//	private Map<String, String> putPlaceData(){
//		Map<String, String> map=new HashMap<String, String>();
//		map.put("character", value)
//		return map;
//		
//	}


}
