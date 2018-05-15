package etu.univ.simon.shootgl.controleur.Game;

import android.content.Intent;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import etu.univ.simon.shootgl.controleur.End.EndActivity;
import etu.univ.simon.shootgl.vue.Utils.ShaderUtilities;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ShaderUtilities.init(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        WindowManager w = getWindowManager();
        Point size = new Point();
        w.getDefaultDisplay().getSize(size);
        surfaceView = new GameSurfaceView(this,size.x,size.y);
        this.setContentView(surfaceView);
    }

    public void endGame(){
        if(!isFinishing()) {
            startActivity(new Intent(this, EndActivity.class));
            super.finish();
        }
    }



}
