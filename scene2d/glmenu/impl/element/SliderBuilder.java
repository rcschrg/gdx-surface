package de.verygame.square.core.scene2d.glmenu.impl.element;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;

import de.verygame.square.util.glmenu.exception.AttributeUnknownException;

/**
 * @author Rico Schrage
 */
public class SliderBuilder extends GenericElementBuilder<Actor> {

    private static final String ATTRIBUTE_MIN = "minValue";
    private static final String ATTRIBUTE_MAX = "maxValue";
    private static final String ATTRIBUTE_VALUE = "value";
    private static final String ATTRIBUTE_STEP = "stepSize";

    public SliderBuilder(Skin skin) {
        super(new Slider(0, 100, 1, false, skin));
    }

    @Override
    protected void applyFloatSpecial(String attribute, float value) throws AttributeUnknownException {
        Slider slider = (Slider) element;
        switch (attribute) {
            case ATTRIBUTE_MAX:
                slider.setRange(slider.getMinValue(), value);
                break;
            case ATTRIBUTE_MIN:
                slider.setRange(value, slider.getMaxValue());
                break;
            case ATTRIBUTE_VALUE:
                slider.setValue(value);
                break;
            case ATTRIBUTE_STEP:
                slider.setStepSize(value);
                break;
            default:
                break;
        }

        super.applyFloatSpecial(attribute, value);
    }
}
