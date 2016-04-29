package de.verygame.square.core.screen.transition;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import de.verygame.square.core.ScreenContext;
import de.verygame.square.core.screen.Transition;
import de.verygame.square.util.modifier.base.Modifier;

/**
 * @author Rico Schrage
 */
public abstract class BaseTransition implements Transition {

    /** True if the transition finished */
    protected boolean finished;
    /** Duration of the transition */
    protected float duration;
    /** Underlying interpolating modifier */
    protected Modifier animationModifier;

    /** Context of the screen which executes this transition */
    protected ScreenContext context;

    /**
     * Called after every reset.
     */
    protected abstract void init();

    /**
     * Creates a base transition with the given duration.
     *
     * @param duration duration of the transition
     */
    public BaseTransition(float duration) {
        this.duration = duration;
    }

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
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void reset(ScreenContext context) {
        this.context = context;

        finished = false;

        if (animationModifier != null) {
            animationModifier.reset();
        }

        init();
    }
}
