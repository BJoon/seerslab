package com.seerslab.ndktest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.hardware.Camera.Face;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by kimbyungjoon on 2015-07-07.
 */
public class CustomView extends View {

    static final String TAG = "camerarelease";
    static final String TAG1 = "point";
    Camera mCamera;
    CustomSurfaceView mCustomSurfaceView;
    private Paint mPaint;
    private Face[] mFaces;
    private int x;
    private int y;
    private int save_x;
    private int save_y;
    private int tmp_x;
    private int tmp_y;
    private int flag=0;

    public CustomView(Context context) {
        super(context);
        initPaint();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.rgb(255, 255, 0));
        mPaint.setStrokeWidth(3f);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(20);
        Log.d(TAG, "initpaint");
    }

    @Override
    protected void onDraw(Canvas canvas) {
//      canvas.drawRect();
        super.onDraw(canvas);

        Log.d(TAG, "ondraw in");
            if (mFaces == null) {
                // Log.d(TAG, "mFaces =" + mFaces);
                canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                return;
            }

            for (Face face : mFaces) {
                canvas.drawRect(mappingToViewCoordinates(face.rect), mPaint);
            }
            canvas.drawText("X : " + x + "Y : " + y, 0, 20, mPaint);
        if(flag==0)
            mFaces = null;

    }

    private RectF mappingToViewCoordinates(Rect rect) {
        float rate;
        RectF newRect = new RectF();

            newRect.left = 1000 - rect.bottom;
            newRect.top = rect.left + 1000;
            newRect.right = 1000 - rect.top;
            newRect.bottom = rect.right + 1000;

            rate = getWidth() / 2000f;
            newRect.left = (int) (newRect.left * rate);
            newRect.right = (int) (newRect.right * rate);

            rate = (getWidth() * 4 / 3) / 2000f;
            newRect.top = (int) (newRect.top * rate);
            newRect.bottom = (int) (newRect.bottom * rate);
/*
        newRect.left= 1000-newRect.right;
        newRect.right= 1000-newRect.left;*/
            //Log.d(TAG, "H"+getHeight());
            Log.d(TAG, "original=(" + rect.left + ", " + rect.top + ", " + rect.right + ", " + rect.bottom + ")");

//        Log.d(TAG, "viewSize=(" + getWidth() + ", " + getHeight() + ")");
            Log.d(TAG, "draw test = " + newRect.left + "," + newRect.top + "," + newRect.right + "," + newRect.bottom);

        if(flag==1){
            newRect.left += tmp_x;
            newRect.right += tmp_x;
            newRect.top += tmp_y;
            newRect.bottom += tmp_y;
        }

        return newRect;
    }

    public void setFaceDetection(Face[] faces, Camera camera) {
        //Log.d(TAG,"facedetect");
        if (faces.length > 0) {
            mFaces = faces;
            invalidate();
        }
    }

   public boolean onTouchEvent(MotionEvent event){
        x=(int)event.getX();
        y=(int)event.getY();

       if(event.getAction()==MotionEvent.ACTION_DOWN){

           if(flag ==0){
               mCamera.stopPreview();
               Log.d(TAG, "stop preview");

/*
           save_x= mFaces[0].rect.centerX();
           save_y=mFaces[0].rect.centerY();
*/

               Log.d(TAG1, "save_x = " + save_x + "save_y = " + save_y );

               mCamera.stopFaceDetection();
               Log.d(TAG, "stop facedetection");

               mCamera.release();
               Log.d(TAG, "camera release");

           }
           flag=1;
       }

       if (event.getAction() == MotionEvent.ACTION_MOVE){
           tmp_x = x - save_x;
           Log.d(TAG1, "tmp_x = " + tmp_x);

           tmp_y = y - save_y;
           Log.d(TAG1, "tmp_y = " + tmp_y);
       }

       if(event.getAction() ==MotionEvent.ACTION_UP){
           save_x = x - save_x;
           save_y = y - save_y;
       }

        invalidate();
        return true;
    }

    public void setCamera(Camera mCamera){
        this.mCamera=mCamera;
    }

    public void setCustomSurfaceView(CustomSurfaceView mCustomSurfaceView){
        this.mCustomSurfaceView=mCustomSurfaceView;
    }
}
