package com.example.bookingsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bookingSystem.adapter.LocationAdapter;
import com.bookingSystem.listener.LocationListViewOnItemClickListener;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
/**
 * 定位或选择地区的activity
 * @author 钟买能
 * @version 1.0
 * @since 2014-5-24
 *
 */
public class Location extends Activity {
	
	private ListView listview;
	private ActionBar actionBar;
	private List<Map<String, String>> list;
	private RequestQueue rQueue;
	private LocationAdapter locationAdapter;
	/**
	 * 单一静态实例
	 */
	public static Location location; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location);
		location=this;
		init();
		takeIpLocation();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater=getMenuInflater();
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
	
	private void takeIpLocation(){
		String url="http://api.map.baidu.com/location/ip?ak=Vud3j7cW7d66bptQaan84MB3&ip";
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,
				null, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						String city = "未知";
						try {
							city=response.getJSONObject("content").getJSONObject("address_detail").getString("city");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						Map<String, String> map2=new HashMap<String, String>();
						map2.put("type", "2");
						map2.put("place", city);
						list.add(map2);
						
						locationAdapter.notifyDataSetChanged();  
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {

					}
				});
		rQueue.add(jsonObjectRequest);
		rQueue.start();
	}

	@SuppressLint("NewApi")
	private void init(){
		listview=(ListView) this.findViewById(R.id.location_listview);
		list=new ArrayList<Map<String,String>>();
		locationAdapter=new LocationAdapter(Location.this, initData());
		listview.setAdapter(locationAdapter);
		listview.setOnItemClickListener(new LocationListViewOnItemClickListener(list));
		
		rQueue = Volley.newRequestQueue(this);
		
		actionBar=getActionBar();
		actionBar.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.actionbar_background));
		actionBar.setHomeButtonEnabled(true);
		actionBar.setIcon(R.drawable.ic_action_back);
		actionBar.setTitle("");
	}
	
	private List<Map<String, String>> initData(){
		Map<String, String> map=new HashMap<String, String>();
		map.put("type", "1");
		map.put("title", "根据定位：");
		list.add(map);

		return list;
		
	}

}
