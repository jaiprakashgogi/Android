package com.example.proctordiary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProctorActivity extends Activity implements View.OnClickListener {
	private Button bLogin, bNewUser, bCancel;
	private EditText etUserName, etPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login);

		etUserName = (EditText) findViewById(R.id.etUserName);
		etPassword = (EditText) findViewById(R.id.etPassword);

		bLogin = (Button) findViewById(R.id.bLogin);
		bLogin.setOnClickListener(this);

		bCancel = (Button) findViewById(R.id.bCancel);
		bCancel.setOnClickListener(this);

		bNewUser = (Button) findViewById(R.id.bNewUser);
		bNewUser.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bLogin:
			String username = etUserName.getText().toString();
			String password = etPassword.getText().toString();
			SqlDataBase db = new SqlDataBase(this);
			db.open();
			String key_id = db.validateUser(username, password);
			db.close();
			if (key_id != null) {
				Bundle basket = new Bundle();
				basket.putString("key", key_id);
				Intent i = new Intent(this, Login.class);
				i.putExtras(basket);
				startActivity(i);
				Toast toast = Toast.makeText(getApplicationContext(),
						"Key_id is " + key_id, Toast.LENGTH_SHORT);
				toast.show();
			} else {
				Toast toast = Toast
						.makeText(
								getApplicationContext(),
								"Invalid Username or Password \nPlease Register if new user",
								Toast.LENGTH_SHORT);
				toast.show();
			}

			break;

		/*
		 * case R.id.bCancel: Intent j = new Intent(this, Login.class);
		 * startActivity(j); break;
		 */

		case R.id.bNewUser:
			Intent k = new Intent(this, NewUser.class);
			startActivity(k);
			break;

		}
	}
}
