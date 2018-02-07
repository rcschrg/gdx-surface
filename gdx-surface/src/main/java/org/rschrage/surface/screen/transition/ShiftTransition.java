package org.rschrage.surface.screen.transition;

import org.rschrage.surface.util.modifier.SingleValueModifier;
import org.rschrage.surface.util.modifier.base.SimpleModifierCallback;

/**
 * @author Rico Schrage
 */
public abstract class ShiftTransition extends BaseTransition {

    /**
     * Available shift directions.
     */
    public enum Shift {
        LEFT, RIGHT
    }

    /** Shift direction of the screen */
    protected final Shift shift;

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

        public Out() {
            this(1f);
        }

        public Out(float duration) {
            this(Shift.LEFT, duration);
        }

        public Out(Shift shift, float duration) {
            super(shift, duration);
        }

        @Override
        protected void init() {
            animationModifier = (shift == Shift.LEFT) ? new SingleValueModifier(duration, 0, -context.getViewport().getWorldWidth(), new ShiftCallback()) :
                   new SingleValueModifier(duration, 0, context.getViewport().getWorldWidth(), new ShiftCallback());
        }

    }

    /**
     * @author Rico Schrage
     */
    public static class In extends ShiftTransition {

        public In() {
            this(1f);
        }

        public In(float duration) {
            this(Shift.LEFT, duration);
        }

        public In(Shift shift, float duration) {
            super(shift, duration);
        }

        @Override
        protected void init() {
            animationModifier = (shift == Shift.LEFT) ? new SingleValueModifier(duration, -context.getViewport().getWorldWidth(), 0, new ShiftCallback()) :
                        new SingleValueModifier(duration, context.getViewport().getWorldWidth(), 0, new ShiftCallback());
        }

    }
}
