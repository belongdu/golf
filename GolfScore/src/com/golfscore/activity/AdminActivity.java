package com.golfscore.activity;


import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.golfscore.R;
import com.golfscore.activity.base.BaseActivity;
import com.golfscore.db.DbHandle;

public class AdminActivity extends BaseActivity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin);

		Button hb1 = (Button) findViewById(R.id.hb1);
		Button hb2 = (Button) findViewById(R.id.hb2);
		Button hb3 = (Button) findViewById(R.id.hb3);
		Button hb4 = (Button) findViewById(R.id.hb4);
		Button hb5 = (Button) findViewById(R.id.hb5);
		Button hb6 = (Button) findViewById(R.id.hb6);
		Button hb7 = (Button) findViewById(R.id.hb7);
		Button hb8 = (Button) findViewById(R.id.hb8);
		Button hb9 = (Button) findViewById(R.id.hb9);
		Button hb10 = (Button) findViewById(R.id.hb10);
		Button hb11 = (Button) findViewById(R.id.hb11);
		Button hb12 = (Button) findViewById(R.id.hb12);
		Button hb13 = (Button) findViewById(R.id.hb13);
		Button hb14 = (Button) findViewById(R.id.hb14);
		Button hb15 = (Button) findViewById(R.id.hb15);
		Button hb16 = (Button) findViewById(R.id.hb16);
		Button hb17 = (Button) findViewById(R.id.hb17);
		Button hb18 = (Button) findViewById(R.id.hb18);
		Button back_bt = (Button) findViewById(R.id.back_bt);

		hb1.setOnClickListener(this);
		hb2.setOnClickListener(this);
		hb3.setOnClickListener(this);
		hb4.setOnClickListener(this);
		hb5.setOnClickListener(this);
		hb6.setOnClickListener(this);
		hb7.setOnClickListener(this);
		hb8.setOnClickListener(this);
		hb9.setOnClickListener(this);
		hb10.setOnClickListener(this);
		hb11.setOnClickListener(this);
		hb12.setOnClickListener(this);
		hb13.setOnClickListener(this);
		hb14.setOnClickListener(this);
		hb15.setOnClickListener(this);
		hb16.setOnClickListener(this);
		hb17.setOnClickListener(this);
		hb18.setOnClickListener(this);
		back_bt.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.hb1:
			dialog("1");
			break;
		case R.id.hb2:
			dialog("2");
			break;
		case R.id.hb3:
			dialog("3");
			break;
		case R.id.hb4:
			dialog("4");
			break;
		case R.id.hb5:
			dialog("5");
			break;
		case R.id.hb6:
			dialog("6");
			break;
		case R.id.hb7:
			dialog("7");
			break;
		case R.id.hb8:
			dialog("8");
			break;
		case R.id.hb9:
			dialog("9");
			break;
		case R.id.hb10:
			dialog("10");
			break;
		case R.id.hb11:
			dialog("11");
			break;
		case R.id.hb12:
			dialog("12");
			break;
		case R.id.hb13:
			dialog("13");
			break;
		case R.id.hb14:
			dialog("14");
			break;
		case R.id.hb15:
			dialog("15");
			break;
		case R.id.hb16:
			dialog("16");
			break;
		case R.id.hb17:
			dialog("17");
			break;
		case R.id.hb18:
			dialog("18");
			break;
		case R.id.back_bt:
			finish();
			return;
		}

	}

	protected void dialog(final String hole) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("已经选"+hole+"号洞，点击确定返回登录页");
		builder.setTitle("提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				new DbHandle().insert("paramTable", new String[]{"paraName","paraValue"}, new String[]{"hole",hole});
				finish();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
}
