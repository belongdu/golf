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

public class GroupActivity extends BaseActivity implements OnClickListener{
	EditText et ;
	String hole;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group);
		Button exit = (Button) findViewById(R.id.groupBack);
		Button sure = (Button) findViewById(R.id.sure_group);
		exit.setOnClickListener(this);
		sure.setOnClickListener(this);
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void onResume() {
		super.onResume();
		et = (EditText) findViewById(R.id.group_id);
		et.setText("");
		Map map = new DbHandle().selectOneRecord("paramTable", new String[]{"paraValue"}, "paraName = ?", new String[]{"hole"}, null, null, null);
		
		hole = (String) map.get("paraValue");
		TextView tv1 = (TextView) findViewById(R.id.hole_info);
		tv1.setText("当前录入"+hole+"号洞成绩");
		
		map = new DbHandle().selectOneRecord("paramTable", new String[]{"paraValue"}, "paraName = ?", new String[]{"matchName"}, null, null, null);
		String matchName = (String) map.get("paraValue");
		TextView tv2 = (TextView) findViewById(R.id.match_info);
		tv2.setText("赛事信息："+matchName);
	}

	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.groupBack) {
			finish();
		} else if (v.getId() == R.id.sure_group) {
			
			if (et.getText() != null && !et.getText().equals("")) {
				String groupId = et.getText().toString();
				GroupReqBean reqBean = new GroupReqBean();
				reqBean.setGroupId(groupId);
				
				new DbHandle().insert("paramTable", new String[]{"paraName","paraValue"}, new String[]{"groupId",groupId});

				
				DispatchRequest.doHttpRequest(ProtocolConstants.MESSID_USR_GROUP, reqBean, handler, GroupRespBean.class);
				
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
						
						String userId = null;
						String userName = null;
						boolean isSuccess = true;
						EditText et = (EditText) findViewById(R.id.group_id);
						String groupId = et.getText().toString();
						Map map = new HashMap();
						for (int i = 0; i <respList.size(); i++) {
							userId = ((GroupRespBean)respList.get(i)).getUserId();
							userName = ((GroupRespBean)respList.get(i)).getUserName();
							map.clear();
							DbHandle db = new DbHandle();
							
							if (userId != null && !userId.equals("") &&userName != null && !userName.equals("")) {
								
								db.insert("infoTable", new String[]{"groupId","hole","userId","name","status"}, new String[]{groupId,hole,userId,userName,"未录入"});	
		
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
							finish();
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
