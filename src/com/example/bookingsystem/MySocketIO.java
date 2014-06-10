package com.example.bookingsystem;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.bookingsystem.RestaurantItem.OrderHandler;

public class MySocketIO implements IOCallback  { 

	public static SocketIO socket;

	private Handler mhandler;
	private OrderHandler orderHandler;
	/**
	 * 对应listview的条数
	 */
	private int num;
	/**
	 * 企业id
	 */
	private int flag;
	
	//构造函数
	public MySocketIO( Handler handler,int num,String url,int flag)
	{
		this.mhandler=handler;
		this.num=num;
		this.flag=flag;
		socket = new SocketIO();
		try { 
			
			socket.connect(url, this);
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void onDisconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessage(String data, IOAcknowledge ack) {
		System.out.println("Server said: " + data);
		
	}

	@Override
	public void onMessage(JSONObject json, IOAcknowledge ack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void on(String event, IOAcknowledge ack, Object... args) {
		// TODO Auto-generated method stub
		if(event.equals("welcome"))
		{
			Message msg=new Message();
			msg.what=1;
			mhandler.sendMessage(msg);
		}
		if(event.endsWith("serverBroadcast"))
		{
			JSONObject object=null;
			if(args.length>0)
			{
				
				 try {
					object=new JSONObject(args[0].toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			Message msg=new Message();
			msg.what=2;
			Bundle bundel=new Bundle();
			int checkFlag = -1;
			try {
				checkFlag=object.getInt("id");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(checkFlag==flag){
				try {

					bundel.putInt("id", object.getInt("id"));
					bundel.putInt("no", object.getInt("no"));
					bundel.putInt("num", num);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//把接收到的数据，通过事件，发送给主界面
				msg.setData(bundel);
				mhandler.sendMessage(msg);
			}
			
		}
		//order的socket
		else if(event.endsWith("nowNumber") && Main.bookedNum!=-1){
			Log.v("nowNumber", "nowNumber");
			JSONObject object=null;
			if(args.length>0)
			{
				
				 try {
					object=new JSONObject(args[0].toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			int checkFlag = -1 ;
			try {
				checkFlag=object.getInt("id");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(checkFlag==(flag)){
				Message msg=new Message();
				msg.what=1;
				Bundle bundel=new Bundle();
				try {
					bundel.putInt("no", object.getInt("no"));
					Log.v("test", object.getInt("no")+"!!!");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				msg.setData(bundel);
				mhandler.sendMessage(msg);
			}

		}
		
	}

	@Override
	public void onError(SocketIOException socketIOException) {
		// TODO Auto-generated method stub
		
	}

}
