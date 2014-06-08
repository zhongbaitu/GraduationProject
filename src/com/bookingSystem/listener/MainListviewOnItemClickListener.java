package com.bookingSystem.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.bookingsystem.R;
import com.example.bookingsystem.RestaurantItem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

public class MainListviewOnItemClickListener implements OnItemClickListener {

	private Context context;
	private List<Map<String, String>> item;
	
	public MainListviewOnItemClickListener(Context context,
			List<Map<String, String>> item) {
		super();
		this.context=context;
		this.item = item;
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long itemId) {

		Intent intent = new Intent(context, RestaurantItem.class);
		intent.putExtra("name", item.get(position).get("itemName").toString());
//		intent.putExtra("image", name);
		intent.putExtra("price", name);
		context.startActivity(intent); 
		((Activity) context).overridePendingTransition(R.anim.right_push_in,
				R.anim.blank);
	}

}
