package de.verygame.square.core.screen.base;

import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * @author Rico Schrage
 */
public abstract class BaseSubScreen extends BaseScreen implements de.verygame.square.core.screen.base.SubScreen {

    /**
     * Constructs a basic screen.
     *
     * @param viewport viewport, which should be used to manage glViewport
     * @param content  content of the screen
     */
    public BaseSubScreen(Viewport viewport, de.verygame.square.core.screen.base.Content content) {
        super(viewport, content);
    }

    @Override
    public void setModal(boolean modal) {
        //TODO implement
    }
}
