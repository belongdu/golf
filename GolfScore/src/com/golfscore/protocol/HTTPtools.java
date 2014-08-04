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
	
	
	private final static String SERVER_URL = "http://192.168.0.110:8080/HttpSer/servlet/";// ���ʵ�ַ
	private static CookieStore cookieStore;// ����һ��Cookie������session

	/**
	 * �����������URL���� ��÷�������
	 * 
	 * @param doUrl
	 *            stauts������
	 * @param actionName
	 *            ������
	 * @param params
	 *            ���ݵĲ���
	 * @return ��÷��ص�JSON���
	 */
	public static String invoke(String doUrl, List<NameValuePair> params) {
		
		String result = null;

		try {
			
			String url = SERVER_URL + doUrl;

			
			BasicHttpParams httpParams = new BasicHttpParams();  
			
			//��������ʱ30���� 
		    HttpConnectionParams.setConnectionTimeout(httpParams, 30*1000);  
		    
		    //���õȴ����ݳ�ʱʱ��30���� 
		    HttpConnectionParams.setSoTimeout(httpParams, 30*1000);  
			
		    HttpPost httpPost = new HttpPost(url);
		    
			if (params != null && params.size() > 0) {

				HttpEntity entity = new UrlEncodedFormEntity(params, "UTF-8");

				httpPost.setEntity(entity);
			}
			httpPost.addHeader("Content-Type",
					"application/x-www-form-urlencoded; charset=\"UTF-8\"");
			DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
			// ���Cookie
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

				// ����Cookie
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
	 * ���cookie
	 */
	public static void clear() {
		HTTPtools.cookieStore = null;
	}
}
