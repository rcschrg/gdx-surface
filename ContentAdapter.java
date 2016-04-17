package de.verygame.square.core;

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
    public void onActivate(ScreenId predecessor, InputMultiplexer inputMultiplexer) {
        //default: do nothing
    }

    @Override
    public void onDeactivate(ScreenId successor, InputMultiplexer inputMultiplexer) {
        //default: do nothing
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
