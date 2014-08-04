package com.golfscore.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.golfscore.application.BaseController;
/**
 * 数据库操作类
 * @author dw
 *
 */
public class DbHelper
	{
		private DatabaseHelper mDbHelper;    //SQLiteOpenHelper实例对象
	    public SQLiteDatabase mDb;    //数据库实例对象
	    private static DbHelper openHelper = null;//数据库调用实例
	    
		private static int version = 1;//数据库版本号
		  
		private static String myDBName = "golfscore";
		private static String TableNames[];//表名
		private static String FieldNames[][];//字段名
		private static String FieldTypes[][];//字段类型
		private static String NO_CREATE_TABLES = "no tables";
		private static String message = "";
		private static Integer  count = 0;  //打开的数据库连接数
		
		private final Context mCtx;    //上下文实例
		
		private DbHelper(Context ctx) {
		        this.mCtx = ctx;
		}
		/*判断数据库实例是否存在*/
		public static boolean hasInstance(){
			if(openHelper == null){
				return false;
			}
			return true;
		}
		/*获得数据库实例*/
		public static DbHelper getInstance(){
			return openHelper;
		}
		/*初始化时获得数据库实例*/
		public static DbHelper getInstance(Context context,DbInfo dbInfo){
			if (openHelper == null) {
				openHelper = new DbHelper(context);
				TableNames = dbInfo.getTableNames();
				FieldNames = dbInfo.getFieldNames();
				FieldTypes = dbInfo.getFieldTypes();
				BaseController.db = openHelper;
			}
			return openHelper;
		}
		
		private static class DatabaseHelper extends SQLiteOpenHelper {    //数据库辅助类
		        
			DatabaseHelper(Context context) {
	            super(context, myDBName, null, version);
	        }
	        @Override
		  	public void onCreate(SQLiteDatabase db)
		  	{
		  	    if (TableNames == null)
		  	    {
		  	    	message = NO_CREATE_TABLES;
		  	    	return;
		  	    }
		  	    for (int i = 0; i < TableNames.length; i++)
		  	    {
		  	    	String sql = "CREATE TABLE " + TableNames[i] + " (";
		  	    	for (int j = 0; j < FieldNames[i].length; j++)
		  	    	{
		  	    		sql += FieldNames[i][j] + " " + FieldTypes[i][j] + ",";
		  	    	}
		  	    	sql = sql.substring(0, sql.length() - 1);
		  	    	sql += ")";
		  	    	db.execSQL(sql);
		  	    } 
		  	}
		  	@Override
		  	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2)
		  	{
		  		for (int i = 0; i < TableNames[i].length(); i++)
		  	    {
		  			String sql = "DROP TABLE IF EXISTS " + TableNames[i];
		  			db.execSQL(sql);
		  	    }
		  		onCreate(db);
		  	}
		}
		
		/**添加数据库相关信息*/
		public void insertTables(String[] tableNames,String[][] fieldNames,String[][] fieldTypes){
			TableNames = tableNames;
			FieldNames = fieldNames;
			FieldTypes = fieldTypes;
		}
	  
		/**打开数据库*/
		public DbHelper open() throws SQLException {
			synchronized (count) {
				count++;
				 if (count == 1) {
		        	 mDbHelper = new DatabaseHelper(mCtx);
		 	        mDb = mDbHelper.getWritableDatabase();
		        }
		        return this;
			}
	       
	    }
	    
		/**关闭数据库*/
	    public void close() {
	    	synchronized (count) {
				count--;
				if (count == 0) {
		    		mDbHelper.close();
		    		mDbHelper = null;
		    	}else if(count < 0){
		    		count = 0;
		    	}
			}
	    	
	    }
	
		public void execSQL(String sql) throws java.sql.SQLException
		{
			mDb.execSQL(sql);
		}
		
		/**sql语句查询数据*/
		public Cursor rawQuery(String sql,String[] selectionArgs)
		{
			Cursor cursor = mDb.rawQuery(sql, selectionArgs);
			return cursor;
		}
	
		/**查询数据*/
		public Cursor select(String table, String[] columns, 
				String selection, String[] selectionArgs, String groupBy, 
				String having, String orderBy)
		{
			Cursor cursor = mDb.query
			(
					table, columns, selection, selectionArgs, 
					groupBy, having, orderBy
			);
			return cursor;
		}
	
		/**添加数据*/
		public long insert(String table, String fields[], String values[])
		{
			ContentValues cv = new ContentValues();
			for (int i = 0; i < fields.length; i++)
			{
				cv.put(fields[i], values[i]);
			}
			return mDb.insert(table, null, cv);
		}
	
		/**删除数据*/
		public int delete(String table, String where, String[] whereValue)
		{
			return mDb.delete(table, where, whereValue);
		}
	
		/**更新数据*/
		public int update(String table, String updateFields[],
				String updateValues[], String where, String[] whereValue)
		{
			ContentValues cv = new ContentValues();
			for (int i = 0; i < updateFields.length; i++)
			{
				cv.put(updateFields[i], updateValues[i]);
			}
			return mDb.update(table, cv, where, whereValue);
		}
	
		/**错误信息： 不为null数据库未建立*/
		public String getMessage()
		{
			return message;
		}
	
	  
}

