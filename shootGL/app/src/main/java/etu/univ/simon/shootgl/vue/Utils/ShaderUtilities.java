package etu.univ.simon.shootgl.vue.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import java.io.InputStream;

public class ShaderUtilities {

    private static Context context;

    public static void init(Context c){
        context = c;
    }

    public static int loadShader(int type, String filename){
        try {
            InputStream is = context.getAssets().open(filename);

            java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
            String sourceCode = s.hasNext() ? s.next() : "";

            s.close();
            is.close();

            int shader = GLES20.glCreateShader(type);
            GLES20.glShaderSource(shader, sourceCode);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {
                String info = GLES20.glGetShaderInfoLog(shader);
                GLES20.glDeleteShader(shader);
                shader = 0;
                throw new RuntimeException("Could not compile shader " +
                        type + ":" + info);
            }
            Log.d("Debug : ", "Shader created");
            return shader;
        } catch (Exception e){
            Log.e(e.toString(), e.getMessage());
            return -1;
        }
    }

    public static int loadTexture(final int resourceId, int[] textureHandle , int index)
    {

        if (textureHandle[index] != 0)
        {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;   // No pre-scaling

            // Read in the resource
            Log.d("debug", "loadTexture: " + resourceId);
            final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);

            // Bind to the texture in OpenGL
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[index]);

            // Set filtering
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

            // Load the bitmap into the bound texture.
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

            // Recycle the bitmap, since its data has been loaded into OpenGL.
            bitmap.recycle();
        }

        if (textureHandle[index] == 0)
        {
            throw new RuntimeException("Error loading texture.");
        }

        return textureHandle[index];
    }
}
