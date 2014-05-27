package com.example.bookingsystem;

import java.lang.reflect.Field;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

/**
 * 登录对话框
 * 
 * @author 钟买能
 * @version 1.0
 * @created 2014-5-16
 */
public class Login {

	private Context context;
	private EditText username, password;
	private AlertDialog.Builder build;
	private RequestQueue rQueue;
	private SharedPreferences sp;

	public Login(Context context) {
		this.context = context;
		init();
		bind();
		build.create().show();
	}

	private void init() {
		rQueue = Volley.newRequestQueue(context);
		build = new AlertDialog.Builder(context);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.login_dialog, null);
		build.setView(view);
		build.setTitle("登录");
		username = (EditText) view.findViewById(R.id.login_dialog_username);
		password = (EditText) view.findViewById(R.id.login_dialog_password);
		sp = context.getSharedPreferences("remember", Context.MODE_APPEND);

	}

	private void bind() {
		build.setNegativeButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				takeLogin();
			}
		});
		build.setPositiveButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void takeLogin() {
		String url = Common.LOGIN;
		JSONObject infoObject = new JSONObject();
		try {
			infoObject.put("userName", username.getText().toString());
			infoObject.put("password", password.getText().toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.POST, url, infoObject,

				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject jsonObject) {
						responseDo(jsonObject);

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {

					}
				});
		rQueue.add(jsonObjectRequest);
		rQueue.start();
	}

	private void responseDo(JSONObject jsonObject) {
		try {
			if (jsonObject.getString("result").equals("OKla")) {

				Editor editor = sp.edit();
				editor.putString("username", username.getText().toString());
				editor.putString("password", password.getText().toString());
				editor.commit();

				context.startActivity(new Intent(context, MainPager.class));
				Guide.guide.finish();
			} else {
				Toast.makeText(context, "帐号或密码错误", Toast.LENGTH_SHORT).show();

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
