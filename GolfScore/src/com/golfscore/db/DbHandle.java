package com.golfscore.db;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.database.Cursor;
import android.util.Log;


public class DbHandle {
	private DbHelper db;
	private Object obj = new Object();

	/**
	 * 可执行除了查询，其他的语句
	 * 
	 * @param sql
	 *            完整的sql语句
	 * @throws SQLException
	 */
	public void execSQL(String sql) throws SQLException {

		synchronized (obj) {

			try{
				db = DbHelper.getInstance();
				db.open();

				db.execSQL(sql);

			}catch(Exception ex){
				Log.i("db Exception", " db error");
				ex.printStackTrace();
			}finally{
				if(db != null){
					db.close();
				}
			}
			
		}
	}

	/**
	 * sql语句查询数据
	 * 
	 * 查询数据,当查询结果有多条时，可使用该方法 每一条数据为一个map。 map中的key是列名，value是对应的值
	 * 
	 * 
	 * 
	 * 
	 * @param sql
	 *            包含占位符的查询语句 例如 select * from tab1 where name = ? and age = ?
	 * @param selectionArgs
	 *            占位符对应的参数 例如 new String[]{"zhang","21"}
	 * @return
	 */
	public List<Map<String, String>> rawQuery(String sql, String[] selectionArgs) {
		synchronized (obj) {try{

			db = DbHelper.getInstance();
			db.open();
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			Cursor cursor = db.rawQuery(sql, selectionArgs);

			while (cursor != null && cursor.moveToNext()) {
				Map<String, String> map = new HashMap<String, String>();
				for (int i = 0; i < cursor.getColumnCount(); i++) {
					if (cursor.getString(i) != null) {
						map.put(cursor.getColumnName(i), cursor.getString(i));
					}
				}
				list.add(map);
			}
			cursor.close();

			return list;
		
		}catch(Exception ex){
			Log.i("db Exception", " db error");
			ex.printStackTrace();
			return null;
		}finally{
			if(db != null){
				db.close();
			}
		}
	   }
	}

	/**
	 * sql语句查询数据
	 * 
	 * 查询数据,当查询结果为一条时，可使用该方法 查询结果为map。map中的key是列名，value是对应的值
	 * 
	 * 
	 * 
	 * @param sql
	 *            包含占位符的查询语句 例如 select * from tab1 where name = ? and age = ?
	 * @param selectionArgs
	 *            占位符对应的参数 例如 new String[]{"zhang","21"}
	 * @return
	 */
	public Map<String, String> rawQueryOneRecord(String sql,
			String[] selectionArgs) {
		synchronized (obj) {try{

			db = DbHelper.getInstance();
			db.open();
			Map<String, String> map = new HashMap<String, String>();
			Cursor cursor = db.rawQuery(sql, selectionArgs);

			if (cursor != null && cursor.moveToFirst()) {
				for (int i = 0; i < cursor.getColumnCount(); i++) {
					if (cursor.getString(i) != null) {
						map.put(cursor.getColumnName(i), cursor.getString(i));
					}
				}
			}
			cursor.close();
			return map;
		
		}catch(Exception ex){
			Log.i("db Exception", " db error");
			ex.printStackTrace();
			return null;
		}finally{
			if(db != null){
				db.close();
			}
		}}
		
	}

	/**
	 * 查询数据,当查询结果有多条时，可使用该方法 每一条数据为一个map。 map中的key是列名，value是对应的值
	 * 
	 * 
	 * @param table
	 *            要查询的表名
	 * @param columns
	 *            要查询的列名
	 * @param selection
	 *            可带占位符查询条件 即where 后的语句 例如： name = ? and age = ?
	 * @param selectionArgs
	 *            填充占位符的字符串 new String[]{"zhang","21"}
	 * @param groupBy
	 *            group by 后面的语句
	 * @param having
	 *            having 后面的语句
	 * @param orderBy
	 *            order by 后面的语句
	 * @return
	 */
	public List<Map<String, String>> select(String table, String[] columns,
			String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy) {
		synchronized (obj) {try{

			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			db = DbHelper.getInstance();
			db.open();

			Cursor cursor = db.select(table, columns, selection, selectionArgs,
					groupBy, having, orderBy);
			while (cursor != null && cursor.moveToNext()) {
				Map<String, String> map = new HashMap<String, String>();
				for (int i = 0; i < cursor.getColumnCount(); i++) {
					if (cursor.getString(i) != null) {
						map.put(cursor.getColumnName(i), cursor.getString(i));
					}
				}
				list.add(map);
			}
			cursor.close();

			return list;
		
		}catch(Exception ex){
			Log.i("db Exception", " db error");
			ex.printStackTrace();
			return null;
		}finally{
			if(db != null){
				db.close();
			}
		}
		}
		
		
	}

