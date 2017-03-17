package de.verygame.square.core.scene2d.xue.element.attribute;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;

import de.verygame.xue.mapping.tag.attribute.AbstractAttribute;

/**
 * @author Rico Schrage
 */

public class ActorAlignmentAttribute extends AbstractAttribute<Actor, String> {
    private static final String NAME = "align";
    private static final String VALUE_ALIGN_CENTER = "center";
    private static final String VALUE_ALIGN_LEFT = "left";
    private static final String VALUE_ALIGN_RIGHT = "right";

    private String align = null;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void apply(Actor element, String value) {
        align = value;
    }

    @Override
    public void end(Actor element) {
        if (align == null) {
            return;
        }
        switch (align) {
            case VALUE_ALIGN_CENTER:
                element.setX(Gdx.graphics.getWidth() / 2f - element.getWidth() / 2f);
                break;

            case VALUE_ALIGN_LEFT:
                element.setX(0);
                break;

            case VALUE_ALIGN_RIGHT:
                element.setX(Gdx.graphics.getWidth() - element.getWidth());
                break;

            default:
                break;
        }
    }
}
