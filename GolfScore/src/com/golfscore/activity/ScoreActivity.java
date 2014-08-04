package com.golfscore.activity;

import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.golfscore.R;
import com.golfscore.activity.base.BaseActivity;
import com.golfscore.db.DbHandle;

public class ScoreActivity  extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score);
		Button button = (Button) findViewById(R.id.score_back);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void onResume() {
		super.onResume();
		
		TextView tv = (TextView) findViewById(R.id.score_title);
				
		DbHandle db = new DbHandle();
		String hole = null;
		String groupId = null;
		Map map = db.selectOneRecord("paramTable", new String[]{"paraValue"}, "paraName = ?", new String[]{"hole"}, null, null, null);
		if (map != null && map.size()>0) {			
			hole = (String) map.get("paraValue");
		}
		
		map = db.selectOneRecord("paramTable", new String[]{"paraValue"}, "paraName = ?", new String[]{"groupId"}, null, null, null);
		if (map != null && map.size()>0) {			
			groupId = (String) map.get("paraValue");
		}
		tv.setText(hole +"ºÅ¶´µÚ" + groupId +"×é");
		
		
		ListView lv = (ListView) findViewById(R.id.listView);
		ScoreAdapter adapter = new ScoreAdapter(this,hole,groupId);
		lv.setAdapter(adapter);
	}
	
}