	/**
	 * 当查询结果只有一条记录时，可使用该方法。 返回map，map中的key是列名，value是对应的值
	 * 
	 * @param table
	 *            要查询的表名
	 * @param columns
	 *            要查询的列名
	 * @param selection
	 *            可带占位符查询条件 即where 后的语句 例如： name = ? and age = ?
	 * @param selectionArgs
	 *            填充占位符的字符串 new String[]{"zhang","21"}
	 * @param groupBy
	 *            group by 后面的语句
	 * @param having
	 *            having 后面的语句
	 * @param orderBy
	 *            order by 后面的语句
	 * @return
	 */
	public Map<String, String> selectOneRecord(String table, String[] columns,
			String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy) {
		synchronized (obj) {
			try{

				Map<String, String> map = new HashMap<String, String>();

				db = DbHelper.getInstance();
				db.open();

				Cursor cursor = db.select(table, columns, selection, selectionArgs,
						groupBy, having, orderBy);
				if (cursor != null && cursor.moveToFirst()) {
					for (int i = 0; i < cursor.getColumnCount(); i++) {
						if (cursor.getString(i) != null) {
							map.put(cursor.getColumnName(i), cursor.getString(i));
						}
					}
				}
				cursor.close();

				return map;
			
			}catch(Exception ex){
				Log.i("db Exception", " db error");
				ex.printStackTrace();
				return null;
			}finally{
				if(db != null){
					db.close();
				}
			}
		}
		
		
	}

	/*
	 * 添加数据
	 * 
	 * @param table 表名
	 * 
	 * @param fields 列名
	 * 
	 * @param values 与列顺序相对应的值
	 * 
	 * @return 返回-1则表示出错
	 */
	public long insert(String table, String fields[], String values[]) {
		synchronized (obj) {
			try{

				db = DbHelper.getInstance();
				db.open();

				long result = db.insert(table, fields, values);

				return result;
			
			}catch(Exception ex){
				Log.i("db Exception", " db error");
				ex.printStackTrace();
				return -1;
			}finally{
				if(db != null){
					db.close();
				}
			}
		}
		
		
	}

	/**
	 * 删除数据，返回删除条数
	 * 
	 * @param table
	 *            表名
	 * @param where
	 *            可带占位符的条件
	 * @param whereValue
	 *            占位符的填充字符
	 * @return 删除的行数
	 */
	public int delete(String table, String where, String[] whereValue) {
		synchronized (obj) {
			try{

				db = DbHelper.getInstance();

				db.open();

				int result = db.delete(table, where, whereValue);

				return result;
			
			}catch(Exception ex){
				Log.i("db Exception", " db error");
				ex.printStackTrace();
				return 0;
			}finally{
				if(db != null){
					db.close();
				}
			}
		}
	}

	/**
	 * 更新数据
	 * 
	 * @param table
	 *            表名
	 * @param updateFields
	 *            更新的列名
	 * @param updateValues
	 *            对应的值
	 * @param where
	 *            更新条件 可带占位符
	 * @param whereValue
	 *            填充占位符的字符串 可为空
	 * @return
	 */
	public int update(String table, String updateFields[],
			String updateValues[], String where, String[] whereValue) {
		synchronized (obj) {
			try{

				db = DbHelper.getInstance();

				db.open();

				int result = db.update(table, updateFields, updateValues, where,
						whereValue);

				return result;
			
			}catch(Exception ex){
				Log.i("db Exception", " db error");
				ex.printStackTrace();
				return 0;
			}finally{
				if(db != null){
					db.close();
				}
			}
		}
		
		
	}

