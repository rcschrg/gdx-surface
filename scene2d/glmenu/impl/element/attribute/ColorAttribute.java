package de.verygame.square.core.scene2d.glmenu.impl.element.attribute;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;

import de.verygame.square.util.ColorUtils;
import de.verygame.xue.mapping.tag.attribute.AbstractAttribute;

/**
 * @author Rico Schrage
 */

public class ColorAttribute extends AbstractAttribute<Actor, String> {
    private static final String NAME = "color";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void apply(Actor element, String value) {
        Color color = ColorUtils.fromHex(value);
        element.setColor(color);
    }
}
