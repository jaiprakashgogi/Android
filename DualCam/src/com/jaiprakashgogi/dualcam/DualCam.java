package com.jaiprakashgogi.dualcam;

import android.app.Activity;
import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.TextureView;
import android.view.Window;
import android.view.WindowManager;

public class DualCam extends Activity {

	private TextureView mRearPrev = null;
	private TextureView mFrontPrev = null;
	MyCamera mFrontCamera = null;
	MyCamera mRearCamera = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_dual_cam);
		
		CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

		mRearPrev = (TextureView) findViewById(R.id.tv1);
		mRearCamera = new MyCamera(this,mRearPrev, manager, 1);
		mRearPrev.setSurfaceTextureListener(mRearCamera);
		
/*		mFrontPrev = (TextureView) findViewById(R.id.tv2);
		mFrontCamera = new MyCamera(this,mFrontPrev, manager, 1);
		mFrontPrev.setSurfaceTextureListener(mFrontCamera);*/

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mRearCamera.stop();
/*		mFrontCamera.stop();*/
	}

}
