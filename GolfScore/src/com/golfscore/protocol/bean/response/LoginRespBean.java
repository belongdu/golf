package com.golfscore.protocol.bean.response;

import com.golfscore.protocol.bean.ResponseBean;

public class LoginRespBean extends ResponseBean{
	
	private String MatchID;
	private String MatchKey;
	private String MatchDate;
	private String MatchName;
	private String MatchStatus;
	public String getMatchID() {
		return MatchID;
	}
	public void setMatchID(String matchID) {
		MatchID = matchID;
	}
	public String getMatchKey() {
		return MatchKey;
	}
	public void setMatchKey(String matchKey) {
		MatchKey = matchKey;
	}
	public String getMatchDate() {
		return MatchDate;
	}
	public void setMatchDate(String matchDate) {
		MatchDate = matchDate;
	}
	public String getMatchName() {
		return MatchName;
	}
	public void setMatchName(String matchName) {
		MatchName = matchName;
	}
	public String getMatchStatus() {
		return MatchStatus;
	}
	public void setMatchStatus(String matchStatus) {
		MatchStatus = matchStatus;
	}
	
	
}
