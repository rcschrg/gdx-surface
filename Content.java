package de.verygame.square.core;

import com.badlogic.gdx.utils.Disposable;

/**
 * @author Rico Schrage
 *
 * Describes the api to the actual content.
 */
public interface Content  extends Disposable {

    /**
     * Will be called after the screen which holds a reference to this object gets added to a screen switch.
     *
     * @param screenContext context of the screen. Hold information about the screen switch and the screen itself.
     */
    void onBind(ScreenContext screenContext);

    /**
     * Will be called when the underlying screen gets activated.
     *
     * @param predecessor predecessor of the screen
     */
    void onActivate(ScreenId predecessor);

    /**
     * Will be called when underlying screen gets deactivated.
     *
     * @param successor successor of the screen
     */
    void onDeactivate(ScreenId successor);

    /**
     * Will be called on update. It will only be called when the underlying screen is active.
     */
    void onUpdate ();

    /**
     * Will be called on render. It will only be called when the underlying screen is active.
     */
    void onRender ();

    /**
     * Will always be called on pause.
     */
    void onPause ();

    /**
     * Will always be called on resume.
     */
    void onResume ();

    /**
     * Will be called on resize. It will only be called when the underlying screen is active.
     */
    void onResize (int width, int height);
}
