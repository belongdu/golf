package com.golfscore.protocol;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.golfscore.protocol.bean.RequestBean;
import com.golfscore.protocol.bean.ResponseBean;
import com.golfscore.protocol.busi.BusiTypeCreator;
import com.golfscore.protocol.busi.BusiTypeStruct;
import com.golfscore.protocol.json.JsonPackUnpack;


public class DispatchRequest {
	
 	private static RequestBean reqbean = null;
 	@SuppressWarnings("rawtypes")
	private static Class respBeanClass = null;
 	private static Handler mhandler = null;
 	private static BusiTypeStruct bStruct = new BusiTypeStruct();
	
	 /**
	  * 
	  * @param ibusiType 
	  * @param requestBean 
	  * @param handler
	  * @param rspBeanClass
	  */
     @SuppressWarnings("rawtypes")
	public static void doHttpRequest(int iBusiType, RequestBean requestBean, Handler handler, Class rspBeanClass){
    	
    	 reqbean = requestBean;
    	 respBeanClass = rspBeanClass;
    	 mhandler = handler;
    	 bStruct = BusiTypeCreator.getBusiType(iBusiType);
    	 
    	 new Thread(){
    		 public void run(){
    			 List<NameValuePair> namePairList = new ArrayList<NameValuePair>();	 
    	    	 String jsonResult=""; 
    	    	 try{
    	    		 namePairList = getObjectToString(reqbean);
    	    		 jsonResult = HTTPtools.invoke(bStruct.getDoUrl(), namePairList);
    	    		 
    	    		 //没数据
    	    		 if (jsonResult == null || jsonResult.trim().equalsIgnoreCase("")){
    	    			
    	    			 Message ms=Message.obtain(); 
        	    		 ms.what = bStruct.messId;
     					 ms.obj=null;
     					 mhandler.sendMessage(ms);
    	    		 }else{
    	    			
    	    			 List<ResponseBean> mBeanList = JsonPackUnpack.UnPack(jsonResult, respBeanClass,bStruct.getJosonResultType());
        	    		 Message ms=Message.obtain(); 
        	    		 ms.what = bStruct.messId;
     					 ms.obj=mBeanList;
     					 mhandler.sendMessage(ms); 
    	    		 }	 
    	    	 }catch(Exception ex){
    	    		 ex.printStackTrace();
    	    	 }
    	    	 
    		 }
    	 }.start();
     }
 	@SuppressWarnings("rawtypes")
	public static void submitScore(int iBusiType, RequestBean requestBean, Handler handler, Class rspBeanClass,final String flag){
    	
   	 reqbean = requestBean;
   	 respBeanClass = rspBeanClass;
   	 mhandler = handler;
   	 bStruct = BusiTypeCreator.getBusiType(iBusiType);
   	 
   	 new Thread(){
   		 public void run(){
   			 List<NameValuePair> namePairList = new ArrayList<NameValuePair>();	 
   	    	 String jsonResult=""; 
   	    	 try{
   	    		 namePairList = getObjectToString(reqbean);
   	    		 jsonResult = HTTPtools.invoke(bStruct.getDoUrl(), namePairList);
   	    		 
   	    		 Message ms=Message.obtain(); 
   	    		 Bundle bundle = new Bundle();
   	    		 bundle.putString("flag", flag);
   	    		 ms.setData(bundle);
   	    		 //没数据
   	    		 if (jsonResult == null || jsonResult.trim().equalsIgnoreCase("")){
   	    			
       	    		 ms.what = bStruct.messId;
    					 ms.obj=null;
    					 mhandler.sendMessage(ms);
   	    		 }else{
   	    			
   	    			 List<ResponseBean> mBeanList = JsonPackUnpack.UnPack(jsonResult, respBeanClass,bStruct.getJosonResultType());
       	    	 
       	    		 ms.what = bStruct.messId;
    					 ms.obj=mBeanList;
    					 mhandler.sendMessage(ms); 
   	    		 }	 
   	    	 }catch(Exception ex){
   	    		 ex.printStackTrace();
   	    	 }
   	    	 
   		 }
   	 }.start();
    }
     @SuppressWarnings({ "rawtypes", "unchecked" })
	private static List<NameValuePair> getObjectToString(Object obj) throws Exception {
    	
 		List<NameValuePair> mList = new ArrayList<NameValuePair>();
 		
 		if (obj == null){
 			return null;
 		}
 		
 		Class cla = obj.getClass();
 		Field[] fd = cla.getDeclaredFields();
 		Object returnValue = null;
 		
 		for (int i = 0; i < fd.length; i++) {
 			returnValue = cla.getMethod("get" + fd[i].getName().substring(0, 1).toUpperCase() + fd[i].getName().substring(1)).invoke(obj);
 			
			BasicNameValuePair bNameVal = new BasicNameValuePair(fd[i].getName(), (String)returnValue);		
			mList.add(bNameVal);
 		}
 		
 		
 		return mList;
 	}
     
     public static void doClear(){
    	 HTTPtools.clear();
     }
}
