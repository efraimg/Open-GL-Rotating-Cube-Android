package com.lightricks.efraim.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.concurrent.atomic.AtomicBoolean;

public class MyGLSurfaceView extends GLSurfaceView {
    private final MyGLRenderer mRenderer;

    private GestureDetectorCompat mDetector;


    public MyGLSurfaceView(Context context) {
        super(context);


        setEGLContextClientVersion(2);

        mRenderer = new MyGLRenderer();


        setRenderer(mRenderer);

        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);


        mDetector = new GestureDetectorCompat(context, new MyGestureListener());

    }
    public MyGLRenderer getRenderer() {
        return mRenderer;
    }





    @Override
    public boolean onTouchEvent(MotionEvent e) {

        return this.mDetector.onTouchEvent(e);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        private final float TOUCH_SCALE_FACTOR = 180.0f / 320;

        public boolean onDown(MotionEvent e) {

            return true;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {

            float angle = (float) Math.sqrt(distanceX*distanceX + distanceY*distanceY);
            mRenderer.setAngle(angle * TOUCH_SCALE_FACTOR);

            mRenderer.setRotationVec(new float[]{-distanceY,-distanceX});
            requestRender();
            return true;
        }

        public boolean onSingleTapConfirmed(MotionEvent e) {
            mRenderer.setAngle(0);
            return true;
        }

    }


}
