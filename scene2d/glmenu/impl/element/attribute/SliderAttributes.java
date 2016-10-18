package de.verygame.square.core.scene2d.glmenu.impl.element.attribute;

import com.badlogic.gdx.scenes.scene2d.ui.Slider;

import de.verygame.xue.mapping.tag.attribute.AbstractAttribute;

/**
 * @author Rico Schrage
 */

public class SliderAttributes {

    public static class MinValue extends AbstractAttribute<Slider, Float> {
        private static final String ATTRIBUTE_MIN = "minValue";


        @Override
        public String getName() {
            return ATTRIBUTE_MIN;
        }

        @Override
        public void apply(Slider element, Float value) {
            element.setRange(value, element.getMaxValue());
        }
    }

    public static class MaxValue extends AbstractAttribute<Slider, Float> {
        private static final String ATTRIBUTE_MAX = "maxValue";

        @Override
        public String getName() {
            return ATTRIBUTE_MAX;
        }

        @Override
        public void apply(Slider element, Float value) {
            element.setRange(element.getMinValue(), value);
        }
    }

    public static class Value extends AbstractAttribute<Slider, Float> {
        private static final String ATTRIBUTE_VALUE = "value";

        @Override
        public String getName() {
            return ATTRIBUTE_VALUE;
        }

        @Override
        public void apply(Slider element, Float value) {
            element.setValue(value);
        }
    }

    public static class StepSize extends AbstractAttribute<Slider, Float> {
        private static final String ATTRIBUTE_STEP = "stepSize";

        @Override
        public String getName() {
            return ATTRIBUTE_STEP;
        }

        @Override
        public void apply(Slider element, Float value) {
            element.setStepSize(value);
        }
    }
}
