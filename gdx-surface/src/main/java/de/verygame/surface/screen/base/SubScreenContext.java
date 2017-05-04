package de.verygame.surface.screen.base;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Map;

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
     * Sets the dependency map of the screen switch.
     *
     * @param dependencies map of dependencies
     */
    public void setDependencies(Map<String, Object> dependencies) {
        screenSwitch.setDependencyMap(dependencies);
    }

    /**
     * Sets the batch of the context.
     *
     * @param polygonSpriteBatch batch
     */
    public void setBatch(PolygonSpriteBatch polygonSpriteBatch) {
        screenSwitch.setBatch(polygonSpriteBatch);
    }

    /**
     * Sets the inputHandler of the context.
     *
     * @param inputHandler inputHandler
     */
    public void setInputHandler(InputMultiplexer inputHandler) {
        screenSwitch.setInputHandler(inputHandler);
    }

    public InputMultiplexer getInputHandler() {
        return screenSwitch.getInputHandler();
    }

    public void onActivate(ScreenId screenKey) {
        if (screenSwitch.getActiveScreen() != null) {
            screenSwitch.getActiveScreen().onActivate(screenKey);
        }
    }

    public float onDeactivate(ScreenId screenKey) {
        if (screenSwitch.getActiveScreen() != null) {
            return screenSwitch.getActiveScreen().onDeactivate(screenKey);
        }
        return 0;
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

    public void update() {
        screenSwitch.updateSwitch();
        screenSwitch.updateScreen();
    }

    public void renderScreen() {
        if (showSubScreen) {
            screenSwitch.renderScreen();
        }
    }

    public void resizeSubScreen(int width, int height) {
        screenSwitch.resize(width, height);
    }

    public void pauseSubScreen() {
        screenSwitch.pause();
    }

    public void resumeSubScreen() {
        screenSwitch.resume();
    }

    public void dispose() {
        screenSwitch.dispose();
    }

    @Override
    public Viewport getViewport() {
        return viewport;
    }

    @Override
    public PolygonSpriteBatch getBatch() {
        return screenSwitch.getBatch();
    }

    @Override
    public void addSubScreen(SubScreenId id, SubScreen subScreen) {
        if (screenSwitch.getBatch() == null) {
            throw new IllegalStateException("Parent screen have to be attached to a screen switch!");
        }
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
    public void initialize(SubScreenId id) {
        showSubScreen = true;
        screenSwitch.setScreenSimple(id);
    }

    @Override
    public void hideScreen() {
        showSubScreen = false;
    }


}
