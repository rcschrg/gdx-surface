package de.verygame.square.core.scene2d.glmenu.impl.element.attribute;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import de.verygame.xue.mapping.tag.attribute.AbstractAttribute;

/**
 * @author Rico Schrage
 */

public class LabelTextAttribute extends AbstractAttribute<Label, String> {
    private static final String ATTRIBUTE_TEXT = "text";

    @Override
    public String getName() {
        return ATTRIBUTE_TEXT;
    }

    @Override
    public void apply(Label element, String value) {
        element.setText(value);
    }
}
