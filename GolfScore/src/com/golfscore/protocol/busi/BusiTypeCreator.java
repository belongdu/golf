package com.golfscore.protocol.busi;

import com.golfscore.protocol.constants.ProtocolConstants;



public class BusiTypeCreator {
	
	public static BusiTypeStruct getBusiType(int ibusiType){
		
		BusiTypeStruct btStruct = new BusiTypeStruct();
    	   
    	   switch(ibusiType){
    	      case ProtocolConstants.USR_LOGIN :{
    	    	   btStruct.setDoUrl(ProtocolConstants.URL_USR_LOGIN);
    	    	   btStruct.setJosonResultType(ProtocolConstants.JSONTYPE_OBJECT);
    	    	   btStruct.setMessId(ProtocolConstants.MESSID_USR_LOGIN);
    	    	   break;  
    	      }
    	      case ProtocolConstants.USR_MATCH :{
    	    	   btStruct.setDoUrl(ProtocolConstants.URL_USR_MATCH);
   	    	       btStruct.setJosonResultType(ProtocolConstants.JSONTYPE_OBJECT);
   	    	       btStruct.setMessId(ProtocolConstants.MESSID_USR_MATCH);
    	    	   break;  
    	      }
    	      case ProtocolConstants.USR_GROUP :{
    	    	   btStruct.setDoUrl(ProtocolConstants.URL_USR_GROUP);
   	    	       btStruct.setJosonResultType(ProtocolConstants.JSONTYPE_ARRAY);
   	    	       btStruct.setMessId(ProtocolConstants.MESSID_USR_GROUP);
    	    	   break;
    	      }
    	      case ProtocolConstants.USR_SCORE :{
    	    	   btStruct.setDoUrl(ProtocolConstants.URL_USR_SCORE);
   	    	       btStruct.setJosonResultType(ProtocolConstants.JSONTYPE_OBJECT);
   	    	       btStruct.setMessId(ProtocolConstants.MESSID_USR_SCORE);
    	    	   break;
    	      }

    	      default:{
    	    	   break;
    	      }
    	   }
    	   
    	   return btStruct;
       }
}
