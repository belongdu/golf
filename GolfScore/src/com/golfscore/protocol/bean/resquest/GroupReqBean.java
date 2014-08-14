package com.golfscore.protocol.bean.resquest;

import com.golfscore.protocol.bean.RequestBean;

public class GroupReqBean extends RequestBean{
	
	private String iMatchID;
	private String iGroupNum;
	private String iHoleNum;
	public String getIMatchID() {
		return iMatchID;
	}
	public void setIMatchID(String iMatchID) {
		this.iMatchID = iMatchID;
	}
	public String getIGroupNum() {
		return iGroupNum;
	}
	public void setIGroupNum(String iGroupNum) {
		this.iGroupNum = iGroupNum;
	}
	public String getIHoleNum() {
		return iHoleNum;
	}
	public void setIHoleNum(String iHoleNum) {
		this.iHoleNum = iHoleNum;
	}

	
}