	/**
	 * 将object对象插入table中
	 * 
	 * @param table
	 *            要插入的表名
	 * @param object
	 *            要插入的对象
	 * @return 插入的条数
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public long insertObject(String table, Object object) {
		synchronized (obj) {
			
			try{

				db = DbHelper.getInstance();
				db.open();
				String sql = "PRAGMA table_info(" + table + ")";
				Cursor cursor = db.rawQuery(sql, null);

				Class objClass = object.getClass();

				Map<String, String> map = new HashMap<String, String>();

				int i = 0;
				while (cursor != null && cursor.moveToNext()) { // 父类的域
					try {

						String mName = getMethodName("get", cursor.getString(1));
						if (cursor.getString(1).equals("MSG_ID")) {
							Method fatherMethod = objClass.getSuperclass()
									.getDeclaredMethod(mName);
							if (fatherMethod != null) {

								try {
									map.put(cursor.getString(1), StringFormat
											.isNull(objClass.getMethod(
													getMethodName("get",
															cursor.getString(1)))
													.invoke(object)));
								} catch (IllegalArgumentException e) {

								} catch (IllegalAccessException e) {

								} catch (InvocationTargetException e) {

								} catch (NoSuchMethodException e) {

								}
							}
							i++;
							continue;
						}
						Method m = objClass.getDeclaredMethod(mName);
						if (objClass.getDeclaredMethod(mName) != null) {

							try {
								map.put(cursor.getString(1), StringFormat
										.isNull(objClass.getMethod(
												getMethodName("get",
														cursor.getString(1)))
												.invoke(object)));
							} catch (IllegalArgumentException e) {

							} catch (IllegalAccessException e) {

							} catch (InvocationTargetException e) {

							} catch (NoSuchMethodException e) {

							}
						}

					} catch (NoSuchMethodException e) {

					}
					i++;
				}

				String[] fields = new String[map.size()];
				String[] values = new String[map.size()];

				Iterator iter = map.entrySet().iterator();

				int j = 0;
				while (iter.hasNext()) {

					Map.Entry entry = (Map.Entry) iter.next();

					fields[j] = entry.getKey().toString();

					values[j] = entry.getValue().toString();

					j++;
				}
				long result = db.insert(table, fields, values);
				cursor.close();

				return result;
			}catch(Exception ex){
				Log.i("db Exception", " db error");
				ex.printStackTrace();
				return 0;
			}finally{
				if(db != null){
					db.close();
				}
			}
		}
		
		
	}

	/**
	 * 根据响应对象，更新表中以 属性_1 结尾的字段 的数据
	 * 
	 * @param table
	 *            更新的表名
	 * @param object
	 *            存放数据的对象
	 * @param where
	 *            更新条件 可带占位符
	 * @param whereValue
	 *            填充占位符的字符串 可为空
	 * @return 受影响的记录条数
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public long updateRespObject(String table, Object object, String where,
			String[] whereValue) {
		synchronized (obj) {
			
			try{

				db = DbHelper.getInstance();
				db.open();
				String sql = "PRAGMA table_info(" + table + ")";
				Cursor cursor = db.rawQuery(sql, null);

				Class objClass = object.getClass();

				Map<String, String> map = new HashMap<String, String>();

				while (cursor != null && cursor.moveToNext()) {

					if (cursor.getString(1).endsWith("_1")) {
						String mName = getMethodName("get", cursor.getString(1));
						try {
							if (!cursor.getString(1).equals("MSG_ID_1")) {

								Method m = objClass.getDeclaredMethod(mName);
								if (objClass.getDeclaredMethod(mName) != null) {

									map.put(cursor.getString(1), StringFormat
											.isNull(objClass.getMethod(
													getMethodName("get",
															cursor.getString(1)))
													.invoke(object)));
								}
							} else {

								Method fatherMethod = objClass.getSuperclass()
										.getDeclaredMethod(mName);
								if (fatherMethod != null) {

									map.put(cursor.getString(1), StringFormat
											.isNull(objClass.getMethod(
													getMethodName("get",
															cursor.getString(1)))
													.invoke(object)));

								}
							}

						} catch (NoSuchMethodException e) {

						} catch (IllegalArgumentException e) {

						} catch (IllegalAccessException e) {

						} catch (InvocationTargetException e) {

						}
					}

				}

				String[] fields = new String[map.size()];
				String[] values = new String[map.size()];

				Iterator iter = map.entrySet().iterator();

				int j = 0;
				while (iter.hasNext()) {

					Map.Entry entry = (Map.Entry) iter.next();

					fields[j] = entry.getKey().toString();

					values[j] = entry.getValue().toString();

					j++;
				}
				long result = db.update(table, fields, values, where, whereValue);
				cursor.close();

				return result;
			
			}catch(Exception ex){
				Log.i("db Exception", " db error");
				ex.printStackTrace();
				return 0;
			}finally{
				if(db != null){
					db.close();
				}
			}
		}
		
		
	}

	/**
	 * 根据对象，更新表中的数据
	 * 
	 * @param table
	 *            更新的表名
	 * @param object
	 *            存放数据的对象
	 * @param where
	 *            更新条件 可带占位符
	 * @param whereValue
	 *            填充占位符的字符串 可为空
	 * @return 受影响的记录条数
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public long updateObject(String table, Object object, String where,
			String[] whereValue) {
		synchronized (obj) {
			
			try{

				db = DbHelper.getInstance();
				db.open();
				String sql = "PRAGMA table_info(" + table + ")";
				Cursor cursor = db.rawQuery(sql, null);

				Class objClass = object.getClass();

				Map<String, String> map = new HashMap<String, String>();

				while (cursor != null && cursor.moveToNext()) {
					try {
						String mName = getMethodName("get", cursor.getString(1));
						Method m = objClass.getDeclaredMethod(mName);
						if (objClass.getDeclaredMethod(mName) != null) {

							try {
								map.put(cursor.getString(1), StringFormat
										.isNull(objClass.getMethod(
												getMethodName("get",
														cursor.getString(1)))
												.invoke(object)));
							} catch (IllegalArgumentException e) {

							} catch (IllegalAccessException e) {

							} catch (InvocationTargetException e) {

							} catch (NoSuchMethodException e) {

							}
						}
					} catch (NoSuchMethodException e) {

					}

				}

				String[] fields = new String[map.size()];
				String[] values = new String[map.size()];

				Iterator iter = map.entrySet().iterator();

				int j = 0;
				while (iter.hasNext()) {

					Map.Entry entry = (Map.Entry) iter.next();

					fields[j] = entry.getKey().toString();

					values[j] = entry.getValue().toString();

					j++;
				}
				long result = db.update(table, fields, values, where, whereValue);
				cursor.close();

				return result;
			
			}catch(Exception ex){
				Log.i("db Exception", " db error");
				ex.printStackTrace();
				return 0;
			}finally{
				if(db != null){
					db.close();
				}
			}
		}
		
		
	}

	/**
	 * 该方法实现将srcTable表中的数据转存到dstTable中 并删除srcTable中数据 的功能 , 支持多条转存
	 * 
	 * @param srcTable
	 *            源表
	 * @param dstTable
	 *            目标表
	 * @return true 表示转存成功
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	public boolean transferTable(String srcTable, String dstTable) {
		synchronized (obj) {
			try{

				boolean isSucc = false;

				db = DbHelper.getInstance();
				db.open();
				String srcSql = "select * from " + srcTable;
				Cursor srcCursor = db.rawQuery(srcSql, null);

				String sql = "PRAGMA table_info(" + dstTable + ")";
				Cursor dstCursor = db.rawQuery(sql, null);

				Map<String, String> map = new HashMap<String, String>();
				while (dstCursor != null && dstCursor.moveToNext()) {
					map.put(dstCursor.getString(1), "");

				}
				int ss = srcCursor.getColumnCount();
				if (srcCursor != null && srcCursor.moveToFirst()) {
					for (int i = 0; i < srcCursor.getColumnCount(); i++) {
						if (map.get(srcCursor.getColumnName(i)) != null) {
							map.put(srcCursor.getColumnName(i),
									StringFormat.isNull(srcCursor.getString(i)));
						}
					}

				}
				map.remove("_ID");
				String dstFields[] = new String[dstCursor.getCount() - 1];
				String dstValues[] = new String[dstCursor.getCount() - 1];
				Iterator iter = map.entrySet().iterator();

				int j = 0;
				while (iter.hasNext()) {

					Map.Entry entry = (Map.Entry) iter.next();

					dstFields[j] = entry.getKey().toString();

					dstValues[j] = entry.getValue().toString();

					j++;
				}

				long result = db.insert(dstTable, dstFields, dstValues);

				if (result >= 1) {
					isSucc = true;
					db.delete(srcTable, null, null);
				}
				srcCursor.close();
				dstCursor.close();

				return isSucc;
			}catch(Exception ex){
				Log.i("db Exception", " db error");
				ex.printStackTrace();
				return false;
			}finally{
				if(db != null){
					db.close();
				}
			}
		}
		
	}

	/**
	 * 组装get,set方法名
	 * 
	 * @param type
	 *            要组装set或get方法
	 * @param name
	 *            属性名
	 * @return
	 */
	private String getMethodName(String type, String name) {
		String methodName = "";
		if (name != null && name.length() > 1) {
			if (name.equalsIgnoreCase("service_PIN_Capture_Code")) {
				return "getServicePINCaptureCode";
			}
			if (name.equals("_ID")) {
				return "get_ID";
			}
			if (name.endsWith("_1")) {
				name = name.substring(0, name.length() - 2);
			}
			String[] arr = name.split("_");
			if (type != null && type.equals("get")) {
				methodName = "get";
				for (int i = 0; i < arr.length; i++) {
					methodName = methodName
							+ arr[i].substring(0, 1)
									.toUpperCase(Locale.ENGLISH)
							+ arr[i].substring(1, arr[i].length()).toLowerCase(
									Locale.ENGLISH);
				}

			}
			if (type != null && type.equals("set")) {
				methodName = "set";
				for (int i = 0; i < arr.length; i++) {
					methodName = methodName
							+ arr[i].substring(0, 1)
									.toUpperCase(Locale.ENGLISH)
							+ arr[i].substring(1, arr[i].length()).toLowerCase(
									Locale.ENGLISH);
				}

			}
		}
		return methodName;
	}
}
