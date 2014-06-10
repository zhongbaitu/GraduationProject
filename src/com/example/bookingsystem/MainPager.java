package com.example.bookingsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bookingSystem.adapter.MyPagerAdapter;
import com.bookingSystem.adapter.SearchListViewAdapter;
import com.bookingSystem.effect.ZoomOutPageTransformer;
import com.bookingSystem.listener.MainListviewOnItemClickListener;

public class MainPager extends Activity {

	private ViewPager viewPager;
	private List<View> list;
	private View mainView; // 主界面
	private View commentView; // 评论/畅谈 界面
	private View bookView; // 查看自己的预约界面
	public static ActionBar actionBar;
	private RequestQueue rQueue;
	private Main main;
	private String city;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_viewpager);
		init();
		bind();
		takeIpLocation();
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		// Get the SearchView and set the searchable configuration
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		final SearchView searchView = (SearchView) menu.findItem(
				R.id.action_search).getActionView();

		searchView.setSubmitButtonEnabled(true);
		
		//没用的家伙
//		searchView.setOnCloseListener(new OnCloseListener() {
//			
//			@Override
//			public boolean onClose() {
//				main.gridView.setVisibility(0);
//				main.listView.setVisibility(8);
//				return false;
//			} 
//		});

		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
//				search();
				System.out.println("onQueryTextSubmit");
				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				main.listviewFlag=false;
				main.searchItem= new ArrayList<Map<String, Object>>();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("itemImage","http://pic23.nipic.com/20120913/7044160_135938632163_2.jpg");
				map.put("itemName", "啦啦啦饭馆");
				map.put("openTime", "");
				map.put("closeTime", "");
				map.put("food", "dsa");
				map.put("price", "2");
				main.searchItem.add(map);
				main.searchListViewAdapter = new SearchListViewAdapter(MainPager.this, main.searchItem);
				//main.listView重新设置适配器和监听并通知数据更新
				main.listView.setAdapter(main.searchListViewAdapter);
				main.listView.setOnItemClickListener(new MainListviewOnItemClickListener(MainPager.this, main.searchItem));
				main.searchListViewAdapter.notifyDataSetChanged();
				
				return true;
			}
		});

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		//跳转到Location Activity，并传city过去显示，那边不作定位
		case android.R.id.home:
			Intent intent=new Intent(this, Location.class);
			intent.putExtra("city", city);
			startActivity(intent);
			break;
		//搜索
		case R.id.action_search:
			//参考http://stackoverflow.com/questions/9327826/searchviews-oncloselistener-doesnt-work
			MenuItemCompat.setOnActionExpandListener(item, new OnActionExpandListener() {
				//SearchView的打开监控
				@Override
			    public boolean onMenuItemActionCollapse(MenuItem item) {
					//gridView显示
			    	main.gridView.setVisibility(0);
			    	//main.listView重新设置适配器和监听并通知数据更新
			    	main.listView.setAdapter(main.mainListViewAdapter);
			    	main.listView.setOnItemClickListener(new MainListviewOnItemClickListener(MainPager.this, main.item));   	
			    	main.searchListViewAdapter.notifyDataSetChanged();
			    	//恢复到记录的滚动行数
			    	main.listView.setSelection(main.mainListviewNum);
			    	//listviewFlag=true,继续记录行数
			    	main.listviewFlag=true;
			    	System.out.println("onMenuItemActionCollapse"+main.mainListviewNum);
			        return true;  // Return true to collapse action view
			    }
				//SearchView的关闭监控
			    @Override
			    public boolean onMenuItemActionExpand(MenuItem item) {
			    	main.gridView.setVisibility(8);
			    	main.listviewFlag=false;
			        return true;  // Return true to expand action view
			    }
			});
			break;
		case R.id.logout:
			Editor editor = sp.edit();
			editor.putString("username", "");
			editor.putString("password", "");
			editor.commit();
			startActivity(new Intent(this, Guide.class));
			finish();
			break;
		}
		

		return true;
	}

	@SuppressLint("NewApi")
	private void init() {
		list = new ArrayList<View>();
		viewPager = (ViewPager) this.findViewById(R.id.main_viewpager);
		mainView = getLayoutInflater().inflate(R.layout.main, null);
		commentView = getLayoutInflater().inflate(R.layout.comment_layout, null);
		bookView = getLayoutInflater().inflate(R.layout.order, null);
		list.add(commentView);
		list.add(mainView);
		list.add(bookView);

		main=new Main(this, mainView);
		new Comment(this, commentView);
		new Order(this,bookView);

		actionBar = getActionBar();
		actionBar.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.actionbar_background));
		actionBar.setTitle("未知");
		actionBar.setHomeButtonEnabled(true);
		actionBar.setIcon(R.drawable.ic_action_place);
		
		rQueue = Volley.newRequestQueue(this);
		
		sp = getSharedPreferences("remember", Context.MODE_APPEND);

	}

	private void bind() {
		viewPager.setAdapter(new MyPagerAdapter(list));
		viewPager.setCurrentItem(1);
		viewPager.setPageTransformer(false, new ZoomOutPageTransformer());
	}
	
//	private void search(){
//		String url = Common.SEARCH;
//		JSONObject infoObject = new JSONObject();
//		try {
//			infoObject.put("epname", "必胜客");
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		rQueue.add(jsonObjectRequest);
//		rQueue.start();
//	}
	
	//IP定位并显示在ActionBar
	@SuppressLint("NewApi")
	private void takeIpLocation() {
		String url = "http://api.map.baidu.com/location/ip?ak=Vud3j7cW7d66bptQaan84MB3&ip";
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						city = "未知";
						try {
							city = response.getJSONObject("content")
									.getJSONObject("address_detail")
									.getString("city");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						actionBar.setTitle(city);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {

					}
				});
		rQueue.add(jsonObjectRequest);
		rQueue.start();
	}
	

	
	

}