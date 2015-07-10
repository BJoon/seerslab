package com.seerslab.ndktest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    private static final String TAG = "cameratest";
   private CustomView mCustomView;
    private CustomSurfaceView mSurface;

    static {
        System.loadLibrary("opencv_java3");
        System.loadLibrary("NDKTest");
    }

    public native String getStringFromNative();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSurface = (CustomSurfaceView) findViewById(R.id.surface_view);
        mCustomView = (CustomView) findViewById(R.id.custom_view);

        mSurface.setCustomView(mCustomView);
        mCustomView.setCustomSurfaceView(mSurface);

      /* SurfaceHolder holder = surface.getHolder();
        holder.addCallback(mSurfaceListener);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

/*

    public native String getStringFromNative();
    private SurfaceHolder.Callback mSurfaceListener = new SurfaceHolder.Callback()
    {
        public void surfaceCreated(SurfaceHolder holder)
        {
            mCamera = Camera.open();
            mCamera.setDisplayOrientation(90);
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
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
        {
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPreviewSize(width, height);

          //  mCamera.setParameters(parameters);
            mCamera.startPreview();
            mCamera.setFaceDetectionListener(MyFaceListener);
            startFaceDetection();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder)
        {
            mCamera.stopFaceDetection();
            mCamera.release();
            mCamera = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomSurfaceView surface = (CustomSurfaceView) findViewById(R.id.surface_view);
        mCustomView = (CustomView) findViewById(R.id.custom_view);

        SurfaceHolder holder = surface.getHolder();
        holder.addCallback(mSurfaceListener);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startFaceDetection(){
        Camera.Parameters params = mCamera.getParameters();
        if(params.getMaxNumDetectedFaces()>0){
            mCamera.startFaceDetection();
        }
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

    public Camera getCamera(){
        return mCamera;
    }
}
*/


