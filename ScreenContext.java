package de.verygame.square.core;

import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * @author Rico Schrage
 */
public interface ScreenContext {

    Viewport getViewport();
    void addSubScreen(SubScreenId id, SubScreen subScreen);
    SubScreen getActiveSubScreen();
    void showScreen(SubScreenId id);
    void hideScreen(SubScreenId id);

}
