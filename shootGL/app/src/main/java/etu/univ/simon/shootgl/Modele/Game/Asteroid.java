package etu.univ.simon.shootgl.Modele.Game;

import android.content.Context;

public class Asteroid extends Entity {

    private long previousFrame;

    public Asteroid(Context context) {
        super(context,"cube.obj",1f);
        this.reinitialise();
        previousFrame = System.currentTimeMillis();
    }

    public void reinitialise() {
        float z = -15f;
        float x = (float)(Math.random()*2.0 - 1.0)*2;
        float y = (float)(Math.random()*2.0 - 1.0)*4;
        this.translateTo(x,y,z);
    }

    public void approach() {
        long time = System.currentTimeMillis();
        if(time - previousFrame > (1.0/60.0)*1000) {
            this.translate(0, 0, 0.2f);
            previousFrame = time;
        }
    }
}
