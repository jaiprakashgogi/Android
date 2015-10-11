package com.example.proctordiary;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SemMarks extends Activity implements OnClickListener {

	private Button bShow, bUpdate;

	private String key_id, sem;
	private ArrayList<TextView> tvSub;
	private ArrayList<ArrayList<EditText>> et;
	private ArrayList<ArrayList<TextView>> tv;
	private ArrayList<ArrayList<String>> mMarks;
	private ArrayList<ArrayList<String>> rMarks;
	private final static int SIZE_EXAM = 5;
	private final static int SIZE_SEM = 6;
	private final static int SIZE_SUB = 8;
	private final static int SIZE_TV = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sem2);
		initialize();
		Bundle gotBasket = getIntent().getExtras();
		key_id = gotBasket.getString("key");
		sem = gotBasket.getString("sem");

		for (int i = 1; i < 9; i++) {
			String sub_id = String.format("sub%d", i);
			String val = gotBasket.getString(sub_id);
			tvSub.get(i - 1).setText(val);
		}
		bShow = (Button) findViewById(R.id.bShow);
		bUpdate = (Button) findViewById(R.id.bUpdate);
		bShow.setOnClickListener(this);
		bUpdate.setOnClickListener(this);
	}

	private void initialize() {
		// TODO Auto-generated method stub
		tvSub = new ArrayList<TextView>();
		for (int sub = 1; sub < SIZE_SUB + 1; sub++) {
			String buttonID = "tvSub" + sub;
			int resID = getResources().getIdentifier(buttonID, "id",
					"com.example.proctordiary");
			tvSub.add((TextView) findViewById(resID));
		}

		et = new ArrayList<ArrayList<EditText>>();
		for (int exam = 0; exam < SIZE_EXAM; exam++) {
			et.add(new ArrayList<EditText>());
			for (int sub = 1; sub < SIZE_SUB + 1; sub++) {
				String buttonID = "etSub" + sub + "Int" + exam;
				int resID = getResources().getIdentifier(buttonID, "id",
						"com.example.proctordiary");
				// Log.e("JAI", "resourceid is: "+buttonID + " "+resID );
				et.get(exam).add((EditText) findViewById(resID));
			}
		}

		tv = new ArrayList<ArrayList<TextView>>();
		for (int exam = 0; exam < SIZE_TV; exam++) {
			tv.add(new ArrayList<TextView>());
			for (int sub = 1; sub < SIZE_SUB + 1; sub++) {
				String buttonID = "tvSub" + sub + "Int" + exam;
				int resID = getResources().getIdentifier(buttonID, "id",
						"com.example.proctordiary");
				// Log.e("JAI", "resourceid is: "+buttonID + " "+resID );
				tv.get(exam).add((TextView) findViewById(resID));
			}
		}

		mMarks = new ArrayList<ArrayList<String>>();
		for (int exam = 0; exam < SIZE_EXAM; exam++) {
			mMarks.add(new ArrayList<String>());
			for (int sub = 0; sub < SIZE_SUB; sub++) {
				mMarks.get(exam).add(null);
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bShow:
			SqlDataBase ent = new SqlDataBase(this);
			ent.open();
			ent.getMarks(mMarks, key_id, sem);
			ent.close();
			for (int exam = 0; exam < SIZE_EXAM; exam++) {
				for (int sub = 0; sub < SIZE_SUB; sub++) {
					et.get(exam).get(sub).setText(mMarks.get(exam).get(sub));
				}
			}
			Toast toast = Toast.makeText(this, "Marks shown from DataBase",
					Toast.LENGTH_SHORT);
			toast.show();
			break;
		case R.id.bUpdate:
			getMarksFromEt(et);
			SqlDataBase entry = new SqlDataBase(this);
			entry.open();
			entry.updateMarks(mMarks, key_id, sem);
			entry.close();
			break;
		}
	}

	private void getMarksFromEt(ArrayList<ArrayList<EditText>> et2) {
		// TODO Auto-generated method stub
		for (int exam = 0; exam < SIZE_EXAM; exam++) {
			for (int sub = 0; sub < SIZE_SUB; sub++) {
				(mMarks.get(exam)).set(sub, ((et2.get(exam)).get(sub))
						.getText().toString());
			}
		}
		return;
	}

}
