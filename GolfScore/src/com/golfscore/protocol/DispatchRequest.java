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
	
	 /**
	  * 
	  * @param ibusiType 
	  * @param requestBean 
	  * @param handler
	  * @param rspBeanClass
	  */
     @SuppressWarnings("rawtypes")
	public static void doHttpRequest(int iBusiType, final RequestBean requestBean,final  Handler handler,final Class rspBeanClass){
    	

    	 final  BusiTypeStruct bStruct = BusiTypeCreator.getBusiType(iBusiType);
    	 
    	 new Thread(){
    		 public void run(){
    			 List<NameValuePair> namePairList = new ArrayList<NameValuePair>();	 
    	    	 String jsonResult=""; 
    	    	 try{
    	    		 namePairList = getObjectToString(requestBean);
    	    		 jsonResult = HTTPtools.invoke(bStruct.getDoUrl(), namePairList);
    	    		 
    	    		 //没数据
    	    		 if (jsonResult == null || jsonResult.trim().equalsIgnoreCase("")){
    	    			
    	    			 Message ms=Message.obtain(); 
        	    		 ms.what = bStruct.messId;
     					 ms.obj=null;
     					 handler.sendMessage(ms);
    	    		 }else{
    	    			
    	    			 List<ResponseBean> mBeanList = JsonPackUnpack.UnPack(jsonResult, bStruct.getMessId(),bStruct.getJosonResultType());
        	    		 Message ms=Message.obtain(); 
        	    		 ms.what = bStruct.messId;
     					 ms.obj=mBeanList;
     					 handler.sendMessage(ms); 
    	    		 }	 
    	    	 }catch(Exception ex){
    	    		 ex.printStackTrace();
    	    	 }
    	    	 
    		 }
    	 }.start();
     }
 	@SuppressWarnings("rawtypes")
	public static void submitScore(int iBusiType, final RequestBean requestBean,final  Handler handler,final Class rspBeanClass,final String flag){
    	
 	final  BusiTypeStruct bStruct = BusiTypeCreator.getBusiType(iBusiType);
   	 
   	 
   	 new Thread(){
   		 public void run(){
   			 List<NameValuePair> namePairList = new ArrayList<NameValuePair>();	 
   	    	 String jsonResult=""; 
   	    	 try{
   	    		 namePairList = getObjectToString(requestBean);
   	    		 jsonResult = HTTPtools.invoke(bStruct.getDoUrl(), namePairList);
   	    		 
   	    		 Message ms=Message.obtain(); 
   	    		 Bundle bundle = new Bundle();
   	    		 bundle.putString("flag", flag);
   	    		 ms.setData(bundle);
   	    		 //没数据
   	    		 if (jsonResult == null || jsonResult.trim().equalsIgnoreCase("")){
   	    			
       	    		 ms.what = bStruct.messId;
    					 ms.obj=null;
    					 handler.sendMessage(ms);
   	    		 }else{
   	    			
   	    			 List<ResponseBean> mBeanList = JsonPackUnpack.UnPack(jsonResult, bStruct.getMessId(),bStruct.getJosonResultType());
       	    	 
       	    		 ms.what = bStruct.messId;
    					 ms.obj=mBeanList;
    					 handler.sendMessage(ms); 
   	    		 }	 
   	    	 }catch(Exception ex){
   	    		 ex.printStackTrace();
   	    	 }
   	    	 
   		 }
   	 }.start();
    }
 	
 	@SuppressWarnings("rawtypes")
	public static ResponseBean submit(int iBusiType, RequestBean requestBean, Class rspBeanClass){
    	
 		 BusiTypeStruct bStruct = BusiTypeCreator.getBusiType(iBusiType);
	   	 List<NameValuePair> namePairList = new ArrayList<NameValuePair>();	 
	   	 String jsonResult=""; 
	   	 try{
	   		 namePairList = getObjectToString(requestBean);
	   		 jsonResult = HTTPtools.invoke(bStruct.getDoUrl(), namePairList);
	   		 
	   		
	   		 //没数据
	   		 if (jsonResult == null || jsonResult.trim().equalsIgnoreCase("")){
	   			
		    		
	   		 }else{
	   			
	   			List<ResponseBean> mBeanList = JsonPackUnpack.UnPack(jsonResult, bStruct.getMessId(),bStruct.getJosonResultType());
		    	 
	   			return mBeanList.get(0);
	   		 }	 
	   	 }catch(Exception ex){
	   		 ex.printStackTrace();
	   	 }	 

	   	 return null;
   		 
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
