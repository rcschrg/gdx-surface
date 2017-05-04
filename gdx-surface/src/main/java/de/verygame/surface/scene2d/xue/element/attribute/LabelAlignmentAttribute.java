package de.verygame.surface.scene2d.xue.element.attribute;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import de.verygame.xue.mapping.tag.attribute.AbstractAttribute;

/**
 * @author Rico Schrage
 */
public class LabelAlignmentAttribute extends AbstractAttribute<Label, String> {
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
    public void apply(Label element, String value) {
        align = value;
    }

    @Override
    public void end(Label element) {
        if (align == null) {
            return;
        }
        element.layout();
        switch (align) {
            case VALUE_ALIGN_CENTER:
                element.setX(Gdx.graphics.getWidth()/2f - element.getGlyphLayout().width/2f);
                break;

            case VALUE_ALIGN_LEFT:
                element.setX(0);
                break;

            case VALUE_ALIGN_RIGHT:
                element.setX(Gdx.graphics.getWidth() - element.getGlyphLayout().width);
                break;

            default:
                break;
        }
    }
}
