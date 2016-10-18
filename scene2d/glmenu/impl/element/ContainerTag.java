package de.verygame.square.core.scene2d.glmenu.impl.element;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * @author Rico Schrage
 */

public class ContainerTag<T extends Group> extends ElementTag<T> {

    public ContainerTag(T element) {
        super(element);
    }

    @Override
    public void applyChild(Object child) {
        if (child instanceof Actor) {
            element.addActor((Actor) child);
        }
    }
}
