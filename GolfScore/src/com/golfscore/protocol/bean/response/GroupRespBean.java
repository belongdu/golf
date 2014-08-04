package com.golfscore.protocol.bean.response;

import com.golfscore.protocol.bean.ResponseBean;

public class GroupRespBean extends ResponseBean{

	private String userId;
	private String userName;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	

}
