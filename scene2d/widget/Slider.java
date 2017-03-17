package de.verygame.square.core.scene2d.widget;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * @author Rico Schrage
 */

public class Slider extends com.badlogic.gdx.scenes.scene2d.ui.Slider {

    public Slider(float min, float max, float stepSize, boolean vertical, Skin skin) {
        super(min, max, stepSize, vertical, skin);
    }

    public Slider(float min, float max, float stepSize, boolean vertical, Skin skin, String styleName) {
        super(min, max, stepSize, vertical, skin, styleName);
    }

    public Slider(float min, float max, float stepSize, boolean vertical, SliderStyle style) {
        super(min, max, stepSize, vertical, style);
    }

    @Override
    public void setHeight(float height) {
        super.setHeight(height);

        getStyle().knob.setMinHeight(height*2);
        getStyle().knob.setMinWidth(height*2);
        getStyle().background.setMinHeight(height);
    }
}
