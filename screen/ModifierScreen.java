package de.verygame.square.core.screen;

import com.badlogic.gdx.utils.viewport.Viewport;

import de.verygame.square.core.Content;
import de.verygame.square.core.ScreenId;
import de.verygame.square.util.modifier.base.Modifier;

/**
 * @author Rico Schrage
 */
public abstract class ModifierScreen extends SimpleSubScreen {

    protected final float duration;

    protected Modifier animationModifier;

    /**
     * Constructs a basic screen.
     *
     * @param viewport viewport, which should be used to manage glViewport
     * @param content  content of the screen
     */
    public ModifierScreen(Viewport viewport, final Content content, final float duration) {
        super(viewport, content);

        this.duration = duration;
    }

    protected abstract Modifier createModifierInactive();
    protected abstract Modifier createModifierActive();

    @Override
    protected float onSetInactive(ScreenId predecessor) {
        animationModifier = createModifierInactive();

        return duration;
    }

    @Override
    protected void onSetActive(ScreenId successor) {
        if (successor != null) {
            animationModifier = createModifierActive();
        }
    }

    @Override
    public void onRender() {
        if (animationModifier != null) {
            animationModifier.update();
        }

        super.onRender();
    }

}
