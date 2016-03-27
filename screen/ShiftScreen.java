package de.verygame.square.core.screen;

import com.badlogic.gdx.utils.viewport.Viewport;

import de.verygame.square.core.BaseSubScreen;
import de.verygame.square.core.Content;
import de.verygame.square.core.ScreenId;
import de.verygame.square.util.modifier.SingleValueModifier;
import de.verygame.square.util.modifier.base.ModifierCallback;

/**
 * @author Rico Schrage
 *         <p>
 *         Implements a shift on switch animation.
 */
public class ShiftScreen extends BaseSubScreen {

    public enum Shift {
        LEFT, RIGHT;
    }

    private final Shift shift;
    private final float duration;
    private SingleValueModifier animationModifier;

    /**
     * Constructs a basic screen.
     *
     * @param viewport viewport, which should be used to manage glViewport
     * @param content  content of the screen
     */
    public ShiftScreen(Viewport viewport, Content content) {
        this(viewport, content, Shift.LEFT, 1f);
    }

    public ShiftScreen(Viewport viewport, final Content content, Shift shift, float duration) {
        super(viewport, content);

        this.shift = shift;
        this.duration = duration;
    }

    private SingleValueModifier createRightModifier(boolean active) {
        return (active) ? new SingleValueModifier(duration, context.getViewport().getWorldWidth(), 0, new ShiftCallback()) :
                new SingleValueModifier(duration, 0, context.getViewport().getWorldWidth(), new ShiftCallback());
    }

    private SingleValueModifier createLeftModifier(boolean active) {
        return (active) ? new SingleValueModifier(duration, -context.getViewport().getWorldWidth(), 0, new ShiftCallback()) :
                new SingleValueModifier(duration, 0, -context.getViewport().getWorldWidth(), new ShiftCallback());
    }

    private SingleValueModifier createModifier(boolean active) {
        return (shift == Shift.LEFT) ? createLeftModifier(active) : createRightModifier(active);
    }

    @Override
    protected float onSetInactive(ScreenId predecessor) {
        animationModifier = createModifier(false);

        return duration;
    }

    @Override
    protected void onSetActive(ScreenId successor) {
        if (successor != null) {
            animationModifier = createModifier(true);
        }
    }

    @Override
    public void onRender() {
        if (animationModifier != null) {
            animationModifier.update();
        }

        super.onRender();
    }

    private class ShiftCallback implements ModifierCallback {

        @Override
        public void call(float value) {
            this.action(value);
        }

        @Override
        public void finish(float value) {
            this.action(value);
        }

        @Override
        public void init(float value) {
            this.action(value);
        }

        private void action(float value) {
            context.getBatch().getProjectionMatrix().translate(value, 0, 0);
        }

    }
}
