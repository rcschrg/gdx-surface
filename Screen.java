package de.verygame.square.core;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.utils.Disposable;

/**
 * @author Rico Schrage
 */
public interface Screen extends Disposable {
    void onAdd(PolygonSpriteBatch batch);
    void onActivate(ScreenId predecessor);
    float onDeactivate(ScreenId descendant);
    void onResize (int width, int height);
    void onUpdate ();
    void onRender ();
    void onPause ();
    void onResume ();
}