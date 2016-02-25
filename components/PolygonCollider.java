package de.verygame.square.core.components;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Marco Deneke
 *
 * Created by Marco Deneke on 27.01.2016.
 *
 * Contain vertices of polygon collision box
 */
@PooledWeaver
public class PolygonCollider extends Component{

    private Vector2[] vertices;

    public Vector2[] getVertices() {
        return vertices;
    }

    public void setVertices(Vector2[] vertices) {
        this.vertices = vertices;
    }
}
