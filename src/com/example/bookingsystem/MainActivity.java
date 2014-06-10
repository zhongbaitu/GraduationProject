package com.example.bookingsystem;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sp = this.getSharedPreferences("remember", Context.MODE_APPEND);
		if (sp.getString("username", "").equals("")) {
			startActivity(new Intent(this, Guide.class));
			
		} else {
//			Log.v("test", sp.getString("username", "")+"!!");
			startActivity(new Intent(this, MainPager.class));
		}
		finish();
	}
 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
