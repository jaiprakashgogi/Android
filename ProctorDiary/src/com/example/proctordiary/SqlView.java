package com.example.proctordiary;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SqlView extends Activity {

	TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqlview);
		tv = (TextView) findViewById(R.id.tvDisplayDB);
		SqlDataBase info = new SqlDataBase(this);
		info.open();
		// String data = info.getData();
		String data = info.getRegisteredUsers();
		info.close();
		tv.setText(data);
	}

}
