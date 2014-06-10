package com.bookingSystem.widget;

import org.apache.http.protocol.HTTP;
import org.json.JSONArray;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;

/**
 * 解决 volley 返回json中文乱码
 * 
 * @参考 
 *     http://stackoverflow.com/questions/19267616/why-does-volleys-response-string
 *     -use-an-encoding-different-from-that-in-the-res
 * @author 钟买能
 * 
 */
public class JsonArrayUTF8Request extends JsonArrayRequest {

	public JsonArrayUTF8Request(String url, Listener<JSONArray> listener,
			ErrorListener errorListener) {
		super(url, listener, errorListener);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
		try {
			String type = response.headers.get(HTTP.CONTENT_TYPE);
			if (type == null) {
				type = "charset=UTF-8";
				response.headers.put(HTTP.CONTENT_TYPE, type);
			} else if (!type.contains("UTF-8")) {
				type += ";" + "charset=UTF-8";
				response.headers.put(HTTP.CONTENT_TYPE, type);
			}
		} catch (Exception e) {
			// print stacktrace e.g.
		}
		return super.parseNetworkResponse(response);
	}

}
