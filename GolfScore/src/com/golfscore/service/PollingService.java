package com.golfscore.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PollingService extends Service {  
	  
      
      
    private Notification mNotification;  
    private NotificationManager mManager;  
  
    @Override  
    public IBinder onBind(Intent intent) {  
        return null;  
    }  
  
//    PollingUtils.startPollingService(this, 30, PollingService.class, PollingService.ACTION);
//    PollingUtils.stopPollingService(this, PollingService.class, PollingService.ACTION);
    
    
    @Override  
    public void onCreate() {  
        
    }  
      
    @Override  
    public void onStart(Intent intent, int startId) {  
        new PollingThread().start();  
    }  
  
   
 
    class PollingThread extends Thread {  
        @Override  
        public void run() {  
        	//TODO
        }  
    }  
      
    @Override  
    public void onDestroy() {  
        super.onDestroy();  
        System.out.println("Service:onDestroy");  
    }  
  
}  