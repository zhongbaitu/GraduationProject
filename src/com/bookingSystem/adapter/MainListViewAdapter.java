package com.bookingSystem.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.bookingSystem.widget.BitmapCache;
import com.example.bookingsystem.R;

public class MainListViewAdapter extends BaseAdapter {

	private List<Map<String, String>> list;
	private LayoutInflater inflater;
	private Context context;

	public MainListViewAdapter(Context context, List<Map<String, String>> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.main_listview_style, null);
			holder.name = (TextView) convertView
					.findViewById(R.id.restaurant_name);
			holder.price = (TextView) convertView.findViewById(R.id.price);
			holder.image = (NetworkImageView) convertView
					.findViewById(R.id.restaurant_image);
			holder.image.setDefaultImageResId(R.drawable.ic_launcher);
			holder.image.setErrorImageResId(R.drawable.food2);
			holder.queue = Volley.newRequestQueue(context);
			holder.imageLoader = new ImageLoader(holder.queue,
					new BitmapCache());
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.name.setText(list.get(position).get("itemName"));
		holder.price.setText("ÈË¾ù£º£¤" + list.get(position).get("price"));
		holder.image.setImageUrl(
				"http://pic23.nipic.com/20120913/7044160_135938632163_2.jpg",
				holder.imageLoader);

		return convertView;
	}

	private class Holder {
		private TextView name, price;
		private NetworkImageView image;
		private ImageLoader imageLoader;
		private RequestQueue queue;
	}

}
