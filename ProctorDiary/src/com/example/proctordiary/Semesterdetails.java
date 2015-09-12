package com.example.proctordiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Semesterdetails extends Activity implements OnClickListener {

	private Button bSem1, bSem2, bSem3, bSem4, bSem5, bSem6;
	private String key_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.semesterdetails);
		Bundle gotBasket = getIntent().getExtras();
		key_id = gotBasket.getString("key");

		bSem1 = (Button) findViewById(R.id.bSem1);
		bSem2 = (Button) findViewById(R.id.bSem2);
		bSem3 = (Button) findViewById(R.id.bSem3);
		bSem4 = (Button) findViewById(R.id.bSem4);
		bSem5 = (Button) findViewById(R.id.bSem5);
		bSem6 = (Button) findViewById(R.id.bSem6);
		bSem1.setOnClickListener(this);
		bSem2.setOnClickListener(this);
		bSem3.setOnClickListener(this);
		bSem4.setOnClickListener(this);
		bSem5.setOnClickListener(this);
		bSem6.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Bundle basket = new Bundle();
		basket.putString("key", key_id);
		switch (v.getId()) {
		case R.id.bSem1:
			/*
			 * basket.putString("sub1", "Sem1Sub1"); basket.putString("sub2",
			 * "Sem1Sub2"); basket.putString("sub3", "Sem1Sub3");
			 * basket.putString("sub4", "Sem1Sub4"); basket.putString("sub5",
			 * "Sem1Sub5"); basket.putString("sub6", "Sem1Sub6");
			 * basket.putString("sub7", "Sem1Sub7"); basket.putString("sub8",
			 * "Sem1Sub8");
			 */
			basket.putString("sem", "0");
			for (int i = 1; i < 9; i++) {
				String key = String.format("sub%d", i);
				String val = String.format("Sem1Sub%d", i);
				basket.putString(key, val);
			}
			break;
		case R.id.bSem2:
			basket.putString("sem", "1");
			for (int i = 1; i < 9; i++) {
				String key = String.format("sub%d", i);
				String val = String.format("Sem2Sub%d", i);
				basket.putString(key, val);
			}
			break;
		case R.id.bSem3:
			basket.putString("sem", "2");
			for (int i = 1; i < 9; i++) {
				String key = String.format("sub%d", i);
				String val = String.format("Sem3Sub%d", i);
				basket.putString(key, val);
			}
			break;
		case R.id.bSem4:
			basket.putString("sem", "3");
			for (int i = 1; i < 9; i++) {
				String key = String.format("sub%d", i);
				String val = String.format("Sem4Sub%d", i);
				basket.putString(key, val);
			}
			break;
		case R.id.bSem5:
			basket.putString("sem", "4");
			for (int i = 1; i < 9; i++) {
				String key = String.format("sub%d", i);
				String val = String.format("Sem5Sub%d", i);
				basket.putString(key, val);
			}
			break;
		case R.id.bSem6:
			basket.putString("sem", "5");
			for (int i = 1; i < 9; i++) {
				String key = String.format("sub%d", i);
				String val = String.format("Sem6Sub%d", i);
				basket.putString(key, val);
			}
			break;
		}
		Intent i = new Intent(this, SemMarks.class);
		i.putExtras(basket);
		startActivity(i);
	}

}
