package com.bookingSystem.adapter;

import java.util.List;
import java.util.Map;

import com.example.bookingsystem.Location;
import com.example.bookingsystem.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LocationAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, String>> list;
	private TextView title,content;
	
	public LocationAdapter(Context context,List<Map<String, String>> list) {
		super();
		this.context=context;
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
		View view = null;
		LayoutInflater inflater=LayoutInflater.from(context);
		
		if(list.get(position).get("type").equals("1")){
			view=inflater.inflate(R.layout.location__listview_title_style, null);
			title=(TextView) view.findViewById(R.id.location_title);
			title.setText(list.get(position).get("title"));
		}else{
			view=inflater.inflate(R.layout.location_listview_content_style, null);
			content=(TextView) view.findViewById(R.id.location_content);
			content.setText(list.get(position).get("title"));
		}
		
		return view;
	}

}
