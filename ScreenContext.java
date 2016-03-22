package de.verygame.square.core;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * @author Rico Schrage
 */
public interface ScreenContext {

    Viewport getViewport();
    PolygonSpriteBatch getBatch();
    void addSubScreen(SubScreenId id, SubScreen subScreen);
    SubScreen getActiveSubScreen();
    void showScreen(SubScreenId id);
    void hideScreen(SubScreenId id);

}
