package com.golfscore.protocol.bean.response;

import java.util.List;

import com.golfscore.protocol.bean.Player;
import com.golfscore.protocol.bean.ResponseBean;

public class GroupRespBean extends ResponseBean{

	
	private String CompetitorID;
	private String CompetitorCode;
	private String CompetitorName;
	private String CompetitorGroup;
	private String Tee;
	private String CurHole;
	private String CurHolePar;
	private String CurHoleResult;
	public String getCompetitorID() {
		return CompetitorID;
	}
	public void setCompetitorID(String competitorID) {
		CompetitorID = competitorID;
	}
	public String getCompetitorCode() {
		return CompetitorCode;
	}
	public void setCompetitorCode(String competitorCode) {
		CompetitorCode = competitorCode;
	}
	public String getCompetitorName() {
		return CompetitorName;
	}
	public void setCompetitorName(String competitorName) {
		CompetitorName = competitorName;
	}
	public String getCompetitorGroup() {
		return CompetitorGroup;
	}
	public void setCompetitorGroup(String competitorGroup) {
		CompetitorGroup = competitorGroup;
	}
	public String getTee() {
		return Tee;
	}
	public void setTee(String tee) {
		Tee = tee;
	}
	public String getCurHole() {
		return CurHole;
	}
	public void setCurHole(String curHole) {
		CurHole = curHole;
	}
	public String getCurHolePar() {
		return CurHolePar;
	}
	public void setCurHolePar(String curHolePar) {
		CurHolePar = curHolePar;
	}
	public String getCurHoleResult() {
		return CurHoleResult;
	}
	public void setCurHoleResult(String curHoleResult) {
		CurHoleResult = curHoleResult;
	}
	
	

}
