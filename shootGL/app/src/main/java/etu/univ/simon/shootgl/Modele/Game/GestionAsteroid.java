package etu.univ.simon.shootgl.Modele.Game;

import java.util.LinkedList;
import java.util.List;

import etu.univ.simon.shootgl.controleur.Game.MainActivity;

public class GestionAsteroid {
    private static GestionAsteroid instance = null;
    private MainActivity context;
    private List<Asteroid> ennemy = new LinkedList<Asteroid>();
    private SpaceShip ship;
    private int difficulty = 2;
    private long previousFrame;

    private GestionAsteroid(MainActivity context) {
        this.context = context;
        ship = new SpaceShip(context);
        previousFrame = System.currentTimeMillis();
    }

    public static GestionAsteroid getInstance() {
        return instance;
    }

    public static GestionAsteroid getInstance(MainActivity context) {
        if(instance == null)
            instance = new GestionAsteroid(context);
        return instance;
    }

    public void Actualiser() {
        List<Asteroid> toRemove = new LinkedList<Asteroid>();
        for (Asteroid a : ennemy){
            a.approach();
            if(a.getPosZ() > 1){
                toRemove.add(a);
            }
            if(a.intersect(ship)){
                end();
            }
        }
        for(Asteroid a: toRemove){
            a.reinitialise();
        }
        if(ennemy.size() < difficulty){
            Asteroid asteroid = new Asteroid(context);
            ennemy.add(asteroid);
        }
        long time = System.currentTimeMillis();
        if(time - previousFrame > 3*1000) {
            difficulty++;
            previousFrame = time;
        }
    }

    private void end(){
        ship.clean();
        for(Asteroid a: ennemy){
            a.clean();
        }
        context.endGame();
    }

    public SpaceShip getShip() {
        return ship;
    }

    public void draw(float[] mProjectionMatrix) {
        ship.draw(mProjectionMatrix);
        for(Asteroid a: ennemy)
            a.draw(mProjectionMatrix);
    }
}
