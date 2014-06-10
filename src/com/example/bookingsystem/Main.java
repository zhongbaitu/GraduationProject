package com.example.bookingsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bookingSystem.adapter.MainListViewAdapter;
import com.bookingSystem.adapter.SearchListViewAdapter;
import com.bookingSystem.listener.MainListviewOnItemClickListener;
import com.bookingSystem.service.CallingService;
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
	public GridView gridView;
	public static ListView listView;
	public List<Map<String, Object>> item;
	private ArrayList<HashMap<String, Object>> gridItem;
	private SwipeRefreshLayout swipeLayout;
	private RequestQueue queue;
	private SimpleAdapter listViewSimpleAdapter;
	public MainListViewAdapter mainListViewAdapter;
	public SearchListViewAdapter searchListViewAdapter;
	public List<Map<String, Object>> searchItem;
	public int mainListviewNum=0;
	/**
	 * listview标记，true:正常的listview，false:搜索状态的listview
	 */
	public boolean listviewFlag=true;
	private MyHandler handler=new MyHandler();
	private MySocketIO socketIO;
	public static boolean isBook=false;
	public static int bookedNum=-1;
	private boolean orderIsInit=false;

	

	public Main(Context context, View main) {
		super();
		this.main = main;
		this.context = context;
		init();
		takeInfo();
	}

	private void init() {
		gridView = (GridView) main.findViewById(R.id.main_gridview);

		//分类GridView的适配器
		SimpleAdapter gridViewSimpleAdapter = new SimpleAdapter(
				context,
				getGridViewData(),
				R.layout.classification_style,
				new String[] {"itemImage", "itemName" },
				new int[] {R.id.class_picture, R.id.class_text });
		gridView.setAdapter(gridViewSimpleAdapter);
		//listview的list
		item = new ArrayList<Map<String, Object>>();
		mainListViewAdapter = new MainListViewAdapter(context, item);
		listView = (ListView) main.findViewById(R.id.main_listview);
		listView.setAdapter(mainListViewAdapter);
		listView.setOnItemClickListener(new MainListviewOnItemClickListener(context, item));
		listView.setOnScrollListener(new MainListviewScrollListener());
		//下拉刷新布局
		swipeLayout = (SwipeRefreshLayout) main.findViewById(R.id.swipe_container);
		swipeLayout.setOnRefreshListener(this);
		swipeLayout.setColorScheme(
				android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_purple);
		//volley的队列
		queue = Volley.newRequestQueue(context);
		
		searchItem = new ArrayList<Map<String, Object>>();
		
		

	}

	// GridView 的数据
	private ArrayList<HashMap<String, Object>> getGridViewData() {
		gridItem = new ArrayList<HashMap<String, Object>>();

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage",context.getResources().getIdentifier("food0","drawable", context.getPackageName()));
			map.put("itemName",context.getResources().getString(R.string.food0));
			gridItem.add(map);
			
			HashMap<String, Object> map1 = new HashMap<String, Object>();
			map1.put("itemImage",context.getResources().getIdentifier("food1","drawable", context.getPackageName()));
			map1.put("itemName",context.getResources().getString(R.string.food1));
			gridItem.add(map1);
			
			HashMap<String, Object> map6 = new HashMap<String, Object>();
			map6.put("itemImage",context.getResources().getIdentifier("food6","drawable", context.getPackageName()));
			map6.put("itemName",context.getResources().getString(R.string.food6));
			gridItem.add(map6);
			
			HashMap<String, Object> map7 = new HashMap<String, Object>();
			map7.put("itemImage",context.getResources().getIdentifier("food7","drawable", context.getPackageName()));
			map7.put("itemName",context.getResources().getString(R.string.food7));
			gridItem.add(map7);
			
			HashMap<String, Object> map2 = new HashMap<String, Object>();
			map2.put("itemImage",context.getResources().getIdentifier("food2","drawable", context.getPackageName()));
			map2.put("itemName",context.getResources().getString(R.string.food2));
			gridItem.add(map2);
			
			HashMap<String, Object> map3 = new HashMap<String, Object>();
			map3.put("itemImage",context.getResources().getIdentifier("food3","drawable", context.getPackageName()));
			map3.put("itemName",context.getResources().getString(R.string.food3));
			gridItem.add(map3);
			
			HashMap<String, Object> map4 = new HashMap<String, Object>();
			map4.put("itemImage",context.getResources().getIdentifier("food4","drawable", context.getPackageName()));
			map4.put("itemName",context.getResources().getString(R.string.food4));
			gridItem.add(map4);

			HashMap<String, Object> map5 = new HashMap<String, Object>();
			map5.put("itemImage",context.getResources().getIdentifier("food5","drawable", context.getPackageName()));
			map5.put("itemName",context.getResources().getString(R.string.food5));
			gridItem.add(map5);
			


		return gridItem;
	}

	// Put 数据进 listview 的 list
	private List<Map<String, Object>> putListViewData(int id,String image,
			String name,String area,String x,String y, String openTime, String closeTime, String food,
			String price,String num) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("itemImage",image);
		map.put("itemName", name);
		map.put("area", area);
		map.put("x", x);
		map.put("y", y);
		map.put("openTime", openTime);
		map.put("closeTime", closeTime);
		map.put("food", food);
		map.put("price", price);
		map.put("num", num);
		item.add(map);

		return item;
	}

	// 向服务器请求数据
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
						swipeLayout.setRefreshing(false);
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {

					}
				});
		queue.add(jsonArrayRequest);
		queue.start();
	}

	// 服务器返回的结果处理
	private void resourceDo(JSONArray jsonArray) throws JSONException {
		for (int i = 0; i < jsonArray.length(); i++) {
//			jsonArray.getJSONObject(i).getString("EP_food")
			putListViewData(
					jsonArray.getJSONObject(i).getInt("id"),	
					jsonArray.getJSONObject(i).getString("image"),
					jsonArray.getJSONObject(i).getString("EP_name"),
					jsonArray.getJSONObject(i).getString("area"),
					jsonArray.getJSONObject(i).getString("x"),
					jsonArray.getJSONObject(i).getString("y"),
					jsonArray.getJSONObject(i).getString("EP_sdate"),
					jsonArray.getJSONObject(i).getString("EP_edate"),
					jsonArray.getJSONObject(i).getString("EP_food"),
					jsonArray.getJSONObject(i).getString("consumption"),
					"打烊");

				socketIO=new MySocketIO(
						handler,
						i,
						Common.LOCALHOST,
						jsonArray.getJSONObject(i).getInt("id"));

		}

	};

	// 下来刷新的处理
	@Override 
	public void onRefresh() {
		// TODO Auto-generated method stub
		takeInfo();

	}
	
	class MyHandler extends Handler
	{	

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what)
			{
			case 1:
			  {
				  Log.v("socket", "连接成功");
				  break;
			  }
			case 2:
	    		{
	    		 
	    		 Bundle bundle=msg.getData();

	    		 int num = bundle.getInt("num");
	    		 item.get(num).put("num", bundle.getInt("no")+"");
	    		 mainListViewAdapter.notifyDataSetChanged();
	    		 
	    		 //初始化order页面，并记录下预约得到的号数
				if(!RestaurantItem.orderIsInit && Main.bookedNum==bundle.getInt("id")){
					Order.number.setText(bundle.getInt("no")+"");
					Order.bookedNo=bundle.getInt("no");
					RestaurantItem.orderIsInit=true;
					
					//启动 Service
					Intent intent=new Intent(context, CallingService.class);
					intent.putExtra("id", bundle.getInt("no"));
					intent.putExtra("no", bundle.getInt("id"));
					context.startService(intent);
				}
 
	    		 
				 break;
		    	}
	    		
			}
			
		}

	}
	
	class MainListviewScrollListener implements OnScrollListener {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			//是正常状态的listview，则记录当前滚动行数
			if(listviewFlag){
				mainListviewNum=firstVisibleItem;
			}
			
			System.out.println("firstVisibleItem:"+mainListviewNum);
		}

	}

}
