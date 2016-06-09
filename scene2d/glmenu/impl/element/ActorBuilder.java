package de.verygame.square.core.scene2d.glmenu.impl.element;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import de.verygame.square.util.glmenu.exception.AttributeUnknownException;
import de.verygame.square.util.glmenu.mapping.builder.AbstractElementBuilder;

/**
 * @author Rico Schrage
 */
public class ActorBuilder extends AbstractElementBuilder<Actor> {

    public ActorBuilder() {
        super(Actor.class);

        element = new Actor();
    }
}
