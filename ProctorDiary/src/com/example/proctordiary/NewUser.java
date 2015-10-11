package com.example.proctordiary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NewUser extends Activity implements OnClickListener {

	private EditText etUser, etUsn, etNameFather, etEmail, etMobile;
	private Button bRegisterSign, bCancelSign;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newuser);

		etUser = (EditText) findViewById(R.id.etUser);
		etUsn = (EditText) findViewById(R.id.etUsn);
		etNameFather = (EditText) findViewById(R.id.etNameFather);
		etEmail = (EditText) findViewById(R.id.etEmail);
		etMobile = (EditText) findViewById(R.id.etMobile);

		bRegisterSign = (Button) findViewById(R.id.bRegisterSign);
		bCancelSign = (Button) findViewById(R.id.bCancelSign);
		bRegisterSign.setOnClickListener(this);
		bCancelSign.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bRegisterSign:
			boolean didItWork = true;
			try {
				if (etUser.getText().length() > 0
						&& etUsn.getText().length() > 0
						&& etNameFather.getText().length() > 0
						&& etEmail.getText().length() > 0
						&& etMobile.getText().length() > 0) {
					String[] user_data = { etUser.getText().toString(),
							etUsn.getText().toString(),
							etNameFather.getText().toString(),
							etEmail.getText().toString(),
							etMobile.getText().toString() };

					SqlDataBase entry = new SqlDataBase(NewUser.this);
					entry.open();
					entry.createEntry(user_data);
					entry.close();
				} else {
					didItWork = false;
					Toast toast = Toast.makeText(this, "Fill all details",
							Toast.LENGTH_SHORT);
					toast.show();
				}
			} catch (Exception e) {
				didItWork = false;
			} finally {
				if (didItWork) {
					Dialog d = new Dialog(this);
					d.setTitle("Registration Success!!");
					TextView tv = new TextView(this);
					tv.setText("Please go back and login");
					d.setContentView(tv);
					d.show();
				}
			}

			break;
		case R.id.bCancelSign:
			Intent k = new Intent(this, SqlView.class);
			startActivity(k);
			break;
		}

	}

}
