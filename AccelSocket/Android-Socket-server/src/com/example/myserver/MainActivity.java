package com.example.myserver;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {
	String mClientMsg = "";
	Thread myCommsThread = null;
	public static final String TAG = "SocketServer";
	private CommsThread commsThread = null;
	TextView tv;
	PrintWriter out;
	private float ax, ay, az;
	private long timenow = 0, timeprev = 0, timestamp =0 ;

	private SensorManager sm;
	private Sensor sensor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView) findViewById(R.id.textView1);
		sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensor = sm.getSensorList(Sensor.TYPE_LINEAR_ACCELERATION).get(0);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
		this.commsThread = new CommsThread();
		this.myCommsThread = new Thread(this.commsThread);
		this.myCommsThread.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		sm.unregisterListener(this);
		if (commsThread != null) {
			commsThread.stopComms();
		}
	}
	
	Handler myHandler = new Handler(){
		public void handleMessage(Message msg){
			TextView status = (TextView) findViewById(R.id.textView3);
			status.setText("Status: Streaming Now!");
		}
	};

	class CommsThread implements Runnable {
		private volatile boolean stopFlag = false;
		private ServerSocket ss = null;
		private static final int SERVERPORT = 6000;
		public void run() {
			Socket s = null;
			try {
				ss = new ServerSocket(SERVERPORT);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				s = ss.accept();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			myHandler.sendEmptyMessage(0);
			
			while(!stopFlag){
				try {
					out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);
					out.printf("*#%3.2f#%3.2f#%3.2f#%2d#*\n",ax,ay,az,(int)timestamp );
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		public void stopComms() {
			// TODO Auto-generated method stub
			this.stopFlag = true;
			if(ss != null){
				try {
					ss.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		ax = event.values[0];
		ay = event.values[1];
		az = event.values[2];
		timenow = event.timestamp;
		timestamp = (timenow - timeprev)/1000000;
		refreshDisplay();
	}

	private void refreshDisplay() {
		// TODO Auto-generated method stub
		String output = String.format("time: %d -- x:%03.2f | Y:%03.2f | Z:%03.2f", timestamp, ax,ay,az);
		timeprev = timenow;
		tv.setText(output);
	}

}
