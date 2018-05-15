package etu.univ.simon.shootgl.Modele.Game;

import android.content.Context;
import android.opengl.Matrix;

import etu.univ.simon.shootgl.R;
import etu.univ.simon.shootgl.vue.Utils.Model3D;
import etu.univ.simon.shootgl.vue.Utils.ModelLoader;

public class Entity {

    private float posX;
    private float posY;
    private float posZ;
    private float szX;
    private float szY;
    private float szZ;


    private float[] mViewMatrix = new float[16];
    private float[] mModelMatrix = new float[16];
    private Model3D modele;

    public Entity(Context context, String filename,float coef) {
        super();

        Matrix.setLookAtM(mViewMatrix, 0, 0, 1, 5, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.setIdentityM(mModelMatrix,0);

        Matrix.scaleM(mModelMatrix,0,coef,coef,coef);
        modele = ModelLoader.readOBJFile(context,filename);
        modele.init("vertexshader.vert", "fragmentshader.frag",
                "vPosition", "vNormal", "vTexCoord", R.drawable.no_texture);

        calcSz(coef);
    }

    private void calcSz(float coef) {
        float xMin=0;
        float xMax=0;
        float yMin=0;
        float yMax=0;
        float zMin=0;
        float zMax=0;
        float[] vertices = modele.getmVertices();
        for(int i=0; i < vertices.length;i += 3){
            float x = vertices[i];
            float y = vertices[i+1];
            float z = vertices[i+2];
            if(x<xMin)
                xMin = x;
            else if(x>xMax)
                xMax = x;

            if(y<yMin)
                yMin = y;
            else if(y>yMax)
                yMax = y;

            if(z<zMin)
                zMin = z;
            else if(z>zMax)
                zMax = z;
        }

        szX = (xMax - xMin)*coef;
        szY = (yMax - yMin)*coef;
        szZ = (zMax - zMin)*coef;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public float getPosZ() {
        return posZ;
    }

    public float getSzX() {
        return szX/2f;
    }

    public float getSzY() {
        return szY/2f;
    }

    public float getSzZ() {
        return szZ/2f;
    }

    public void translate(float x, float y,float z){
        posX += x;
        posY += y;
        posZ += z;
        Matrix.translateM(mViewMatrix,0,x,y,z);
    }

    public void draw(float[] mProjectionMatrix) {
        modele.draw(mProjectionMatrix,mViewMatrix,mModelMatrix);
    }

    public boolean intersect(Entity other) {
       /* Log.d("debug", "posC: "+this.getPosX()+","+this.getPosY()+","+this.getPosZ());
        Log.d("debug", "posS: "+other.getPosX()+","+other.getPosY()+","+other.getPosZ());*/

        //check the X axis
        if(Math.abs(other.getPosX() - this.getPosX()) < other.getSzX() + this.getSzX())
        {
            //check the Y axis
            if(Math.abs(other.getPosY() - this.getPosY()) < other.getSzY() + this.getSzY())
            {
                //check the Z axis
                if(Math.abs(other.getPosZ() - this.getPosZ()) < other.getSzZ() + this.getSzZ())
                {
                    return true;
                }
            }
        }

        return false;
    }

    public void translateTo(float x, float y, float z) {
        Matrix.translateM(mViewMatrix,0,(x-posX),(y-posY),z-posZ);
        posX = x;
        posY = y;
        posZ = z;
    }

    public void clean() {
        modele.clean();
    }
}
