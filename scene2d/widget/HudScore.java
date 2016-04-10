package de.verygame.square.core.scene2d.widget;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * @author Rico Schrage
 */
public class HudScore extends Panel {

    private final Label label;
    private float counter = 0f;

    public HudScore(Skin hudSkin) {
        this(new Label("0", hudSkin));
    }

    public HudScore(Label label) {
        super();

        this.label = label;
        this.addActor(label);
    }

    public Label getLabel() {
        return label;
    }

    public float getScore() {
        return counter;
    }

    public void increment(float inc) {
        this.counter += inc;
        this.label.setText(String.valueOf((int) counter));
    }

}
