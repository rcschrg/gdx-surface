package org.rschrage.surface.screen;

import com.badlogic.gdx.utils.viewport.Viewport;

import org.rschrage.surface.screen.base.BaseSubScreen;
import org.rschrage.surface.screen.base.Content;

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

}
