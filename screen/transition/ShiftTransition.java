package de.verygame.square.core.screen.transition;

import de.verygame.square.util.modifier.SingleValueModifier;
import de.verygame.square.util.modifier.base.SimpleModifierCallback;

/**
 * @author Rico Schrage
 */
public abstract class ShiftTransition extends BaseTransition {

    /**
     * Available shift directions.
     */
    public enum Shift {
        LEFT, RIGHT;
    }

    /** Shift direction of the screen */
    protected final Shift shift;

    /**
     * Creates a left-shift transition with a duration of one second.
     */
    public ShiftTransition() {
        this(1f);
    }

    /**
     * Creates a left-shift transition with the given duration.
     *
     * @param duration duration of the transition
     */
    public ShiftTransition(float duration) {
        this(Shift.LEFT, duration);
    }

    /**
     * Creates a shift transition with the given duration.
     *
     * @param shift shift direction
     * @param duration duration of the transition
     */
    public ShiftTransition(Shift shift, float duration) {
        super(duration);

        this.shift = shift;
    }

    /**
     * Callback to perform a screen shift
     *
     * @author Rico Schrage
     */
    private class ShiftCallback extends SimpleModifierCallback {
        @Override
        protected void action(float value) {
            context.getBatch().getProjectionMatrix().translate(value, 0, 0);
        }
    }

    /**
     * @author Rico Schrage
     */
    public static class Out extends ShiftTransition {

        @Override
        protected void init() {
            if (animationModifier == null) {
                animationModifier = (shift == Shift.LEFT) ? new SingleValueModifier(duration, 0, -context.getViewport().getWorldWidth(), new ShiftCallback()) :
                        new SingleValueModifier(duration, 0, context.getViewport().getWorldWidth(), new ShiftCallback());
            }
        }

    }

    /**
     * @author Rico Schrage
     */
    public static class In extends ShiftTransition {

        @Override
        protected void init() {
            if (animationModifier == null) {
                animationModifier = (shift == Shift.LEFT) ? new SingleValueModifier(duration, -context.getViewport().getWorldWidth(), 0, new ShiftCallback()) :
                        new SingleValueModifier(duration, context.getViewport().getWorldWidth(), 0, new ShiftCallback());
            }
        }

    }
}
