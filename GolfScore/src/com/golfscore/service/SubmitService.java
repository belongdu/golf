package com.golfscore.service;

import java.util.List;
import java.util.Map;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.golfscore.db.DbHandle;
import com.golfscore.protocol.DispatchRequest;
import com.golfscore.protocol.bean.ResponseBean;
import com.golfscore.protocol.bean.response.ScoreRespBean;
import com.golfscore.protocol.bean.resquest.ScoreReqBean;
import com.golfscore.protocol.constants.ProtocolConstants;

public class SubmitService extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		
		return null;
	}

	
	@Override
	public void onCreate() {
		super.onCreate();
//		DbHandle db = new DbHandle();
//		while(true) {
//			try {
//
//				List<Map<String,String>> list = db.select("infoTable", new String[]{"groupId", "hole","userId", "name", "score",}, "status = ?", new String[]{"提交中"}, null, null, null);
//				for (int i = 0; i < list.size(); i++) {
//					Map<String,String> map = list.get(i);
//					if (map != null && map.size() > 0) {
//						ScoreReqBean reqBean = new ScoreReqBean();
//						reqBean.setGroupId(map.get("groupId"));
//						reqBean.setHole(map.get("hole"));
//						reqBean.setUserId(map.get("userId"));
//						reqBean.setScore(map.get("score"));
//						
//						ResponseBean respBean = DispatchRequest.submit(ProtocolConstants.USR_SCORE, reqBean, ScoreRespBean.class);
//						
//						if (respBean != null && respBean.getResult().equals("0")) {
//							
//						}else{
//							
//							db.update("infoTable", new String[]{"status"}, new String[]{"已提交"}, "hole = ? and groupId = ? and userId = ?", new String[]{reqBean.getHole(),reqBean.getGroupId(),reqBean.getUserId()});	
//							
//						}
//					}
//				}
//	
//				Thread.sleep(30000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
	}

}
