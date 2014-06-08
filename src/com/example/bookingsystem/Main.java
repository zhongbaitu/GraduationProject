package com.example.bookingsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bookingSystem.adapter.MainListViewAdapter;
import com.bookingSystem.listener.MainListviewOnItemClickListener;
import com.bookingSystem.listener.MainListviewOnScrollListener;
import com.bookingSystem.widget.JsonArrayUTF8Request;

/**
 * 主界面
 * 
 * @author 钟买能
 * @version 1.0
 * @since 2014-5-16
 */
public class Main implements SwipeRefreshLayout.OnRefreshListener {
	private Context context;
	private View main;
	private GridView gridView;
	public static ListView listView;
	public static LinearLayout wraper;
	private List<Map<String, String>> item;
	private ArrayList<HashMap<String, Object>> gridItem;
	private SwipeRefreshLayout swipeLayout;
	private RequestQueue queue;
	private SimpleAdapter listViewSimpleAdapter;
	private MainListViewAdapter mainListViewAdapter;

	public Main(Context context, View main) {
		super();
		this.main = main;
		this.context = context;
		init();
		takeInfo();
	}

	private void init() {
		gridView = (GridView) main.findViewById(R.id.main_gridview);

		SimpleAdapter gridViewSimpleAdapter = new SimpleAdapter(context,
				getGridViewData(), R.layout.classification_style, new String[] {
						"itemImage", "itemName" }, new int[] {
						R.id.class_picture, R.id.class_text });
		gridView.setAdapter(gridViewSimpleAdapter);
		item = new ArrayList<Map<String,String>>();
		mainListViewAdapter=new MainListViewAdapter(context, item);
		listView = (ListView) main.findViewById(R.id.main_listview);
		listView.setAdapter(mainListViewAdapter); 
		listView.setOnScrollListener(new MainListviewOnScrollListener());
		listView.setOnItemClickListener(new MainListviewOnItemClickListener(context, item));

		swipeLayout = (SwipeRefreshLayout) main
				.findViewById(R.id.swipe_container);
		swipeLayout.setOnRefreshListener(this);
		swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_purple);

		queue = Volley.newRequestQueue(context);

	}

	//GridView 的数据
	private ArrayList<HashMap<String, Object>> getGridViewData() {
		gridItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 5; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage",
					context.getResources().getIdentifier("food" + i,
							"drawable", context.getPackageName()));
			map.put("itemName",
					context.getResources().getIdentifier("food" + i, "string",
							context.getPackageName()));
			gridItem.add(map);
		}

		return gridItem;
	}
	
	//Put 数据进 listview 的 list
	private List<Map<String, String>> putListViewData(String image,
			String name, String openTime, String closeTime, String food,String price) {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("itemImage", "http://pic18.nipic.com/20111229/6755670_152852649000_2.jpg");
		map.put("itemName", name);
		map.put("openTime", openTime);
		map.put("closeTime", closeTime);
		map.put("food", food);
		map.put("price", price);
		item.add(map);

		return item;
	}
	//向服务器请求数据
	private void takeInfo() {
		JsonArrayUTF8Request jsonArrayRequest = new JsonArrayUTF8Request(
				Common.RESTAURAN_INFO, new Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray jsonArray) {

						try {
							resourceDo(jsonArray);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						mainListViewAdapter.notifyDataSetChanged();
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {

					}
				});
		queue.add(jsonArrayRequest);
		queue.start();
	}
	
	//服务器返回的结果处理
	private void resourceDo(JSONArray jsonArray) throws JSONException {
		for (int i = 0; i < jsonArray.length(); i++) {
			putListViewData("",
					jsonArray.getJSONObject(i).getString("EP_name"), 
					jsonArray.getJSONObject(i).getString("EP_sdate"),
					jsonArray.getJSONObject(i).getString("EP_edate"), 
					jsonArray.getJSONObject(i).getString("EP_food"),
					jsonArray.getJSONObject(i).getString("consumption"));
		}

	};

	//下来刷新的处理
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		gridView.setVisibility(0);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				swipeLayout.setRefreshing(false);
			}
		}, 3000);
	}

}
