package com.seerslab.ndktest;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by kimbyungjoon on 2015-07-06.
 */
public class CustomSurfaceView extends SurfaceView {
    private static final String TAG = "Preview";
    SurfaceHolder mHolder;
    CustomView mCustomView;
    Camera mCamera;

    public CustomSurfaceView(Context context) {
        super(context);
    }

    public CustomSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder();
        mHolder.addCallback(mSurfaceListener);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    private SurfaceHolder.Callback mSurfaceListener = new SurfaceHolder.Callback()
    {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mCamera = Camera.open();
            mCamera.setDisplayOrientation(90);
            mCustomView.setCamera(mCamera);
            try
            {
                mCamera.setPreviewDisplay(holder);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPreviewSize(width, height);

            //  mCamera.setParameters(parameters);
            mCamera.startPreview();
            mCamera.setFaceDetectionListener(MyFaceListener);
            startFaceDetection();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            mCamera.stopFaceDetection();
            mCamera.release();
            mCamera = null;
        }
    };
        @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = (int)((double)width * 4/3);
        Log.d(TAG,"width" + width);
        setMeasuredDimension(width, height);
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private Camera.FaceDetectionListener MyFaceListener = new Camera.FaceDetectionListener() {
        @Override
        public void onFaceDetection(Camera.Face[] faces, Camera camera) {
            if (faces.length > 0){
                Log.d("FaceDetection", "face detected: " + faces.length +
                        " Face 1 Location X: " + faces[0].rect.centerX() +
                        "Y: " + faces[0].rect.centerY());
                mCustomView.setFaceDetection(faces, camera);
            }
            else
                mCustomView.invalidate();
        }
    };

    public void startFaceDetection(){
        Camera.Parameters params = mCamera.getParameters();
        if(params.getMaxNumDetectedFaces()>0){
            mCamera.startFaceDetection();
        }
    }

    public void setCustomView(CustomView mCustomView){
        this.mCustomView = mCustomView;
    }
}
