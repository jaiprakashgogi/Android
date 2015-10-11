package com.jaiprakashgogi.dualcam;

import java.util.Arrays;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CameraDevice.StateCallback;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;

public class MyCamera implements SurfaceTextureListener {
	
	private static final String TAG = "DualCamJai";
	private CameraDevice mCameraDevice = null;
	private CaptureRequest.Builder mPreviewBuilder = null;
	private CameraCaptureSession mPreviewSession = null;
	private TextureView mTextureView = null;
	private Size mPreviewSize = null;
	private int camId = -1;
	private CameraManager manager;

	public MyCamera(Context context, TextureView mTextureView1, CameraManager man, int id) {
		// TODO Auto-generated constructor stub
		camId = id;
		mTextureView = mTextureView1;
		manager = man;
	}

	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
			int height) {
		// TODO Auto-generated method stub
		openCamera(width, height, camId);
	}

	@Override
	public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onSurfaceTextureUpdated(SurfaceTexture surface) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onSurfaceTextureUpdated -- " + camId);
		
	}
	
	
	private StateCallback mStateCallback = new StateCallback() {
		@Override
		public void onOpened(CameraDevice camera) {
			// TODO Auto-generated method stub
			mCameraDevice = camera;
			createCameraPreviewSession();
			Log.i(TAG, "onOpened " + camId);
		}

		@Override
		public void onError(CameraDevice camera, int error) {
			// TODO Auto-generated method stub
			Log.i(TAG, "onError " + camId);
		}

		@Override
		public void onDisconnected(CameraDevice camera) {
			// TODO Auto-generated method stub
			Log.i(TAG, "onDisconnected "+ camId);
		}
	};

	protected void openCamera(int width, int height, int id) {
		// TODO Auto-generated method stub
		
		try {
			String cameraId = manager.getCameraIdList()[id];
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

			mPreviewBuilder.set(CaptureRequest.CONTROL_MODE,
					CameraMetadata.CONTROL_MODE_AUTO);

			HandlerThread backgroundThread = new HandlerThread("CameraPreview");
			backgroundThread.start();
			Handler backgroundHandler = new Handler(
					backgroundThread.getLooper());

			try {
				mPreviewSession.setRepeatingRequest(mPreviewBuilder.build(),
						null, backgroundHandler);
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

		texture.setDefaultBufferSize(mPreviewSize.getWidth(),
				mPreviewSize.getHeight());
		Surface surface = new Surface(texture);

		try {
			mPreviewBuilder = mCameraDevice
					.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
		} catch (CameraAccessException e) {
			e.printStackTrace();
		}

		mPreviewBuilder.addTarget(surface);

		try {
			mCameraDevice.createCaptureSession(Arrays.asList(surface),
					mPreviewStateCallback, null);
		} catch (CameraAccessException e) {
			e.printStackTrace();
		}

	}

	public void stop() {
		// TODO Auto-generated method stub
		if (mCameraDevice != null) {
			mCameraDevice.close();
			mCameraDevice = null;
		}
	}

}
