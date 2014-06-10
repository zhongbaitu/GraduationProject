package com.bookingSystem.service;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.bookingsystem.Common;
import com.example.bookingsystem.MainPager;
import com.example.bookingsystem.R;
 
public class CallingService extends IntentService {
	
	private MySocketIO socket;
	long vT[]={300,100,300,100};
	int id, no;
	
	public CallingService() {
	    super("CallingService");
	}

	public CallingService(String name) {
		super(name);
		
	}
	
    @Override
    public IBinder onBind(Intent intent) { 
        Log.d("test", "onBind()"); 
        return super.onBind(intent); 
    } 
  
    @Override
    public void onCreate() { 
        Log.d("test", "onCreate()"); 
        super.onCreate(); 
    } 
  
    @Override
    public void onDestroy() { 
        Log.d("test", "onDestroy()"); 
        super.onDestroy(); 
    } 
  
    @Override
    public void onStart(Intent intent, int startId) { 
        Log.d("test", "onStart()"); 
        super.onStart(intent, startId); 
    } 
  
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) { 
        Log.d("test", "onStartCommand()"); 
        return super.onStartCommand(intent, flags, startId); 
    } 

	@Override 
	protected void onHandleIntent(Intent intent) {
		
		no = intent.getIntExtra("no", -1);
        id = intent.getIntExtra("id", -1);
        socket=new MySocketIO(Common.LOCALHOST, id);
        initNotification(no);
	}
	
	/**
	 * 
	 * @param Myno �õ��ĺ���
	 * @param nowNo ���ڽеĺ���
	 */
	@SuppressLint("NewApi")
	private void initNotification(int nowNo){
		
	Intent resultIntent = new Intent(this, MainPager.class);
	

	TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
	
	stackBuilder.addNextIntent(resultIntent);

	PendingIntent resultPendingIntent = 
	                stackBuilder.getPendingIntent(
	                    0,
	                    PendingIntent.FLAG_UPDATE_CURRENT
	                );

	NotificationCompat.Builder mBuilder =           //Notification �ļ�����
	
	                new NotificationCompat.Builder(this)
	
	                .setSmallIcon(R.drawable.ic_launcher)   // ��û������ largeicon����Ϊ��ߵĴ� icon�������� largeicon����Ϊ���½ǵ�С icon��������������Ӱ�� Notifications area ��ʾ��ͼ��
	
	                .setContentTitle("���ĺ�����"+no) // ����
	
	                .setContentText("�ֽк�����"+nowNo)         // ����
	
	                .setOngoing(true)      //true ʹ notification ��Ϊ ongoing���û������ֶ���������� QQ,false ���߲�������Ϊ��ͨ��֪ͨ
	                
	                .setContentIntent(resultPendingIntent);      
	
	if(no==nowNo){
		mBuilder.setVibrate(vT) ; //��
		mBuilder.setDefaults(Notification.DEFAULT_SOUND);// ������������ΪĬ������
		mBuilder.setAutoCancel(true);// ���֮���Զ���ʧ
	}
				
	
	NotificationManager mNotificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	
	mNotificationManager.notify(1000, mBuilder.build());


		}
	
	class MySocketIO implements IOCallback  { 

		public SocketIO socket;

		/**
		 * ��ҵid
		 */
		private int flag;
		
		//���캯��
		public MySocketIO( String url,int flag)
		{
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
			if(event.endsWith("nowNumber") ){
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
					try {
						
						initNotification(object.getInt("no"));
						Log.v("test", object.getInt("no")+"");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
			
		}

		@Override
		public void onError(SocketIOException socketIOException) {
			// TODO Auto-generated method stub
			
		}

	}

}
