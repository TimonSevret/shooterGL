package etu.univ.simon.shootgl.controleur.End;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import etu.univ.simon.shootgl.vue.End.EndRenderer;

class EndSurfaceView extends GLSurfaceView {

    Renderer renderer;
    Activity activity;

    public EndSurfaceView(Activity context) {
        super(context);

        activity = context;

        this.setEGLContextClientVersion(2);

        renderer = new EndRenderer(context);

        this.setRenderer(renderer);
        this.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e)  {
        activity.finish();
        return super.onTouchEvent(e);
    }
}
