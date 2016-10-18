package de.verygame.square.core.scene2d.glmenu.impl.element.attribute;

import com.badlogic.gdx.scenes.scene2d.Actor;

import de.verygame.xue.mapping.tag.attribute.AbstractAttribute;

/**
 * @author Rico Schrage
 */

public class AlphaAttribute extends AbstractAttribute<Actor, Float> {
    private static final String NAME = "alpha";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void apply(Actor element, Float value) {
        element.getColor().a = value;
    }
}
