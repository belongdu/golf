package com.golfscore.protocol.constants;

public class ProtocolConstants {

	public static final int USR_LOGIN=0;//登录
	public static final int USR_MATCH=1;//获取比赛信息
 	public static final int USR_GROUP=2;//获取组信息
 	public static final int USR_SCORE=3;//提交成绩

	
	public static final String JSONTYPE_OBJECT="object";//json传回为object类型
 	public static final String JSONTYPE_ARRAY="array";//json传回为array类型
 	
 	
 	public static final int MESSID_USR_LOGIN = 0;//登录
	public static final int MESSID_USR_MATCH = 1;//获取比赛信息
 	public static final int MESSID_USR_GROUP = 2;//获取组信息
 	public static final int MESSID_USR_SCORE = 3;//提交成绩

 		
 	public static final String URL_USR_LOGIN = "";//登录
	public static final String URL_USR_MATCH = "Succ";//获取比赛信息
	public static final String URL_USR_GROUP = "Mpay";//获取组信息
	public static final String URL_USR_SCORE = "Succ";//提交成绩


}
