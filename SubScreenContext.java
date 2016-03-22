package de.verygame.square.core;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * @author Rico Schrage
 */
public class SubScreenContext extends ScreenSwitch implements ScreenContext {

    /** List of subScreen's plus visibility flag */
    protected final ScreenSwitch screenSwitch;

    /** viewport of the screen (manages the glViewport) */
    protected final Viewport viewport;

    /** Batch of the screen switch */
    protected PolygonSpriteBatch batch;

    /** True if a subScreen is visible  */
    protected boolean showSubScreen = false;


    public SubScreenContext(Viewport viewport) {
        super();

        this.viewport = viewport;
        this.screenSwitch = new ScreenSwitch();
    }

    public void setBatch(PolygonSpriteBatch polygonSpriteBatch) {
        this.batch = polygonSpriteBatch;
    }

    public void applyViewport() {
        viewport.apply(true);
    }

    public void updateViewport(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public Viewport getViewport() {
        return viewport;
    }

    @Override
    public PolygonSpriteBatch getBatch() {
        return batch;
    }

    @Override
    public void addSubScreen(SubScreenId id, SubScreen subScreen) {
        if (batch == null) {
            throw new IllegalStateException("The parent screen have to be added to screen switch before.");
        }
        this.screenSwitch.addScreen(id, subScreen, batch);
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
