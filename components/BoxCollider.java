package de.verygame.square.core.components;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;

/**
 * @author Marco Deneke
 *
 * Created by Marco Deneke on 04.01.2016.
 *
 * Contains size of rectangular collision box.
 */
@PooledWeaver
public class BoxCollider extends Component{

    private float width = 0;

    private float height = 0;

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

}
