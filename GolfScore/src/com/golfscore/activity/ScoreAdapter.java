package com.golfscore.activity;

import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.golfscore.R;
import com.golfscore.db.DbHandle;
import com.golfscore.protocol.DispatchRequest;
import com.golfscore.protocol.bean.ResponseBean;
import com.golfscore.protocol.bean.response.ScoreRespBean;
import com.golfscore.protocol.bean.resquest.ScoreReqBean;
import com.golfscore.protocol.constants.ProtocolConstants;

public class ScoreAdapter extends BaseAdapter{
	

	private DbHandle db = new DbHandle();
	private String hole;
	private String groupId;
	private List<Map<String,String>> list;
	private Map<String,String> data;
	private Context context;
	
	public ScoreAdapter(Context context,String hole, String groupId,List<Map<String,String>> list){
		super();
		this.context = context;
		this.hole = hole;
		this.groupId = groupId;
		this.list = list;
	}
	
	@Override
	public int getCount() {
		
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		
		return null;
	}

	@Override
	public long getItemId(int position) {
		
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		try{
			if (convertView == null) {			
				convertView = LayoutInflater.from(context).inflate(R.layout.adapter, null);
			}
			TextView name = (TextView) convertView.findViewById(R.id.user_name);
			TextView status = (TextView) convertView.findViewById(R.id.score_status);
			final EditText scoreET = (EditText) convertView.findViewById(R.id.user_score);
			Button submit = (Button) convertView.findViewById(R.id.score_submit);
			
//			getData();
			
			data= list.get(position);
			
			name.setText(data.get("CompetitorName"));
			status.setText(data.get("status"));
			if (data.get("CurHoleResult") != null && !data.get("CurHoleResult").equals("")&& !data.get("CurHoleResult").equals("0")) {
				scoreET.setText(data.get("CurHoleResult"));
			}
			final String userId = data.get("CompetitorID");
			submit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String score = scoreET.getText().toString();
					if( score!= null &&  !score.equals("") &&!score.equals("0")) {
						AlertDialog.Builder builder = new Builder(context);
						builder.setMessage(data.get("CompetitorName")+"的" +hole+"号洞成绩是：" +score +"。提交点击确定，重新录入点击取消");
						builder.setTitle("提示");
						builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							
							@SuppressWarnings("rawtypes")
							@Override
							public void onClick(DialogInterface dialog, int which) {
								
								ScoreReqBean reqBean = new ScoreReqBean();
								reqBean.setIHoleNum(hole);
								String score = scoreET.getText().toString();
								reqBean.setIHoleResult(score);
								Map map = new DbHandle().selectOneRecord("paramTable", new String[]{"paraValue"}, "paraName = ?", new String[]{"matchId"}, null, null, null);
								String iMatchID = (String) map.get("paraValue");
								reqBean.setIMatchID(iMatchID);
								reqBean.setIRegisterID(userId);
								
								DispatchRequest.submitScore(ProtocolConstants.USR_SCORE, reqBean, handler, ScoreRespBean.class,hole+"#"+userId);
								db.update("infoTable", new String[]{"CurHoleResult","status"}, new String[]{score,"正在提交"}, "CompetitorID = ? and CurHole = ?", new String[]{userId,hole});
								notifyDataSetChanged();
							}
						});
						builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
						builder.create().show();
					} else {
						Toast.makeText(context, "请先输入成绩",
								Toast.LENGTH_LONG).show();
					}
					

				}
			});
		} catch(Exception e) {
			
		}

		
		return convertView;
	}

	
	private void getData(){
		
		list = db.select( "infoTable",new String[]{"CompetitorGroup", "CurHole","CompetitorID", "Tee","CompetitorName", "CurHoleResult", "status"}, "CompetitorGroup=? and CurHole=?", new String[]{groupId,hole}, null, null, "Tee");
		
	}
	private Handler handler = new Handler() {
		@SuppressWarnings({ "unchecked" })
		public void handleMessage(Message msg) {
			try{
				if (msg.obj != null) {
					Bundle bundle = msg.getData();
					String flag =bundle.getString("flag");
					String[] arr = flag.split("#");
					switch (msg.what) {
					case ProtocolConstants.MESSID_USR_SCORE: // 提交成绩结果
						
						List<ResponseBean> respList = (List<ResponseBean>) msg.obj;
						if (!respList.get(0).getResult().equals("0")) {
							db.update("infoTable", new String[]{"status"}, new String[]{"提交中"}, "CurHole = ? and CompetitorID = ?", new String[]{arr[0],arr[1]});
							Toast.makeText(context, "提交失败",
									Toast.LENGTH_LONG).show();
						}else{
							
							db.update("infoTable", new String[]{"status"}, new String[]{"已提交"}, "CurHole = ? and CompetitorID = ?", new String[]{arr[0],arr[1]});	
						
							notifyDataSetChanged();
						}
						break;
					}
				}else {
					Toast.makeText(context, "请求发送失败，请稍后再试！",
							Toast.LENGTH_LONG).show();
				}
				;
			} catch(Exception e) {
				
			}

		}
	};
}
