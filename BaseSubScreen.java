package de.verygame.square.core;

import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * @author Rico Schrage
 */
public abstract class BaseSubScreen extends BaseScreen implements SubScreen {

    /**
     * Constructs a basic screen.
     *
     * @param viewport viewport, which should be used to manage glViewport
     * @param content  content of the screen
     */
    public BaseSubScreen(Viewport viewport, Content content) {
        super(viewport, content);
    }

    @Override
    public void setModal(boolean modal) {
        //TODO implement
    }
}
