package com.lightricks.efraim.opengl;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OpenGLES20Activity extends Activity {

    private MyGLSurfaceView mGLView;
    private float[] rotationMatrix = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGLView = new MyGLSurfaceView(this);

        if(savedInstanceState != null){
            rotationMatrix = savedInstanceState.getFloatArray("rotationMatrix");
            if(rotationMatrix != null){
                mGLView.getRenderer().setRotationMatrix(rotationMatrix);
            }
        }
        setContentView(mGLView);
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putFloatArray("rotationMatrix", mGLView.getRenderer().getRotationMatrix());
        super.onSaveInstanceState(savedInstanceState);
    }

}
