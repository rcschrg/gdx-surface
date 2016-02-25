package de.verygame.square.core;

import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * @author Rico Schrage
 */
public interface Screen extends Disposable {
    void onActivate(ScreenId predecessor);
    float onDeactivate(ScreenId descendant);
    void onResize (int width, int height);
    void onUpdate ();
    void onRender ();
    void onPause ();
    void onResume ();
    Viewport getViewport();
}