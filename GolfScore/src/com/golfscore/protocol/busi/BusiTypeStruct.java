package com.golfscore.protocol.busi;

public class BusiTypeStruct {
	public String doUrl = "";// 请求所使用的url
	public String josonResultType;// 返回结果所使用的结构
	public int messId;// 此业务类型所使用的消息ID
	
	public String getDoUrl() {
		return doUrl;
	}

	public void setDoUrl(String doUrl) {
		this.doUrl = doUrl;
	}

	public int getMessId() {
		return messId;
	}

	public void setMessId(int messId) {
		this.messId = messId;
	}

	public String getJosonResultType() {
		return josonResultType;
	}

	public void setJosonResultType(String josonResultType) {
		this.josonResultType = josonResultType;
	}

	
}
