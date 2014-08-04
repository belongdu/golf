package com.golfscore.application;

import java.sql.SQLException;

import android.app.Application;
import android.database.Cursor;

import com.golfscore.db.DbHelper;
import com.golfscore.db.DbInfo;


public class BaseController extends Application{

	
	public static DbHelper db = null;
	
	private static DbHelper appdbhelper = null;
	@Override
	public void onCreate() {
		
		super.onCreate();
		
		
		initDb();
	}

	
	// ��ʼ�����ݿ�
	private  boolean initDb() {
		
		// �������ݿ�
		appdbhelper = DbHelper.getInstance(getApplicationContext(), new DbInfo());
		appdbhelper.open();

		Cursor cursor = appdbhelper.select("paramTable", new String[] { "paraName"}, null, null, null, null, null);

		// �ж����ݿ��Ƿ��Ѿ���ʼ����
		if (cursor.moveToNext()) {
			cursor.close();
			appdbhelper.close();
			return true;
		}
		// ��ʼ�����ݿ�
		try {
			
			// ��ʼ��������
			appdbhelper.execSQL("insert into paramTable(_ID,paraName,paraValue,paraExplain) values ('1','INIT','��','�Ƿ��ʼ����')");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		appdbhelper.close();
		
		return true;
	}
	
}
