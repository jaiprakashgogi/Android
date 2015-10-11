package com.app.samplewidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MainActivity extends AppWidgetProvider {

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
		Toast.makeText(context, "onDeleted called",Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		Intent in = new Intent(context,/*WidgetConfig*/ChatThread.class);
		PendingIntent pi = PendingIntent.getService(context, 0, in, 0);
		/*PendingIntent pi = PendingIntent.getActivity(context, 0, in, 0);*/
		
		final int N = appWidgetIds.length;
		for(int i=0; i<N ; i++){
			int awId = appWidgetIds[i]; 
			RemoteViews v = new RemoteViews(context.getPackageName(), R.layout.widget);
			v.setOnClickPendingIntent(R.id.bwidgetOpen, pi);
			appWidgetManager.updateAppWidget(awId, v);
		}
		
	}


}


