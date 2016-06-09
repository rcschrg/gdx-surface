package de.verygame.square.core.scene2d.glmenu.impl.element;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;

import de.verygame.square.util.ColorUtils;
import de.verygame.square.util.glmenu.exception.AttributeUnknownException;
import de.verygame.square.util.glmenu.mapping.builder.AbstractElementBuilder;

/**
 * @author Rico Schrage
 */
public class GenericElementBuilder<T extends Actor> extends AbstractElementBuilder<Actor> {

    private static final String ATTRIBUTE_ACTOR_ALIGN = "align";

    private static final String VALUE_ALIGN_CENTER = "center";
    private static final String VALUE_ALIGN_LEFT = "left";
    private static final String VALUE_ALIGN_RIGHT = "right";

    private static final String ATTRIBUTE_COLOR = "color";
    private static final String ATTRIBUTE_ALPHA = "alpha";

    private String align = null;

    public GenericElementBuilder(T element) {
        super(element.getClass());

        this.element = element;
    }

    @Override
    protected void applyStringSpecial(String attribute, String value) throws AttributeUnknownException {
        if (ATTRIBUTE_ACTOR_ALIGN.equals(attribute)) {
            align = value;
        }
        else if (ATTRIBUTE_COLOR.equals(attribute)) {
            Color color = ColorUtils.fromHex(value);
            element.setColor(color);
        }
    }

    private void applyAlignment(String alignment) {
        switch (alignment) {
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

    @Override
    public void postBuild() {
        if (align != null) {
            applyAlignment(align);
        }
    }

    @Override
    protected void applyFloatSpecial(String attribute, float value) throws AttributeUnknownException {
        if (ATTRIBUTE_ALPHA.equals(attribute)) {
            element.getColor().a = value;
        }
    }
}
