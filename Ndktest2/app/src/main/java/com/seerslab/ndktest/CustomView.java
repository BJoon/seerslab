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
import android.view.View;

/**
 * Created by kimbyungjoon on 2015-07-07.
 */
public class CustomView extends View {

    static final String TAG = "detecttest";
    private Paint mPaint;
    private Face[] mFaces;
    private Camera camera;

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
        Log.d(TAG, "initpaint");
    }

    @Override
    protected void onDraw(Canvas canvas) {
//      canvas.drawRect();
        super.onDraw(canvas);

       if (mFaces == null) {
          // Log.d(TAG, "mFaces =" + mFaces);
           canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            return;
        }

        for (Face face : mFaces) {
            canvas.drawRect(mappingToViewCoordinates(face.rect), mPaint);
        }
        mFaces=null;
    }

    private RectF mappingToViewCoordinates(Rect rect) {
        float rate;
        RectF newRect = new RectF();

        newRect.left =  1000-rect.bottom;
        newRect.top = rect.left + 1000;
        newRect.right = 1000-rect.top;
        newRect.bottom = rect.right + 1000;

        rate =  getWidth()/ 2000f;
        newRect.left = (int) (newRect.left * rate);
        newRect.right = (int) (newRect.right * rate);

        rate =  (getWidth()*4/3)/ 2000f;
        newRect.top = (int) (newRect.top * rate);
        newRect.bottom = (int) (newRect.bottom * rate);
/*
        newRect.left= 1000-newRect.right;
        newRect.right= 1000-newRect.left;*/
        //Log.d(TAG, "H"+getHeight());
        Log.d(TAG, "original=(" + rect.left + ", " + rect.top + ", " + rect.right + ", " + rect.bottom + ")");

//        Log.d(TAG, "viewSize=(" + getWidth() + ", " + getHeight() + ")");
        Log.d(TAG, "draw test = " + newRect.left + "," + newRect.top + "," + newRect.right + "," + newRect.bottom);
        return newRect;
    }

    public void setFaceDetection(Face[] faces, Camera camera) {
        //Log.d(TAG,"facedetect");
        if (faces.length > 0) {
            mFaces = faces;
            invalidate();
        }
    }
}
