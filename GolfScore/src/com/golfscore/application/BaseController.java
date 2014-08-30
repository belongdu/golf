package com.golfscore.application;

import java.lang.Thread.UncaughtExceptionHandler;
import java.sql.SQLException;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.golfscore.activity.LoginActivity;
import com.golfscore.db.DbHelper;
import com.golfscore.db.DbInfo;


public class BaseController extends Application implements UncaughtExceptionHandler{

	
	public static DbHelper db = null;
	
	private static DbHelper appdbhelper = null;
	@Override
	public void onCreate() {
		
		super.onCreate();
		
		
		initDb();
	}

	
	// 初始化数据库
	private  boolean initDb() {
		
		// 建立数据库
		appdbhelper = DbHelper.getInstance(getApplicationContext(), new DbInfo());
		appdbhelper.open();

		Cursor cursor = appdbhelper.select("paramTable", new String[] { "paraName"}, null, null, null, null, null);

		// 判断数据库是否已经初始化过
		if (cursor.moveToNext()) {
			cursor.close();
			appdbhelper.close();
			return true;
		}
		// 初始化数据库
		try {
			
			// 初始化参数表
			appdbhelper.execSQL("insert into paramTable(_ID,paraName,paraValue,paraExplain) values ('1','INIT','是','是否初始化过')");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		appdbhelper.close();
		
		return true;
	}


	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		Intent intent = new Intent(this.getApplicationContext(), LoginActivity.class);  
        PendingIntent restartIntent = PendingIntent.getActivity(    
                this.getApplicationContext(), 0, intent,    
                Intent.FLAG_ACTIVITY_NEW_TASK);                                                 
        //退出程序                                          
        AlarmManager mgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);    
        mgr.set(AlarmManager.RTC, System.currentTimeMillis(),    
                restartIntent); // 1秒钟后重启应用   
        
	}
	
}
