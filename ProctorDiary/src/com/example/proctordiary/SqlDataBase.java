package com.example.proctordiary;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class SqlDataBase {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_USER = "user_name";
	public static final String KEY_USN = "user_usn";
	public static final String KEY_FATHERNAME = "user_father";
	public static final String KEY_EMAIL = "user_email";
	public static final String KEY_PHONE = "user_phone";

	public static final String DATABASE_NAME = "UserDb";
	public static final String DATABASE_TABLE = "UserTable";
	public static final int DATABASE_VERSION = 1;

	private final static int SIZE_SEM = 6;
	private final static int SIZE_SUB = 8;
	private final static int SIZE_EXAM = 5;

	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String result = "";
			for (int sem = 0; sem < SIZE_SEM; sem++) {
				for (int exam = 0; exam < SIZE_EXAM; exam++) {
					for (int sub = 0; sub < SIZE_SUB; sub++) {
						result = result + "KEY_SEM" + sem + "_EXAM" + exam
								+ "_SUB" + sub + " TEXT , ";
					}
				}
			}
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_USER
					+ " TEXT NOT NULL, " + KEY_USN + " TEXT NOT NULL, "
					+ KEY_FATHERNAME + " TEXT NOT NULL, " + KEY_EMAIL
					+ " TEXT NOT NULL, " + result + KEY_PHONE
					+ " TEXT NOT NULL);");

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}

	}

	public SqlDataBase(Context c) {
		ourContext = c;
	}

	public SqlDataBase open() throws SQLException {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		ourHelper.close();
	}

	public long createEntry(String user_name, String user_usn) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_USER, user_name);
		cv.put(KEY_USN, user_usn);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}

	public String getData() {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID, KEY_USER, KEY_USN };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);
		String result = "";
		int iRow = c.getColumnIndex(KEY_ROWID);
		int iName = c.getColumnIndex(KEY_USER);
		int iUsn = c.getColumnIndex(KEY_USN);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(iRow) + " " + c.getString(iName)
					+ " " + c.getString(iUsn) + "\n";
		}

		return result;
	}

	public String getRegisteredUsers() {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID, KEY_USER };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, KEY_USER);
		// Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null,
		// null, null, null);
		String result = "";
		int iName = c.getColumnIndex(KEY_USER);
		int i = 1;

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + i++ + " - " + c.getString(iName) + "\n";
		}

		return result;
	}

	public String getBasicInfoUser(long key) {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID, KEY_USER, KEY_USN,
				KEY_FATHERNAME, KEY_EMAIL, KEY_PHONE };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "="
				+ key, null, null, null, null);
		String result = "";
		int iKey = c.getColumnIndex(KEY_ROWID);
		int iUser = c.getColumnIndex(KEY_USER);
		int iUsn = c.getColumnIndex(KEY_USN);
		int iFather = c.getColumnIndex(KEY_FATHERNAME);
		int iEmail = c.getColumnIndex(KEY_EMAIL);
		int iPhone = c.getColumnIndex(KEY_PHONE);

		if (c != null) {
			c.moveToFirst();
			result = result + c.getString(iKey) + "\nname: "
					+ c.getString(iUser) + "\nUSN: " + c.getString(iUsn)
					+ "\nFather's Name: " + c.getString(iFather) + "\nEmail: "
					+ c.getString(iEmail) + "\nPhone: " + c.getString(iPhone);
		}
		return result;
	}

	public String validateUser(String username, String password) {
		String usn = null;
		String[] columns = new String[] { KEY_ROWID, KEY_USER, KEY_USN };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);
		int iRow = c.getColumnIndex(KEY_ROWID);
		int iName = c.getColumnIndex(KEY_USER);
		int iUsn = c.getColumnIndex(KEY_USN);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			if (c.getString(iName).contentEquals(username)
					&& c.getString(iUsn).contentEquals(password)) {
				usn = c.getString(iRow);
				break;
			}
		}

		return usn;
	}

	public long createEntry(String[] user_data) {
		// TODO Auto-generated method stub
		// Log.e("JAI", " Length if userdata is "+ user_data.length);
		ContentValues cv = new ContentValues();
		if (user_data.length == 5) {
			cv.put(KEY_USER, user_data[0]);
			cv.put(KEY_USN, user_data[1]);
			cv.put(KEY_FATHERNAME, user_data[2]);
			cv.put(KEY_EMAIL, user_data[3]);
			cv.put(KEY_PHONE, user_data[4]);
		} else {
			Toast toast = Toast.makeText(ourContext, "Fill all details",
					Toast.LENGTH_SHORT);
			toast.show();
		}
		return ourDatabase.insert(DATABASE_TABLE, null, cv);

	}

	public void updateMarks(ArrayList<ArrayList<String>> mMarks, String key_id,
			String sem) {
		long keyID = Long.parseLong(key_id);
		ContentValues cv = new ContentValues();
		for (int exam = 0; exam < SIZE_EXAM; exam++) {
			for (int sub = 0; sub < SIZE_SUB; sub++) {
				String key = "KEY_SEM" + sem + "_EXAM" + exam + "_SUB" + sub;
				cv.put(key, mMarks.get(exam).get(sub));
			}
		}
		ourDatabase.update(DATABASE_TABLE, cv, KEY_ROWID + "=" + keyID, null);
		Toast toast = Toast.makeText(ourContext, "Marks Updated",
				Toast.LENGTH_SHORT);
		toast.show();

		return;
	}

	public void getMarks(ArrayList<ArrayList<String>> mMarks, String key_id,
			String sem) {
		// TODO Auto-generated method stub

		String[] columns = new String[41];
		columns[0] = key_id;

		for (int exam = 0; exam < SIZE_EXAM; exam++) {
			for (int sub = 0; sub < SIZE_SUB; sub++) {
				columns[exam * SIZE_SUB + sub + 1] = "KEY_SEM" + sem + "_EXAM"
						+ exam + "_SUB" + sub;
			}
		}

		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "="
				+ key_id, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
			for (int exam = 0; exam < SIZE_EXAM; exam++) {
				for (int sub = 0; sub < SIZE_SUB; sub++) {
					mMarks.get(exam).set(
							sub,
							c.getString(c.getColumnIndex("KEY_SEM" + sem
									+ "_EXAM" + exam + "_SUB" + sub)));
				}
			}

		}
		return;

	}

}
