package com.golfscore.protocol.bean.resquest;

import com.golfscore.protocol.bean.RequestBean;

public class ScoreReqBean extends RequestBean{
	private String userId;
	private String groupId;
	private String hole;
	private String score;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getHole() {
		return hole;
	}
	public void setHole(String hole) {
		this.hole = hole;
	}
	
}
