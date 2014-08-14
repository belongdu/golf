package com.golfscore.protocol.json;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.golfscore.protocol.bean.ResponseBean;
import com.golfscore.protocol.bean.response.GroupRespBean;
import com.golfscore.protocol.bean.response.LoginRespBean;
import com.golfscore.protocol.bean.response.ScoreRespBean;
import com.golfscore.protocol.constants.ProtocolConstants;

//import com.alibaba.fastjson.JSONObject;
public class JsonPackUnpack {

	public static List<ResponseBean> UnPack(String json, int msgId,
			String jsonType) {
		List<ResponseBean> list = new ArrayList<ResponseBean>();

		if (jsonType.equals(ProtocolConstants.JSONTYPE_OBJECT)) {// 如果json是object类型
			if (msgId == ProtocolConstants.USR_LOGIN) {
				if ("InvalidateUser".equals(json)) {
					LoginRespBean respBean = new LoginRespBean();
					respBean.setResult("-1");
					list.add(respBean);
				} else {
					LoginRespBean respBean = new LoginRespBean();
					try {
						JSONObject jsonObject = new JSONObject(json);
						respBean.setMatchID(jsonObject.getString("MatchID"));
						respBean.setMatchDate(jsonObject.getString("MatchDate"));
						respBean.setMatchKey(jsonObject.getString("MatchKey"));
						respBean.setMatchName(jsonObject.getString("MatchName"));
						respBean.setMatchStatus(jsonObject.getString("MatchStatus"));
						respBean.setResult("0");
						list.add(respBean);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			} else {

				ScoreRespBean respBean = new ScoreRespBean();

				if ("UpdateOneCompeitorOneHoleResult_Fail".equals(json)) {
					respBean.setResult("-1");
				} else if ("UpdateOneCompeitorOneHoleResult_Success"
						.equals(json)) {
					respBean.setResult("0");
				}
				list.add(respBean);

			}

		} else if (jsonType.equals(ProtocolConstants.JSONTYPE_ARRAY)) {// 如果json是array类型
			if (json.equals("[]")) {
				GroupRespBean bean = new GroupRespBean();
				
				bean.setResult("-1");
				list.add(bean);
			} else {
				try {
					JSONArray array = new JSONArray(json);
					for (int i = 0; i < array.length(); i++) {
						JSONObject jsonObject = array.getJSONObject(i);
						GroupRespBean bean = new GroupRespBean();
						bean.setCompetitorID(jsonObject.getString("CompetitorID"));
						bean.setCompetitorCode(jsonObject.getString("CompetitorCode"));
						bean.setCompetitorName(jsonObject.getString("CompetitorName"));
						bean.setCompetitorGroup(jsonObject.getInt("CompetitorGroup")+"");
						bean.setTee(jsonObject.getInt("Tee")+"");
						bean.setCurHole(jsonObject.getInt("CurHole")+"");
						bean.setCurHolePar(jsonObject.getInt("CurHolePar")+"");
						bean.setCurHoleResult(jsonObject.getInt("CurHoleResult")+"");
						bean.setResult("0");
						list.add(bean);
					}
				} catch (JSONException e) {
					
					e.printStackTrace();
				}
			}

		}
		return list;
	}
}
