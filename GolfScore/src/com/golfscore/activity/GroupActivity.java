package com.golfscore.activity;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.golfscore.R;
import com.golfscore.activity.base.BaseActivity;
import com.golfscore.db.DbHandle;
import com.golfscore.protocol.DispatchRequest;
import com.golfscore.protocol.bean.ResponseBean;
import com.golfscore.protocol.bean.response.GroupRespBean;
import com.golfscore.protocol.bean.resquest.GroupReqBean;
import com.golfscore.protocol.constants.ProtocolConstants;
import com.golfscore.service.PollingService;
import com.golfscore.service.PollingUtils;


public class GroupActivity extends BaseActivity implements OnClickListener{
	EditText et ;
	String hole;
	@SuppressWarnings("rawtypes")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PollingUtils.startPollingService(this, 30, PollingService.class, PollingService.ACTION);
		setContentView(R.layout.group);
		Button exit1 = (Button) findViewById(R.id.groupBack1);
		Button exit2 = (Button) findViewById(R.id.groupBack2);
		Button sure = (Button) findViewById(R.id.sure_group);
		TextView tv = (TextView) findViewById(R.id.textView2);
		DbHandle db = new DbHandle();
		Map map = db.selectOneRecord("paramTable", new String[]{"paraValue"}, "paraName = ?", new String[]{"userName"}, null, null, null);
		tv.setText("记分员"+map.get("paraValue"));
		exit1.setOnClickListener(this);
		exit2.setOnClickListener(this);
		sure.setOnClickListener(this);
		et = (EditText) findViewById(R.id.group_id);
		
		map = new DbHandle().selectOneRecord("paramTable", new String[]{"paraValue"}, "paraName = ?", new String[]{"hole"}, null, null, null);
		
		hole = (String) map.get("paraValue");
		TextView tv1 = (TextView) findViewById(R.id.hole_info);
		tv1.setText(hole);
		
		map = new DbHandle().selectOneRecord("paramTable", new String[]{"paraValue"}, "paraName = ?", new String[]{"matchName"}, null, null, null);
		String matchName = (String) map.get("paraValue");
		TextView tv2 = (TextView) findViewById(R.id.editText1);
		tv2.setText(matchName);
		
		map = new DbHandle().selectOneRecord("paramTable", new String[]{"paraValue"}, "paraName = ?", new String[]{"matchDate"}, null, null, null);
		String matchDate = (String) map.get("paraValue");
		TextView tv3 = (TextView) findViewById(R.id.editText2);
		tv3.setText(matchDate);
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		PollingUtils.stopPollingService(this, PollingService.class, PollingService.ACTION);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.groupBack1||v.getId() == R.id.groupBack2) {
			Intent intent = new Intent(this,LoginActivity.class);
			startActivity(intent);
			finish();
		} else if (v.getId() == R.id.sure_group) {
			
			if (et.getText() != null && !et.getText().equals("")) {
				String groupId = et.getText().toString();
				GroupReqBean reqBean = new GroupReqBean();
				reqBean.setIGroupNum(groupId);
				reqBean.setIHoleNum(hole);
				
				Map map = new DbHandle().selectOneRecord("paramTable", new String[]{"paraValue"}, "paraName = ?", new String[]{"matchId"}, null, null, null);
				String iMatchID = (String) map.get("paraValue");
				
				reqBean.setIMatchID(iMatchID);
				new DbHandle().delete("paramTable", "paraName = ?", new String[]{"groupId"});
				new DbHandle().insert("paramTable", new String[]{"paraName","paraValue"}, new String[]{"groupId",groupId});
				

				List list = new DbHandle().select("infoTable",new String[]{"CompetitorGroup", "CurHole","CompetitorID", "Tee","CompetitorName", "CurHoleResult", "status"}, "CompetitorGroup=? and CurHole=?", new String[]{groupId,hole}, null, null, "Tee");
				if (list == null || list.size() == 0) {					
					DispatchRequest.doHttpRequest(ProtocolConstants.MESSID_USR_GROUP, reqBean, handler, GroupRespBean.class);
				} else {
					Intent intent = new Intent(GroupActivity.this,ScoreActivity.class);
					startActivity(intent);
				}
				
				
			} else {
				Toast.makeText(this, "请输入组号", Toast.LENGTH_SHORT).show();
			}
		}
	}
	private Handler handler = new Handler() {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void handleMessage(Message msg) {
			if (msg.obj != null) {
				switch (msg.what) {
				case ProtocolConstants.MESSID_USR_GROUP: // 查询组信息
					
					List<ResponseBean> respList = (List<ResponseBean>) msg.obj;
					if (!respList.get(0).getResult().equals("0")) {
						
						EditText et = (EditText) findViewById(R.id.group_id);
						et.setText("");
						
						Toast.makeText(GroupActivity.this, "组号错误，请重新输入",
								Toast.LENGTH_LONG).show();
						
					}else{
						
						String CompetitorID = null;
						String CompetitorCode = null;
						String CompetitorName = null;
						String CompetitorGroup = null;
						String Tee = null;
						String CurHole = null;
						String CurHolePar = null;
						String CurHoleResult = null;
						boolean isSuccess = true;
						
						Map map = new HashMap();
						for (int i = 0; i <respList.size(); i++) {
							GroupRespBean bean = (GroupRespBean)respList.get(i);
							CompetitorID = bean.getCompetitorID();
							CompetitorCode = bean.getCompetitorCode();
							CompetitorName = bean.getCompetitorName();
							CompetitorGroup = bean.getCompetitorGroup();
							Tee = bean.getTee();
							CurHolePar = bean.getCurHolePar();
							CurHoleResult = bean.getCurHoleResult();
							CurHole = bean.getCurHole();
							
							map.clear();
							DbHandle db = new DbHandle();
							
							if (CompetitorID != null && !CompetitorID.equals("") ) {
								
								db.insert("infoTable", new String[]{"CompetitorID","CompetitorCode","CompetitorName","CompetitorGroup","Tee","CurHole","CurHolePar","CurHoleResult","status"}, new String[]{CompetitorID,CompetitorCode,CompetitorName,CompetitorGroup,Tee,CurHole,CurHolePar,CurHoleResult,"未录入"});	
		
							} else {
								isSuccess = false;
								Toast.makeText(GroupActivity.this, "服务器异常，请稍后再试！",
										Toast.LENGTH_LONG).show();
								break;
							}
						}
						
						if (isSuccess) {
							Intent intent = new Intent(GroupActivity.this,ScoreActivity.class);
							startActivity(intent);
							
						} 
						
					}
					break;
				}
			}else {
				Toast.makeText(GroupActivity.this, "请求发送失败，请稍后再试！",
						Toast.LENGTH_LONG).show();
			}
			;
		}
	};
}

		




