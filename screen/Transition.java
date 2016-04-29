package de.verygame.square.core.screen;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import de.verygame.square.core.ScreenContext;

/**
 * @author Rico Schrage
 */
public interface Transition {

    void reset(ScreenContext context);
    void update();

    void preRender(PolygonSpriteBatch batch);
    void postRender(PolygonSpriteBatch batch);

    float getDuration();
    boolean isFinished();

}
