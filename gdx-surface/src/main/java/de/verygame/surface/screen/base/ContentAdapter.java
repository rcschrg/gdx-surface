package de.verygame.surface.screen.base;

import com.badlogic.gdx.InputMultiplexer;

/**
 * @author Rico Schrage
 *
 * Adapter for {@link Content}.
 */
public class ContentAdapter implements Content {

    @Override
    public void onBind(ScreenContext screenContext) {
        //default: do nothing
    }

    @Override
    public void onActivate(de.verygame.surface.screen.base.ScreenId predecessor, InputMultiplexer inputMultiplexer) {
        //default: do nothing
    }

    @Override
    public float onDeactivate(de.verygame.surface.screen.base.ScreenId successor, InputMultiplexer inputMultiplexer) {
        return 0f;
    }

    @Override
    public void onUpdate() {
        //default: do nothing
    }

    @Override
    public void onRender() {
        //default: do nothing
    }

    @Override
    public void onPause() {
        //default: do nothing
    }

    @Override
    public void onResume() {
        //default: do nothing
    }

    @Override
    public void onResize(int width, int height) {
        //default: do nothing
    }

    @Override
    public void dispose() {
        //default: do nothing
    }
}
