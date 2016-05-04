package de.verygame.square.core.scene2d.glmenu.impl.element;

import com.badlogic.gdx.scenes.scene2d.Actor;

import de.verygame.square.util.glmenu.handler.builder.base.AbstractElementBuilder;

/**
 * @author Rico Schrage
 */
public class ActorBuilder extends AbstractElementBuilder<Actor> {

    public ActorBuilder() {
        super(Actor.class);

        element = new Actor();
    }

}
