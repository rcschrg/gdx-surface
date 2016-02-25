package de.verygame.square.core;

import com.badlogic.gdx.utils.Disposable;

/**
 * @author Rico Schrage
 */
public interface Content  extends Disposable {
    void onBind(ScreenContext screenContext);
    void onActivate(ScreenId predecessor);
    void onDeactivate(ScreenId successor);
    void onUpdate ();
    void onRender ();
    void onPause ();
    void onResume ();
    void onResize (int width, int height);
}
