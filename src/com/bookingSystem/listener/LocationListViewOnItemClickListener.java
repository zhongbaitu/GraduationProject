package com.bookingSystem.listener;

import java.util.List;
import java.util.Map;

import com.example.bookingsystem.Location;
import com.example.bookingsystem.MainPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 定位或选择地区的 listview 的 item 监听
 * 
 * @author 钟买能
 * @version 1.0
 * @since 2014-5-24
 * 
 */
public class LocationListViewOnItemClickListener implements OnItemClickListener {

	private List<Map<String, String>> list;

	public LocationListViewOnItemClickListener(List<Map<String, String>> list) {
		super();
		this.list = list;
	}

	@SuppressLint("NewApi")
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int posision,
			long itemId) {
		if (list.get(posision).get("type").toString().equals("2")) {
			String city = list.get(posision).get("place").toString();
			MainPager.actionBar.setTitle(city);
			Location.location.finish();
		}

	}

}
