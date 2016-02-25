package de.verygame.square.core.components;

import com.artemis.Component;

/**
 * @author Marco Deneke
 *
 * Created by Marco Deneke on 04.01.2016.
 *
 * Contains the texture to be rendered and the animation timer.
 */
public class AnimatedTexture extends Component{

    private float speed;

    //waiting for resource manager

    public float getSpeed(){
        return speed;
    }

    public void setSpeed(float speed){
        this.speed = speed;
    }
}
