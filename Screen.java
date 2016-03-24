package de.verygame.square.core;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.utils.Disposable;

/**
 * @author Rico Schrage
 *
 * Describes a logical screen used by the {@link ScreenSwitch}.
 */
public interface Screen extends Disposable {

    /**
     * Will be called when the screen gets added to a screen switch.
     *
     * @param batch batch of the screen switch
     */
    void onAdd(PolygonSpriteBatch batch);

    /**
     * Will be called when the screen gets set active.
     *
     * @param predecessor predecessor of the screen
     */
    void onActivate(ScreenId predecessor);

    /**
     * Will be called when the screen gets set inactive. With returning a number > 0 you can delay the
     * switch to perform an animation.
     *
     * @param successor successor of the screen
     * @return number > 0 as delay
     */
    float onDeactivate(ScreenId successor);

    /**
     * Will be called on resize when the screen is active.
     *
     * @param width width of the frame
     * @param height height of the frame
     */
    void onResize(int width, int height);

    /**
     * Will be called on update when the screen is active.
     */
    void onUpdate();

    /**
     * Will be called on render when the screen is active.
     */
    void onRender();

    /**
     * Will always be called on pause when the screen has been added to the screen switch.
     */
    void onPause();

    /**
     * Will always be called on resume when the screen has been added to the screen switch.
     */
    void onResume();

    /**
     * @return content of the screen
     */
    Content getContent();
}