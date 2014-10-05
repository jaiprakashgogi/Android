package com.app.samplewidget;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;

public class WidgetConfig extends Activity implements OnClickListener{
	AppWidgetManager awm;
	EditText info;
	Context c;
	int awId;
	
	public static final String TAG = "Widgetconfig"; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widgetconfig);
		Button button = (Button) findViewById(R.id.bApp);
		button.setOnClickListener(this);
		c = WidgetConfig.this;
		
		info = (EditText) findViewById(R.id.eApp);
		
		Intent i = getIntent();
		Bundle extras = i.getExtras();
		if (extras != null){
			awId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
		}
		awm = AppWidgetManager.getInstance(c);
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String e = info.getText().toString();
		Log.i(TAG, "JAI -- " + e);
		RemoteViews views = new RemoteViews(c.getPackageName(),R.layout.widget);
		/*views.setTextViewText(R.id.tvConfigInput, e);*/
		
		Intent in = new Intent(c,WidgetConfig.class);
		PendingIntent pi = PendingIntent.getActivity(c, 0, in, 0);
		views.setOnClickPendingIntent(R.id.bwidgetOpen, pi);
		
		Intent result = new Intent();
		result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, awId);
		setResult(RESULT_OK,result);
		
		awm.updateAppWidget(awId, views);
		finish();
		
	}
	

}
