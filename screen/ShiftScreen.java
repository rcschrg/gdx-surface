package de.verygame.square.core.screen;

import com.badlogic.gdx.utils.viewport.Viewport;

import de.verygame.square.core.Content;
import de.verygame.square.util.modifier.SingleValueModifier;
import de.verygame.square.util.modifier.base.Modifier;
import de.verygame.square.util.modifier.base.SimpleModifierCallback;

/**
 * @author Rico Schrage
 *         <p/>
 *         Implements a shift on switch animation.
 */
public class ShiftScreen extends ModifierScreen {

    public enum Shift {
        LEFT, RIGHT;
    }

    /** Shift direction of the screen */
    private final Shift shift;

    /**
     * Constructs a basic screen.
     *
     * @param viewport viewport, which should be used to manage glViewport
     * @param content  content of the screen
     */
    public ShiftScreen(Viewport viewport, Content content) {
        this(viewport, content, Shift.LEFT, 1f);
    }

    /**
     * Constructs a shift screen.
     *
     * @param viewport viewport of the parent screen
     * @param content content to be displayed
     * @param shift shift direction
     * @param duration duration of the animation
     */
    public ShiftScreen(Viewport viewport, final Content content, Shift shift, float duration) {
        super(viewport, content, duration);

        this.shift = shift;
    }

    @Override
    protected Modifier createModifierInactive() {
        return (shift == Shift.LEFT) ? new SingleValueModifier(duration, 0, -context.getViewport().getWorldWidth(), new ShiftCallback()) :
                new SingleValueModifier(duration, 0, context.getViewport().getWorldWidth(), new ShiftCallback());
    }

    @Override
    protected Modifier createModifierActive() {
        return (shift == Shift.LEFT) ? new SingleValueModifier(duration, -context.getViewport().getWorldWidth(), 0, new ShiftCallback()) :
                new SingleValueModifier(duration, context.getViewport().getWorldWidth(), 0, new ShiftCallback());
    }

    /**
     * Callback to perform a screen shift
     */
    private class ShiftCallback extends SimpleModifierCallback {
        @Override
        protected void action(float value) {
            context.getBatch().getProjectionMatrix().translate(value, 0, 0);
        }
    }
}
