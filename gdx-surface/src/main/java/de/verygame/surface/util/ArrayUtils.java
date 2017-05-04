package de.verygame.surface.util;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Rico Schrage
 */
public class ArrayUtils {

    private ArrayUtils() {
        //utility class
    }

    public static float[] createAndFill(int size, float value) {
        float[] res = new float[size];
        for (int i = 0; i < size; ++i) {
            res[i] = value;
        }
        return res;
    }

    public static float[] buildVertexArray(Vector2[] vertices){

        float[] res = new float[vertices.length*2];

        for(int i= 0, j=0; j< vertices.length; i+=2, j++){
            res[i] = vertices[j].x;
            res[i+1] = vertices[j].y;
        }

        return res;
    }

}