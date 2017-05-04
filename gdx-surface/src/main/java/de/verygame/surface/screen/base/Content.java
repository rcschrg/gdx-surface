package de.verygame.surface.screen.base;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.utils.Disposable;

/**
 * @author Rico Schrage
 *
 * Describes the api to the actual content.
 */
public interface Content extends Disposable {

    /**
     * Will be called after the screen, which holds a reference to this object, gets added to a screen switch.
     *
     * @param screenContext context of the screen. Hold information about the screen switch and the screen itself.
     */
    void onBind(ScreenContext screenContext);

    /**
     * Will be called when the underlying screen gets activated.
     * If you want to handle gdx input please do not call Gdx.input.setInputProcessor(...). Instead
     * you can use the given inputHandler here do add your processor to a chain.
     *
     * @param predecessor predecessor of the screen
     * @param inputHandler Will chain input processors
     */
    void onActivate(ScreenId predecessor, InputMultiplexer inputHandler);

    /**
     * Will be called when underlying screen gets deactivated. It's common to remove any added input processors
     * from the input handler.
     *
     * @param successor successor of the screen
     * @param inputHandler Will chain input processors
     * @return delay before screen will get deactivated
     */
    float onDeactivate(ScreenId successor, InputMultiplexer inputHandler);

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
