package com.golfscore.activity;

import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
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
import com.golfscore.protocol.bean.response.LoginRespBean;
import com.golfscore.protocol.bean.resquest.LoginReqBean;
import com.golfscore.protocol.constants.ProtocolConstants;

/**
 * 

 * @Description:��¼��ҳ 

 * @author:dw

 * @time:2014-8-1 ����10:44:18
 */
public class LoginActivity extends BaseActivity implements OnClickListener{
	EditText et1 = null;
	EditText et2 = null;
	
	private String hole;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		 
		Button bt = (Button) findViewById(R.id.button1);
		bt.setOnClickListener(this);		
		
		et1 = (EditText) findViewById(R.id.editText1);
		et2 = (EditText) findViewById(R.id.editText2);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		et1.setText("");
		et2.setText("");
		
		Map map = new DbHandle().selectOneRecord("paramTable", new String[]{"paraValue"}, "paraName = ?", new String[]{"hole"}, null, null, null);
		if (map != null && map.size()>0) {			
			hole = (String) map.get("paraValue");
			TextView tv = (TextView) findViewById(R.id.editText3);
			tv.setText(hole);
		}
	}




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
	
		if (v.getId() == R.id.button1) {
			
			String userName = et1.getText().toString();
			String password = et2.getText().toString();
			if (userName == null || userName.equals("")) {
				Toast.makeText(this, "�û�������Ϊ��", Toast.LENGTH_SHORT).show();
				return;
			}
			
			if (password == null || password.equals("")) {
				Toast.makeText(this, "���벻��Ϊ��", Toast.LENGTH_SHORT).show();
				return ;
			}

			if (userName.equals("admin") && password.equals("123456")) { //��������Ա
				Intent intent = new Intent(this,AdminActivity.class);
				startActivity(intent);
			} else {
				if (hole == null) {
					Toast.makeText(this, "���ȵ�¼����Ա�˻�ѡ����", Toast.LENGTH_SHORT).show();
				} else {
					LoginReqBean reqBean = new LoginReqBean();
					reqBean.setStrUser(userName);
					reqBean.setStrPwd(password);
					DispatchRequest.doHttpRequest(ProtocolConstants.USR_LOGIN, reqBean, handler, LoginRespBean.class);
					
				}
			}
			
		}
		
	}

	private Handler handler = new Handler() {
		@SuppressWarnings({ "unchecked" })
		public void handleMessage(Message msg) {
			if (msg.obj != null) {
				switch (msg.what) {
				case ProtocolConstants.MESSID_USR_LOGIN: // �û���¼���ؽ��
					
					List<ResponseBean> respList = (List<ResponseBean>) msg.obj;
					if (!respList.get(0).getResult().equals("0")) {
						Toast.makeText(LoginActivity.this, "�û����������",
								Toast.LENGTH_LONG).show();
					}else{
						String MatchID = ((LoginRespBean)respList.get(0)).getMatchID();
						String MatchKey = ((LoginRespBean)respList.get(0)).getMatchKey();
						String MatchDate = ((LoginRespBean)respList.get(0)).getMatchDate();
						String MatchName = ((LoginRespBean)respList.get(0)).getMatchName();
						String MatchStatus = ((LoginRespBean)respList.get(0)).getMatchStatus();
						
						if (MatchID != null && !MatchID.equals("")) {
							
							DbHandle db = new DbHandle();
							db.insert("paramTable", new String[]{"paraName","paraValue"}, new String[]{"matchId",MatchID});	
							db.insert("paramTable", new String[]{"paraName","paraValue"}, new String[]{"matchName",MatchName});
							db.insert("paramTable", new String[]{"paraName","paraValue"}, new String[]{"matchKey",MatchKey});
							db.insert("paramTable", new String[]{"paraName","paraValue"}, new String[]{"matchDate",MatchDate});
							db.insert("paramTable", new String[]{"paraName","paraValue"}, new String[]{"matchStatus",MatchStatus});
							
							Intent intent = new Intent(LoginActivity.this,GroupActivity.class);
							startActivity(intent);
							
						} else {
							Toast.makeText(LoginActivity.this, "�������쳣�����Ժ����ԣ�",
									Toast.LENGTH_LONG).show();
						}
					}
					break;
				}
			}else {
				Toast.makeText(LoginActivity.this, "������ʧ�ܣ����Ժ����ԣ�",
						Toast.LENGTH_LONG).show();
			}
			;
		}
	};
}
