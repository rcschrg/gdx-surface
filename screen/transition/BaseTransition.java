package de.verygame.square.core.screen.transition;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import de.verygame.square.core.screen.base.ScreenContext;
import de.verygame.square.core.screen.base.Transition;
import de.verygame.square.util.modifier.base.Modifier;

/**
 * @author Rico Schrage
 */
public abstract class BaseTransition implements Transition {

    /** Duration of the transition */
    protected float duration;
    /** Underlying interpolating modifier */
    protected Modifier animationModifier;

    /** Context of the screen which executes this transition */
    protected ScreenContext context;

    /**
     * Creates a base transition with the given duration.
     *
     * @param duration duration of the transition
     */
    public BaseTransition(float duration) {
        this.duration = duration;
    }

    /**
     * Called after every reset.
     */
    protected abstract void init();

    @Override
    public void preRender(PolygonSpriteBatch batch) {
        //default
    }

    @Override
    public void postRender(PolygonSpriteBatch batch) {
        //default
    }

    @Override
    public void update() {
        if (animationModifier != null) {
            animationModifier.update();
        }
    }

    @Override
    public float getDuration() {
        return duration;
    }

    @Override
    public boolean hasFinished() {
        return animationModifier.hasFinished();
    }

    @Override
    public void reset(ScreenContext context) {
        this.context = context;

        if (animationModifier != null) {
            animationModifier.reset();
        }

        init();
    }
}
