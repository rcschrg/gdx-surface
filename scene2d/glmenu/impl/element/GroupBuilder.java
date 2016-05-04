package de.verygame.square.core.scene2d.glmenu.impl.element;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import de.verygame.square.util.glmenu.handler.builder.base.AbstractContainerBuilder;

/**
 * @author Rico Schrage
 */
public class GroupBuilder extends AbstractContainerBuilder<Actor> {

    public GroupBuilder() {
        super(Group.class);

        this.element = new Group();
    }

    @Override
    public void applyChild(Actor element) {
        Group group = (Group) this.element;
        group.addActor(element);
    }
}
