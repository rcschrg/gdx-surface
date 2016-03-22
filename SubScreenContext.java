package de.verygame.square.core;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * @author Rico Schrage
 *
 * Context which can contain several subscreens.
 */
public class SubScreenContext implements ScreenContext {

    /** List of subScreen's plus visibility flag */
    protected final ScreenSwitch screenSwitch;

    /** viewport of the screen (manages the glViewport) */
    protected final Viewport viewport;

    /** Batch of the screen switch */
    protected PolygonSpriteBatch batch;

    /** True if a subScreen is visible  */
    protected boolean showSubScreen = false;


    /**
     * Constructs a context with the given viewport.
     *
     * @param viewport viewport viewport of the screen
     */
    public SubScreenContext(Viewport viewport) {
        super();

        this.viewport = viewport;
        this.screenSwitch = new ScreenSwitch();
    }

    /**
     * Sets the batch of the context.
     *
     * @param polygonSpriteBatch batch
     */
    public void setBatch(PolygonSpriteBatch polygonSpriteBatch) {
        this.batch = polygonSpriteBatch;
    }

    /**
     * Applies the viewport of the context. Calls {@link Viewport#apply(boolean)}.
     */
    public void applyViewport() {
        viewport.apply(true);
    }

    /**
     * Updates the viewport of the context. Calls {@link Viewport#update(int, int, boolean)}.
     *
     * @param width width of the frame
     * @param height height of the frame
     */
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
            throw new IllegalStateException("Parent screen have to be attached to a screen switch!");
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
    public void hideScreen() {
        showSubScreen = false;
    }

    @Override
    public void update() {
        screenSwitch.updateSwitch();
        screenSwitch.updateScreen();
    }

    @Override
    public void renderScreen() {
        if (showSubScreen) {
            screenSwitch.renderScreen();
        }
    }

    @Override
    public void resize(int width, int height) {
        screenSwitch.resize(width, height);
    }

    @Override
    public void pause() {
        screenSwitch.pause();
    }

    @Override
    public void resume() {
        screenSwitch.resume();
    }

    @Override
    public void dispose() {
        screenSwitch.dispose();
    }

}
