package de.verygame.square.core;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * @author Rico Schrage
 *
 * Context of a screen. Provides information about the context of the screen and the screen itself.
 */
public interface ScreenContext {

    /**
     * @return the viewport of the screen
     */
    Viewport getViewport();

    /**
     * @return the batch of the switch
     */
    PolygonSpriteBatch getBatch();

    /**
     * Add a sub screen to the screen.
     *
     * @param id id of the screen
     * @param subScreen the screen itself
     */
    void addSubScreen(SubScreenId id, SubScreen subScreen);

    /**
     * @return the active sub screen
     */
    SubScreen getActiveSubScreen();

    /**
     * Shows the screen mapped to the given id.
     *
     * @param id id of an added subscreen
     */
    void showScreen(SubScreenId id);

    /**
     * Hides the currently active screen.
     */
    void hideScreen();

}
