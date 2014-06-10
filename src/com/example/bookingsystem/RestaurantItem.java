package com.example.bookingsystem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ClipData.Item;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.bookingSystem.widget.BitmapCache;
import com.bookingSystem.widget.MyListView;

public class RestaurantItem extends Activity {

	private TextView restaurantName, priceText;
	private Intent intent;
	private ImageView mapView;
	private NetworkImageView restaurantImage;
	private ActionBar actionBar;
	private MyListView foodListView;
	private Item collection;
	private RequestQueue rQueue;
	private ImageLoader imageLoader;
	private String itemImage,name,price,food,area,x,y;
	private int id;
	private OrderHandler handler=new OrderHandler();
	private MySocketIO socketIO;
	public static boolean orderIsInit=false;
//	public static int bookerId=-1;
	

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
//		collection=(Item) menu.findItem(R.id.collection);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) { 
		case android.R.id.home:
			finish();
			overridePendingTransition(R.anim.blank, R.anim.right_push_out);
			break; 
		case R.id.book_button:
			if(!Main.isBook){
	            JSONObject json = new JSONObject();
	            try {
	            	//put企业id，进行匹配
					json.put("id", id);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace(); 
				}
	            //发送socket给服务器，再由服务器转发给企业网页端
				MySocketIO.socket.emit("message", json);
				Main.isBook=true;
				Main.bookedNum=id;
				
				socketIO=new MySocketIO(
						handler,
						-1,
						Common.LOCALHOST,
						id);
				Order.initBookedPanel( x, y);
				Toast.makeText(this, "预定成功", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this, "抱歉！目前只能预约一个餐厅", Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.collection:
			item.setIcon(R.drawable.ic_action_important);
			break;

		}
		return true;
	}
 
	@SuppressLint("NewApi")
	private void init() {
		//获取main传过来的intent 
		intent = getIntent();
		//企业ID
		id=intent.getIntExtra("id", 1);
		//餐厅的名字
		name = intent.getStringExtra("name");
		//地区
		area=intent.getStringExtra("area");
		//x坐标
		x=intent.getStringExtra("x");
		//y坐标
		y=intent.getStringExtra("y");
		//价格
		price = intent.getStringExtra("price");
		//菜牌
		food=intent.getStringExtra("food");
		//顶部的图片
		itemImage=intent.getStringExtra("itemImage");
		
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
		
		restaurantImage=(NetworkImageView) this.findViewById(R.id.restaurant_item_image);
		imageLoader = new ImageLoader(rQueue,new BitmapCache());
		
		foodListView = (MyListView) this.findViewById(R.id.food_listview);
		ArrayAdapter foodAdapter = null;
		try {
			foodAdapter = new ArrayAdapter(this,
					android.R.layout.simple_list_item_1, takeFoodList(food));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		foodListView.setAdapter(foodAdapter);
	}

	// 下载地图
	private void takeMap() {
		restaurantImage.setImageUrl(itemImage, imageLoader);
		String url = "http://api.map.baidu.com/staticimage?width=400&height=300&center="+x+","+y+"&zoom=14&markers="+x+","+y+"&markerStyles=l,0";
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

	private String[] takeFoodList(String food) throws JSONException { 
		JSONArray jsonArray=new JSONArray(food);
		String[] strs = new String[jsonArray.length()];
		for(int i=0;i<jsonArray.length();i++){ 
			strs[i]=jsonArray.getString(i);
		}

		return strs;
 
	}
	
	
	class OrderHandler extends Handler
	{	

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what)
			{
			case 1:
			  {
				  	Bundle bundle=msg.getData();
		    		 //初始化order界面的号码

//			    		 int num = bundle.getInt("no");
//			    		 Log.v("test", num+"###");
//			    		 Order.number.setText(num+"");
//		    			 orderIsInit=true;

//		    		 
//		    		 String num = bundle.getString("no");
//		    		 Order.number.setText(num);
			  }
	    		
			}
			
		}
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
