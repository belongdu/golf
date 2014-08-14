package com.golfscore.protocol.bean.resquest;

import com.golfscore.protocol.bean.RequestBean;

public class ScoreReqBean extends RequestBean{
	
	private String iMatchID;
	private String iRegisterID;
	private String iHoleNum;
	private String iHoleResult;
	public String getIMatchID() {
		return iMatchID;
	}
	public void setIMatchID(String iMatchID) {
		this.iMatchID = iMatchID;
	}
	public String getIRegisterID() {
		return iRegisterID;
	}
	public void setIRegisterID(String iRegisterID) {
		this.iRegisterID = iRegisterID;
	}
	public String getIHoleNum() {
		return iHoleNum;
	}
	public void setIHoleNum(String iHoleNum) {
		this.iHoleNum = iHoleNum;
	}
	public String getiHoleResult() {
		return iHoleResult;
	}
	public void setIHoleResult(String iHoleResult) {
		this.iHoleResult = iHoleResult;
	}
	
}
