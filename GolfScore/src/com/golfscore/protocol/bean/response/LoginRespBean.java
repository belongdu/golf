package com.golfscore.protocol.bean.response;

import com.golfscore.protocol.bean.ResponseBean;

public class LoginRespBean extends ResponseBean{

	private String matchId;
	private String matchName;

	
	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getMatchName() {
		return matchName;
	}

	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}
	

}
