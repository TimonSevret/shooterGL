package etu.univ.simon.shootgl.vue.Utils;

import android.opengl.GLES20;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Model3D {

    private int mVertexID;
    private int mTexID;
    private  int mNormalID;

    private int mProgram;

    private float mVertices[];
    private float mNormals[];
    private float mUV[];
    private int mIndices[];


    private FloatBuffer mVertexBuffer;
    private IntBuffer mIndexBuffer;
    private FloatBuffer mTexBuffer;
    private FloatBuffer mNormalBuffer;


    private static final int COORDS_PER_VERTEX = 3;
    private static final int COORDS_PER_NORMAL = 3;
    private static final int COORDS_PER_TEX = 2;

    private final int VERTEX_STRIDE = COORDS_PER_VERTEX * 4;
    private final int TEX_STRIDE = COORDS_PER_TEX * 4;
    private final int NORMAL_STRIDE = COORDS_PER_NORMAL * 4;
    private int mVertexShaderID;
    private int mFragShaderID;
    private int mIdViewMatrix;
    private int mIdProjMatrix;
    private int mIdModelMatrix;
    private int[] mTextureID;


    Model3D(float[] vertices, float[] normals, float[] uvs, int[] indices)
    {
        mVertices = vertices;
        mNormals = normals;
        mUV = uvs;
        mIndices = indices;

        ByteBuffer byteBuf = ByteBuffer.allocateDirect(mVertices.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        mVertexBuffer = byteBuf.asFloatBuffer();
        mVertexBuffer.put(mVertices);
        mVertexBuffer.position(0);

        byteBuf = ByteBuffer.allocateDirect(mNormals.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        mNormalBuffer = byteBuf.asFloatBuffer();
        mNormalBuffer.put(mNormals);
        mNormalBuffer.position(0);

        byteBuf = ByteBuffer.allocateDirect(mUV.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        mTexBuffer = byteBuf.asFloatBuffer();
        mTexBuffer.put(mUV);
        mTexBuffer.position(0);

        byteBuf = ByteBuffer.allocateDirect(mIndices.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        mIndexBuffer = byteBuf.asIntBuffer();
        mIndexBuffer.put(mIndices);
        mIndexBuffer.position(0);

    }

    public void init(String vertexShader, String fragmentShader, String vertexLoc,
              String normalLoc, String texLoc, int texture){


        mProgram = GLES20.glCreateProgram();

        mVertexShaderID = ShaderUtilities.loadShader(GLES20.GL_VERTEX_SHADER, vertexShader);
        mFragShaderID = ShaderUtilities.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader);
        GLES20.glAttachShader(mProgram, mVertexShaderID);
        GLES20.glAttachShader(mProgram, mFragShaderID);
        GLES20.glLinkProgram(mProgram);
        int[] linkStatus = {0};
        GLES20.glGetProgramiv(mProgram, GLES20.GL_LINK_STATUS, linkStatus, 0);

        mVertexID = GLES20.glGetAttribLocation(mProgram, vertexLoc);
        mNormalID = GLES20.glGetAttribLocation(mProgram, normalLoc);
        mTexID = GLES20.glGetAttribLocation(mProgram, texLoc);


        mTextureID = new int[1];
        GLES20.glGenTextures(1, mTextureID, 0);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        ShaderUtilities.loadTexture(texture, mTextureID, 0);

        mIdProjMatrix = GLES20.glGetUniformLocation(mProgram, "projection");
        mIdViewMatrix = GLES20.glGetUniformLocation(mProgram, "view");
        mIdModelMatrix = GLES20.glGetUniformLocation(mProgram, "model");

        Log.d("MODEL 3D", "init: mVertexID :" + mVertexID) ;
    }

    public void draw(float[] projection, float[] view, float[] model){
        GLES20.glUseProgram(mProgram);

        // Apply the projection and view transformation.
        GLES20.glUniformMatrix4fv(mIdViewMatrix, 1, false, view, 0);

        GLES20.glUniformMatrix4fv(mIdModelMatrix, 1, false, model, 0);
        GLES20.glUniformMatrix4fv(mIdProjMatrix, 1, false, projection, 0);


        GLES20.glEnableVertexAttribArray(mVertexID);

        GLES20.glVertexAttribPointer(
                mVertexID, 3, GLES20.GL_FLOAT, false, VERTEX_STRIDE, mVertexBuffer);

        GLES20.glEnableVertexAttribArray(mTexID);
        GLES20.glVertexAttribPointer(
                mTexID, 2, GLES20.GL_FLOAT, false, TEX_STRIDE, mTexBuffer);

        GLES20.glEnableVertexAttribArray(mNormalID);
        GLES20.glVertexAttribPointer(
                mNormalID, 2, GLES20.GL_FLOAT, false, NORMAL_STRIDE, mNormalBuffer);

        GLES20.glDrawElements(
                GLES20.GL_TRIANGLES, mIndices.length, GLES20.GL_UNSIGNED_INT, mIndexBuffer);

        GLES20.glDisableVertexAttribArray(mVertexID);
        GLES20.glDisableVertexAttribArray(mTexID);
        GLES20.glDisableVertexAttribArray(mNormalID);
    }

    public void clean(){
        GLES20.glDetachShader(mProgram, mVertexShaderID);
        GLES20.glDetachShader(mProgram, mFragShaderID);
        GLES20.glDeleteProgram(mProgram);
    }
    public int[] getmIndices() {
        return mIndices;
    }

    public float[] getmNormals() {
        return mNormals;
    }

    public float[] getmUV() {
        return mUV;
    }

    public float[] getmVertices() {
        return mVertices;
    }
}
