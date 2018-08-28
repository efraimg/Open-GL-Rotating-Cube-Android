package com.lightricks.efraim.opengl;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Cube {


    private final FloatBuffer mColorBuffer;
    private FloatBuffer vertexBuffer;
    private final int mProgram;

    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "attribute vec4 aColor;" +
                    "varying vec4 vColor;" +
                    "void main() {" +
                    // the matrix must be included as a modifier of gl_Position
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    " gl_Position = uMVPMatrix * vPosition;" +
                    " vColor = aColor;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "varying vec4 vColor;" +

                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";
    static final int COORDS_PER_VERTEX = 3;
    static float value = 0.25f;





    static float triangleCoords[] =  {
            -0.25f, 0.25f, -0.25f, // vertex 0
            -0.25f, -0.25f, -0.25f, // vertex 1
            0.25f, -0.25f, -0.25f, // vertex 2
            -0.25f, 0.25f, -0.25f, // vertex 0
            0.25f, -0.25f, -0.25f, // vertex 2
            0.25f, 0.25f, -0.25f, // vertex 3
// Top face (green)
            -0.25f, 0.25f, -0.25f, // vertex 4
            0.25f, 0.25f, -0.25f, // vertex 5
            0.25f, 0.25f, 0.25f, // vertex 6
            -0.25f, 0.25f, -0.25f, // vertex 4
            0.25f, 0.25f, 0.25f, // vertex 6
            -0.25f, 0.25f, 0.25f, // vertex 7
// Left face (blue)
            -0.25f, 0.25f, -0.25f, // vertex 8
            -0.25f, 0.25f, 0.25f, // vertex 9
            -0.25f, -0.25f, 0.25f, // vertex 10
            -0.25f, 0.25f, -0.25f, // vertex 8
            -0.25f, -0.25f, 0.25f, // vertex 10
            -0.25f, -0.25f, -0.25f, // vertex 11
// Right face (yellow)
            0.25f, 0.25f, -0.25f, // vertex 12
            0.25f, -0.25f, -0.25f, // vertex 13
            0.25f, -0.25f, 0.25f, // vertex 14
            0.25f, 0.25f, -0.25f, // vertex 12
            0.25f, -0.25f, 0.25f, // vertex 14
            0.25f, 0.25f, 0.25f, // vertex 15
// Bottom face (cyan)
            -0.25f, -0.25f, -0.25f, // vertex 16
            0.25f, -0.25f, -0.25f, // vertex 17
            0.25f, -0.25f, 0.25f, // vertex 18
            -0.25f, -0.25f, -0.25f, // vertex 16
            0.25f, -0.25f, 0.25f, // vertex 18
            -0.25f, -0.25f, 0.25f, // vertex 19
// Back face (magenta)
            -0.25f, 0.25f, 0.25f, // vertex 20
            -0.25f, -0.25f, 0.25f, // vertex 21
            0.25f, -0.25f, 0.25f, // vertex 22
            -0.25f, 0.25f, 0.25f, // vertex 20
            0.25f, -0.25f, 0.25f, // vertex 22
            0.25f, 0.25f, 0.25f,}; // vertex 23






    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    float color[]  = {
            // (red)
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,


            // (green)
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,


            // (blue)
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,


            // (yellow)
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,


            //  (cyan)
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,


            // (magenta)
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
    };


    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;


    public Cube() {

        ByteBuffer triangleCoordsByteBuffer = ByteBuffer.allocateDirect(triangleCoords.length * 4);
        triangleCoordsByteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = triangleCoordsByteBuffer.asFloatBuffer();
        vertexBuffer.put(triangleCoords);
        vertexBuffer.position(0);




        ByteBuffer byteBuf = ByteBuffer.allocateDirect(color.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        mColorBuffer = byteBuf.asFloatBuffer();
        mColorBuffer.put(color);
        mColorBuffer.position(0);


        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        // create empty OpenGL ES Program
        mProgram = GLES20.glCreateProgram();

        // add the vertex shader to program
        GLES20.glAttachShader(mProgram, vertexShader);

        // add the fragment shader to program
        GLES20.glAttachShader(mProgram, fragmentShader);

        // creates OpenGL ES program executables
        GLES20.glLinkProgram(mProgram);
    }

    public void draw(float[] mMVPMatrix) {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);




        mColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor");

        GLES20.glEnableVertexAttribArray(mColorHandle);


        GLES20.glVertexAttribPointer(mColorHandle, 4, GLES20.GL_FLOAT, false,
                4*4, mColorBuffer);



        MyGLRenderer.checkGlError("color");


        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");


        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);



        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);

        GLES20.glDisableVertexAttribArray(mColorHandle);
    }
}
