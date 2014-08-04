package com.golfscore.protocol.json;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.golfscore.protocol.bean.ResponseBean;
import com.golfscore.protocol.constants.ProtocolConstants;

public class JsonPackUnpack {
	 
     
     @SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<ResponseBean> UnPack(String json, Class cla,String jsonType){
    	 List<ResponseBean> list=new ArrayList<ResponseBean>();
    	 
    	 if(jsonType.equals(ProtocolConstants.JSONTYPE_OBJECT)){//如果json是object类型
    		 
    		 Object obj = (Object) JSONObject.parseObject(json, cla);
    		 list.add((ResponseBean) obj);
    	 }else if(jsonType.equals(ProtocolConstants.JSONTYPE_ARRAY)){//如果json是array类型
    		 
    		 list=JSONObject.parseArray(json, cla);
    	 }
    	 return list;
     }
}
