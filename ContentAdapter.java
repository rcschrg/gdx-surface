package de.verygame.square.core;

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
    public void onActivate(ScreenId predecessor) {
        //default: do nothing
    }

    @Override
    public void onDeactivate(ScreenId successor) {
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
