package com.example.bookingsystem;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.bookingSystem.widget.MyListView;
import com.example.bookingsystem.Main.MyHandler;

public class Order {

	private Context context;
	private View order;
	private static ImageView mapView;
	public static TextView number;
	private static RequestQueue rQueue;
	//此界面暂不显示菜单
//	private MyListView foodListView;
//	private ArrayAdapter foodAdapter;
	public static int bookedNo=-1;



	public Order(Context context, View order) {
		super();
		this.context = context;
		this.order = order;
		init();
		
	}

	private void init() {
		rQueue = Volley.newRequestQueue(context);
		mapView = (ImageView) order.findViewById(R.id.map_image);
		number=(TextView) order.findViewById(R.id.order_number);
//		foodListView = (MyListView) order.findViewById(R.id.food_listview);
//		foodAdapter = new ArrayAdapter(context,
//				android.R.layout.simple_list_item_1, new String[]{});
//		foodListView.setAdapter(foodAdapter);
	}
	
	public static void initBookedPanel(String x,String y){

		takeMap(x,y);
	}

	private static void takeMap(String x,String y) {
		//百度IP定位API
		String url = "http://api.map.baidu.com/staticimage?width=400&height=300&center="+x+","+y+"&zoom=14&markers="+x+","+y+"&markerStyles=l,0";
		ImageRequest imageRequest = new ImageRequest(url,
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap response) {
						mapView.setImageBitmap(response);
					}
				}, 0, 0, Config.RGB_565, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// imageView.setImageResource(R.drawable.default_image);
					}
				});
		rQueue.add(imageRequest);

		rQueue.start();
	}

	private String[] takeFoodList(String food) throws JSONException { 
		JSONArray jsonArray=new JSONArray(food);
		String[] strs = new String[jsonArray.length()];
		for(int i=0;i<jsonArray.length();i++){ 
			strs[i]=jsonArray.getString(i);
		}

		return strs;
 
	}
	
	

}
