package com.golfscore.protocol;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

import android.util.Log;

public class HTTPtools {
	
	
	private final static String SERVER_URL = "http://192.168.0.110:8080/HttpSer/servlet/";// 访问地址
	private static CookieStore cookieStore;// 定义一个Cookie来保存session

	/**
	 * 向服务器发送URL请求 获得返回数据
	 * 
	 * @param doUrl
	 *            stauts的类名
	 * @param actionName
	 *            方法名
	 * @param params
	 *            传递的参数
	 * @return 获得返回的JSON结果
	 */
	public static String invoke(String doUrl, List<NameValuePair> params) {
		
		String result = null;

		try {
			
			String url = SERVER_URL + doUrl;

			
			BasicHttpParams httpParams = new BasicHttpParams();  
			
			//设置请求超时30秒钟 
		    HttpConnectionParams.setConnectionTimeout(httpParams, 30*1000);  
		    
		    //设置等待数据超时时间30秒钟 
		    HttpConnectionParams.setSoTimeout(httpParams, 30*1000);  
			
		    HttpPost httpPost = new HttpPost(url);
		    
			if (params != null && params.size() > 0) {

				HttpEntity entity = new UrlEncodedFormEntity(params, "UTF-8");

				httpPost.setEntity(entity);
			}
			httpPost.addHeader("Content-Type",
					"application/x-www-form-urlencoded; charset=\"UTF-8\"");
			DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
			// 添加Cookie
			if (cookieStore != null) {
				httpClient.setCookieStore(cookieStore);
			}
			Log.i("====send url=====",url);
			if (params != null) {
				for (NameValuePair value: params) {
					Log.i("====send data=====",value.getName() +":" + value.getValue());
				}
			} else {
				Log.i("====send data=====","null");
			}
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {

				StringBuilder builder = new StringBuilder();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(httpResponse.getEntity()
								.getContent(), "UTF-8"));
				for (String s = reader.readLine(); s != null; s = reader
						.readLine()) {
					builder.append(s);
				}
				result = builder.toString();

				// 保存Cookie
				cookieStore = ((AbstractHttpClient) httpClient)
						.getCookieStore();
			}
		} catch (Exception e) {
			
		}

		
		if (result == null) {
			Log.i("====recive=====","null");
		} else {
			Log.i("====recive=====",result);
		}
		return result;
	}

	/**
	 * 清除cookie
	 */
	public static void clear() {
		HTTPtools.cookieStore = null;
	}
}
