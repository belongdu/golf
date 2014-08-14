package com.golfscore.protocol.bean.resquest;

import com.golfscore.protocol.bean.RequestBean;

public class LoginReqBean extends RequestBean{
	
	private String strUser;
	private String strPwd;
	public String getStrUser() {
		return strUser;
	}
	public void setStrUser(String strUser) {
		this.strUser = strUser;
	}
	public String getStrPwd() {
		return strPwd;
	}
	public void setStrPwd(String strPwd) {
		this.strPwd = strPwd;
	}
	
	
}
