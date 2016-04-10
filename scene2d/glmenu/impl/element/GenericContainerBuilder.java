package de.verygame.square.core.scene2d.glmenu.impl.element;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import de.verygame.square.util.glmenu.element.ContainerBuilder;

/**
 * @author Rico Schrage
 */
public class GenericContainerBuilder<T extends Group> extends GenericElementBuilder<T> implements ContainerBuilder<Actor> {

    public GenericContainerBuilder(T element) {
        super(element);
    }

    @Override
    public void applyChild(Actor element) {
        Group g = (Group) this.element;
        g.addActor(element);
    }
}
