package com.example.proctordiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Login extends Activity implements OnClickListener {

	private TextView tv;
	private String key_id;
	private Button bModify;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqlview);
		tv = (TextView) findViewById(R.id.tvDisplayDB);
		bModify = (Button) findViewById(R.id.bModify);

		Bundle gotBasket = getIntent().getExtras();
		key_id = gotBasket.getString("key");
		Log.e("JAI", "Key is " + key_id);
		long key = Long.parseLong(key_id);
		SqlDataBase info = new SqlDataBase(this);
		info.open();
		String data = info.getBasicInfoUser(key);
		info.close();
		Log.e("JAI", "data is : " + data);
		tv.setText(data);
		bModify.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.bModify) {
			Bundle basket = new Bundle();
			basket.putString("key", key_id);
			Intent i = new Intent(this, Semesterdetails.class);
			i.putExtras(basket);
			startActivity(i);
		}

	}

}
