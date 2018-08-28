package com.lightricks.efraim.opengl;


import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer {
    private Cube mCube;
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private float[] mRotationMatrix = new float[16];
    public volatile float angle = 1f;

    private volatile float[] rotationVec = {0.0f,1.0f};
    private static final String TAG = "MyGLRenderer";



    public MyGLRenderer(){
        super();
        Matrix.setRotateM(mRotationMatrix, 0, angle,rotationVec[0],rotationVec[1], 0.0f);
    }


    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, .011f);
        mCube = new Cube();
    }

    public void onDrawFrame(GL10 unused) {


        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);


        GLES20.glEnable(GLES20.GL_DEPTH_TEST);





        float[] newRotation = new float[16];
        Matrix.setRotateM(newRotation, 0, angle,rotationVec[0],rotationVec[1], 0.0f);
        Matrix.multiplyMM(mRotationMatrix, 0,newRotation , 0, mRotationMatrix, 0);
        

        float[] scratch = new float[16];
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);


        mCube.draw(scratch);
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 1, 10);
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 1.5f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
    }
    public static int loadShader(int type, String shaderCode){

        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }


    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            String err = GLES20.glGetString(error);
            Log.e(TAG, glOperation + ": glError " + err);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }



    public void setAngle(float angle) {
        this.angle = angle % 360;
    }

    public void setRotationMatrix(float[] mat){
        for (int i = 0; i < mat.length; i++) {
            mRotationMatrix[i] = mat[i];
        }
    }
    public float[] getRotationMatrix(){
        return mRotationMatrix;
    }
    public void setRotationVec(float[] rotationVec) {
        this.rotationVec = rotationVec;
    }
}
