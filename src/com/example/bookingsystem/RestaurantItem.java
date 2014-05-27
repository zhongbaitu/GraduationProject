package com.example.bookingsystem;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

public class RestaurantItem extends Activity {

	private TextView restaurantName, priceText;
	private Intent intent;
	private ImageView mapView;
	private NetworkImageView restaurantImage;
	private RequestQueue rQueue;
	private ActionBar actionBar;
	private ListView foodListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_restaurant_layout);

		init();
		takeMap();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.restauran_item_menu, menu);
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			overridePendingTransition(R.anim.blank, R.anim.right_push_out);
			break;

		}
		return true;
	}

	@SuppressLint("NewApi")
	private void init() {
		intent = getIntent();
		String name = intent.getStringExtra("name");
		String price = intent.getStringExtra("price");
		restaurantName = (TextView) this
				.findViewById(R.id.restaurant_item_name);
		priceText = (TextView) this.findViewById(R.id.price);
		restaurantName.setText(name);
		priceText.setText("人均：￥" + price);
		rQueue = Volley.newRequestQueue(this);
		mapView = (ImageView) this.findViewById(R.id.map_image);

		actionBar = getActionBar();
		actionBar.setBackgroundDrawable(this.getResources().getDrawable(
				R.drawable.actionbar_background));
		actionBar.setHomeButtonEnabled(true);
		actionBar.setIcon(R.drawable.ic_action_back);
		actionBar.setTitle("");

		foodListView = (ListView) this.findViewById(R.id.food_listview);
		ArrayAdapter foodAdapter = new ArrayAdapter(this,
				android.R.layout.simple_list_item_1, takeFoodList());
		foodListView.setAdapter(foodAdapter);
	}

	// 下载地图
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
		String[] strs = { "番薯", "红薯", "什么书" };
		return strs;

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.blank, R.anim.right_push_out);
		}
		return true;
	}

}
