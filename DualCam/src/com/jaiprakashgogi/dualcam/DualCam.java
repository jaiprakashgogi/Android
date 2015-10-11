package com.jaiprakashgogi.dualcam;

import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraDevice.StateCallback;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.Window;
import android.view.WindowManager;

public class DualCam extends Activity {

	private static final String TAG = "DualCam";
	// private Size mPreviewSize = null;
	private CameraDevice mCameraDevice = null;
	private CaptureRequest.Builder mPreviewBuilder = null;
	private CameraCaptureSession mPreviewSession = null;
	private TextureView mTextureView = null;
	private Size mPreviewSize = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_dual_cam);
		mTextureView = (TextureView) findViewById(R.id.tv1);
		mTextureView.setSurfaceTextureListener(mTextureListener);
	}
	
	

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (mCameraDevice != null)
		{
			mCameraDevice.close();
			mCameraDevice = null;
		}
	}



	private SurfaceTextureListener mTextureListener = new SurfaceTextureListener() {

		@Override
		public void onSurfaceTextureUpdated(SurfaceTexture surface) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSurfaceTextureSizeChanged(SurfaceTexture surface,
				int width, int height) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onSurfaceTextureAvailable(SurfaceTexture surface,
				int width, int height) {
			// TODO Auto-generated method stub
			openCamera(width, height);
		}
	};
	
	private StateCallback mStateCallback = new StateCallback() {

		@Override
		public void onOpened(CameraDevice camera) {
			// TODO Auto-generated method stub
			mCameraDevice = camera;
			createCameraPreviewSession();
		}

		@Override
		public void onError(CameraDevice camera, int error) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onDisconnected(CameraDevice camera) {
			// TODO Auto-generated method stub

		}
	};

	protected void openCamera(int width, int height) {
		// TODO Auto-generated method stub
		CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
		try {
			String cameraId = manager.getCameraIdList()[0];
			CameraCharacteristics characteristics = manager
					.getCameraCharacteristics(cameraId);
			StreamConfigurationMap map = characteristics
					.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
			mPreviewSize = map.getOutputSizes(SurfaceTexture.class)[0];
			manager.openCamera(cameraId, mStateCallback, null);
		} catch (CameraAccessException e) {
			e.printStackTrace();
		}
	}
	
	private CameraCaptureSession.StateCallback mPreviewStateCallback = new CameraCaptureSession.StateCallback() {
		
		@Override
		public void onConfigured(CameraCaptureSession session) {
			// TODO Auto-generated method stub
			Log.i(TAG, "onConfigured");
			mPreviewSession = session;
			
			mPreviewBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
			
			HandlerThread backgroundThread = new HandlerThread("CameraPreview");
			backgroundThread.start();
			Handler backgroundHandler = new Handler(backgroundThread.getLooper());
			
			try {
				mPreviewSession.setRepeatingRequest(mPreviewBuilder.build(), null, backgroundHandler);
			} catch (CameraAccessException e) {
				e.printStackTrace();
			}
			
		}
		
		@Override
		public void onConfigureFailed(CameraCaptureSession session) {
			// TODO Auto-generated method stub
			Log.e(TAG, "CameraCaptureSession Configure failed");
		}
	};

	protected void createCameraPreviewSession() {
		// TODO Auto-generated method stub
		Log.i(TAG, "createCameraPreviewSession");
		SurfaceTexture texture = mTextureView.getSurfaceTexture();
		if (texture == null) {
			Log.e(TAG, "texture is null");
			return;
		}
		
		texture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
		Surface surface = new Surface(texture);
		
		try {
			mPreviewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
		} catch (CameraAccessException e){
			e.printStackTrace();
		}
		
		mPreviewBuilder.addTarget(surface);
		
		try {
			mCameraDevice.createCaptureSession(Arrays.asList(surface), mPreviewStateCallback, null);
		} catch (CameraAccessException e) {
			e.printStackTrace();
		}
		
	}

}
