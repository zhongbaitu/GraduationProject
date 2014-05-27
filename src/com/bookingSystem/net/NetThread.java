package com.bookingSystem.net;

import org.json.JSONObject;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bookingSystem.Myinterface.HandleRequest;

/**
 * Volley-用于高并发的网络访问
 * 
 * @author 钟买能
 * @version 1.0
 * @created 2014-5-16
 * 
 */
public class NetThread {
	private Context context;
	private HandleRequest handleRequest;
	private RequestQueue rQueue;
	private StringRequest stringRequest;
	private NetworkImageView netWorkImageView;

	public NetThread(Context context, HandleRequest handleRequest) {
		super();
		this.context = context;
		this.handleRequest = handleRequest;
		rQueue = Volley.newRequestQueue(context);
	}

	public void makeJsonObjectRequest(String url) {
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						handleRequest.handle(response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {

					}
				});
		rQueue.add(jsonObjectRequest);
	}

	public void start() {
		rQueue.start();
	}

}
