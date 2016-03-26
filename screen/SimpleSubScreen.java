package de.verygame.square.core.screen;

import com.badlogic.gdx.utils.viewport.Viewport;

import de.verygame.square.core.BaseSubScreen;
import de.verygame.square.core.Content;
import de.verygame.square.core.ScreenId;

/**
 * @author Rico Schrage
 */
public class SimpleSubScreen extends BaseSubScreen {

    /**
     * Constructs a basic screen.
     *
     * @param viewport viewport, which should be used to manage glViewport
     * @param content  content of the screen
     */
    public SimpleSubScreen(Viewport viewport, Content content) {
        super(viewport, content);
    }

    @Override
    protected float onSetInactive(ScreenId predecessor) {
        return 0;
    }

    @Override
    protected void onSetActive(ScreenId successor) {

    }
}
