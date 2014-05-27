package com.example.bookingsystem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

public class Order {

	private Context context;
	private View order;
	private ImageView mapView;
	private RequestQueue rQueue;
	private ListView foodListView;

	public Order(Context context, View order) {
		super();
		this.context = context;
		this.order = order;
		init();
		takeMap();
	}

	private void init() {
		rQueue = Volley.newRequestQueue(context);
		mapView = (ImageView) order.findViewById(R.id.map_image);

		foodListView = (ListView) order.findViewById(R.id.food_listview);
		ArrayAdapter foodAdapter = new ArrayAdapter(context,
				android.R.layout.simple_list_item_1, takeFoodList());
		foodListView.setAdapter(foodAdapter);
	}

	private void takeMap() {
		String url = "http://api.map.baidu.com/staticimage?width=400&height=300&center=113.451082,23.386214&zoom=14&markers=113.451082,23.386214&markerStyles=l,0";
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

	private String[] takeFoodList() {
		String[] strs = { "∑¨ Ì", "∫Ï Ì", " ≤√¥ È" };
		return strs;

	}
}
