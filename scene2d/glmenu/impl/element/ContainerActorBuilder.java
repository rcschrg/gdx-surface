package de.verygame.square.core.scene2d.glmenu.impl.element;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;

import de.verygame.square.util.glmenu.mapping.builder.AbstractContainerBuilder;

/**
 * @author Rico Schrage
 */
public class ContainerActorBuilder extends AbstractContainerBuilder<Actor> {

    public ContainerActorBuilder() {
        super(Container.class);

        this.element = new Container<>();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void applyChild(Actor element) {
        Container g = (Container) this.element;
        g.setActor(element);
    }
}
