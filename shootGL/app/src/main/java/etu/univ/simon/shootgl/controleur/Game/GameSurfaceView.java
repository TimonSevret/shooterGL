package etu.univ.simon.shootgl.controleur.Game;

import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import etu.univ.simon.shootgl.Modele.Game.GestionAsteroid;
import etu.univ.simon.shootgl.vue.Game.GameRenderer;

/**
 * Created by simon on 06/04/18.
 */

public class GameSurfaceView extends GLSurfaceView {

    private GameRenderer renderer;
    private float widthCoef;
    private float heightCoef;


    private float oldX;
    private float oldY;

    public GameSurfaceView(MainActivity context, int width, int height) {
        super(context);

        this.setEGLContextClientVersion(2);

        renderer = new GameRenderer(context);

        this.setRenderer(renderer);
        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        this.widthCoef = width/2;
        this.heightCoef = height/2;

        oldX = 0;
        oldY = 0;

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX() - widthCoef;
        float y = e.getY() - heightCoef;

        float deltaX = (x - oldX)/(widthCoef)*2f;
        float deltaY = (oldY - y)/(heightCoef)*3.5f;

        oldX = x;
        oldY = y;

        GestionAsteroid.getInstance().getShip().translate (deltaX,deltaY,0);

        requestRender();

        return true;
    }

}
