package de.verygame.square.core.scene2d.glmenu.impl.element;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;

import de.verygame.square.util.ColorUtils;
import de.verygame.square.util.glmenu.exception.AttributeUnknownException;
import de.verygame.square.util.glmenu.handler.builder.AbstractElementBuilder;

/**
 * @author Rico Schrage
 */
public class GenericElementBuilder<T extends Actor> extends AbstractElementBuilder<Actor> {

    private static final String ATTRIBUTE_COLOR = "color";

    public GenericElementBuilder(T element) {
        super(element.getClass());

        this.element = element;
    }

    @Override
    protected void applyStringSpecial(String key, String value) throws AttributeUnknownException {
        if (ATTRIBUTE_COLOR.equals(key)) {
            Color color = ColorUtils.fromHex(value);
            element.setColor(color);
        }
    }

}
