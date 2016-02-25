package de.verygame.square.core;

import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * @author Rico Schrage
 */
public class SubScreenContext extends ScreenSwitch implements ScreenContext {

    /** List of subScreen's plus visibility flag */
    protected final ScreenSwitch screenSwitch;

    /** viewport of the screen (manages the glViewport) */
    protected final Viewport viewport;

    /** True if a subScreen is visible  */
    protected boolean showSubScreen = false;

    public SubScreenContext(Viewport viewport) {
        super();

        this.viewport = viewport;
        this.screenSwitch = new ScreenSwitch();
    }

    public void applyViewport() {
        viewport.apply();
    }

    public void updateViewport(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public Viewport getViewport() {
        return viewport;
    }

    @Override
    public void addSubScreen(SubScreenId id, SubScreen subScreen) {
        this.screenSwitch.addScreen(id, subScreen);
    }

    @Override
    public SubScreen getActiveSubScreen() {
        return (SubScreen) screenSwitch.getActiveScreen();
    }

    @Override
    public void showScreen(SubScreenId id) {
        showSubScreen = true;
        screenSwitch.setActive(id);
    }

    @Override
    public void hideScreen(SubScreenId id) {
        showSubScreen = false;
    }

}
