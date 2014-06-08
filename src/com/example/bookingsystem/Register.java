package com.example.bookingsystem;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
/**
 * 注册界面
 * @author 钟买能
 * @version 1.0
 * @created 2014-5-16
 *
 */
public class Register extends Activity{
	
	private EditText registerUsername,registerPassword,registerCheckPassword;
	private Button registerSureButton;
	private String url;
	private RequestQueue queue;
	private String usernameString,passwordString,repasswordString;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		setContentView(R.layout.register);
		
		init();
		bind();
	}
	
	private void init(){
		queue=Volley.newRequestQueue(this);
		registerUsername=(EditText) this.findViewById(R.id.register_username);
		registerPassword=(EditText) this.findViewById(R.id.register_password);
		registerCheckPassword=(EditText) this.findViewById(R.id.register_check_password);
		registerSureButton=(Button) this.findViewById(R.id.register_sure_button);
		sp=this.getSharedPreferences("remember", Context.MODE_APPEND);
		
	}
	
	private void bind(){
		registerSureButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				usernameString=registerUsername.getText().toString();
				passwordString=registerPassword.getText().toString();
				repasswordString=registerCheckPassword.getText().toString();
				if(usernameString.equals("")){
					Toast.makeText(Register.this, "帐号不能为空", Toast.LENGTH_SHORT).show();
				}else if(passwordString.equals("")){
					Toast.makeText(Register.this, "密码不能为空", Toast.LENGTH_SHORT).show();
				}else if(!passwordString.equals(repasswordString)){
					Toast.makeText(Register.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
				}else{
					takeRegister();
				}
				
				
			}
		});
	}
	
	private void takeRegister(){
		JSONObject infoObject=new JSONObject();
		try {
			infoObject.put("userName", usernameString);
			infoObject.put("password", passwordString);
			infoObject.put("tel", "123");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String url=Common.REGISTER;
		JsonObjectRequest registerRequest=new JsonObjectRequest(
				Request.Method.POST,
				url, 
				infoObject,
				new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject jsonObject) {
						responesDo(jsonObject);
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub
						
					}
				});
		queue.add(registerRequest);
		queue.start();
		
	}
	
	private void responesDo(JSONObject jsonObject){
		try {
			if (jsonObject.getString("result").equals("OK")) {
				
				Editor editor=sp.edit();
				editor.putString("username", usernameString);
				editor.putString("password", passwordString);
				editor.commit();
				
				startActivity(new Intent(Register.this,
						MainPager.class));
				//把 Guide 关掉
				Guide.guide.finish();
				finish();	
			}else{
				Toast.makeText(Register.this, "系统异常", Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			finish();
			overridePendingTransition(R.anim.blank,
					R.anim.right_push_out);
		}
		return true;
	}


}
