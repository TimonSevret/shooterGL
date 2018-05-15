package etu.univ.simon.shootgl.vue.Game;

import android.opengl.GLES20;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import etu.univ.simon.shootgl.Modele.Game.GestionAsteroid;
import etu.univ.simon.shootgl.controleur.Game.MainActivity;

/**
 * Created by simon on 06/04/18.
 */

public class GameRenderer implements android.opengl.GLSurfaceView.Renderer {

    private MainActivity context;
    private float[] mProjectionMatrix = new float[16];

    public GameRenderer(MainActivity context) {
        super();
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glClearColor(0,0,0.5f,1);
        GLES20.glClearDepthf(1);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);
        Matrix.perspectiveM(mProjectionMatrix, 0, 70.0f, 9.0f / 16.0f, 0.1f, 100.0f);
        GestionAsteroid.getInstance(context);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0,0,width,height);
        Matrix.perspectiveM(mProjectionMatrix, 0, 70.0f,  ((float)width)/((float)height), 0.1f, 100.0f);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GestionAsteroid.getInstance().draw(mProjectionMatrix);
        GestionAsteroid.getInstance().Actualiser();
    }
}
