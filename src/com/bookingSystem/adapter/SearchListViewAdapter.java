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

public class SearchListViewAdapter extends BaseAdapter {

	private List<Map<String, Object>> list;
	private LayoutInflater inflater;
	private Context context;

	public SearchListViewAdapter(Context context, List<Map<String, Object>> list) {
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
			convertView = inflater.inflate(R.layout.search_listview_style, null);
			holder.name = (TextView) convertView.findViewById(R.id.searchview_text);
//			holder.queue = Volley.newRequestQueue(context);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.name.setText((CharSequence) list.get(position).get("itemName"));

		return convertView;
	}

	private class Holder {
		private TextView name;
		private RequestQueue queue;
	}

}
