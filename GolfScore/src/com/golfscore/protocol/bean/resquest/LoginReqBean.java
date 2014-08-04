package com.golfscore.protocol.bean.resquest;

import com.golfscore.protocol.bean.RequestBean;

public class LoginReqBean extends RequestBean{
	private String userName;
	private String password;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
