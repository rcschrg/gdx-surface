package de.verygame.square.core.components;

import com.artemis.Component;

/**
 * @author Marco Deneke
 *
 * Created by Marco Deneke on 04.01.2016.
 *
 * Contains velocity
 */
public class Movement extends Component {

    private float xVelocity;
    private float yVelocity;

    public float getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(float xVelocity) {
        this.xVelocity = xVelocity;
    }

    public float getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(float yVelocity) {
        this.yVelocity = yVelocity;
    }
}
