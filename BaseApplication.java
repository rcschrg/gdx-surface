package de.verygame.square.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.verygame.square.core.event.Event;
import de.verygame.square.core.event.EventListener;
import de.verygame.square.core.resource.Resource;
import de.verygame.square.core.resource.ResourceHandler;
import de.verygame.square.core.screen.base.Screen;
import de.verygame.square.core.screen.base.ScreenId;
import de.verygame.square.core.screen.base.ScreenSwitch;

/**
 * @author Rico Schrage
 *
 * Provides an ordered, convenient entry point for game applications. Creates and handles batch and resourceHandler.
 * It also offers screen management.
 *
 * @see de.verygame.square.core.screen.base.ScreenSwitch
 */
public abstract class BaseApplication implements ApplicationListener, EventListener {

    /** Main resource handler of the application */
    protected ResourceHandler resourceHandler;
    /** Main batch of the game */
    protected PolygonSpriteBatch batch;
    /** Main viewport of the application, can be overwritten per screen */
    protected Viewport viewport;
    /** Responsible for screen switching */
    protected ScreenSwitch screenSwitch;
    /** Renders fps to screen, if isDebug() == true */
    protected FPSOverlay fpsOverlay;
    /** True if all res loaded */
    private boolean init = false;

    /**
     * Convenience method for {@link de.verygame.square.core.screen.base.ScreenSwitch#addScreen(de.verygame.square.core.screen.base.ScreenId, de.verygame.square.core.screen.base.Screen)}.
     *
     * @param id id of the screen
     * @param screen screen
     */
    protected void add(ScreenId id, Screen screen) {
        if (batch == null) {
            throw new IllegalStateException("You must not call this method before create() has been called!");
        }
        screenSwitch.addScreen(id, screen);
    }

    /**
     * Sets the screen active which is mapped to the given id.
     *
     * @param id id of the screen you want to activate
     */
    protected void setActive(ScreenId id) {
        screenSwitch.setActive(id);
    }

    /**
     * @return the currently active screen
     */
    protected ScreenId getActiveScreenId() {
        return screenSwitch.getActiveScreenId();
    }

    /**
     * In this method you should create and add all your screens (use {@link #add(de.verygame.square.core.screen.base.ScreenId, Screen)}) for adding
     * screens.
     *
     * @return Id of the active screen
     */
    protected abstract ScreenId createScreens();

    /**
     * This method should return a viewport, which will be used by the whole game.
     *
     * @return Viewport of the game
     */
    protected abstract Viewport createViewport();

    /**
     * In this method you can load all your resources with help of the resource handler. <br>
     * The given resource handler is the same as {@link #resourceHandler}.
     *
     * @param resourceHandler equals to {@link #resourceHandler}
     */
    protected abstract void loadResources(ResourceHandler resourceHandler);

    @Override
    public void create() {
        this.batch = new PolygonSpriteBatch();
        this.resourceHandler = new ResourceHandler();
        this.viewport = createViewport();
        this.screenSwitch = new ScreenSwitch();
        this.fpsOverlay = new FPSOverlay();

        this.screenSwitch.setBatch(batch);
        this.screenSwitch.addDependency("batch", batch);
        this.screenSwitch.addDependency("resourceHandler", resourceHandler);
        this.screenSwitch.addDependency("viewport", viewport);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        this.screenSwitch.setInputHandler(inputMultiplexer);
        Gdx.input.setInputProcessor(inputMultiplexer);

        this.preLoadResources(resourceHandler);
        this.screenSwitch.setActive(createLoadingScreen());

        this.loadResources(resourceHandler);
    }

    protected abstract ScreenId createLoadingScreen();

    /**
     * It's important to note, that the amount of resources loaded here have to be very small and you have to load it using
     * blocking methods like {@link ResourceHandler#waitFor(Resource)}.
     * @param resourceHandler Handler of the resources
     */
    protected void preLoadResources(ResourceHandler resourceHandler) {
        //can be used for loading screen resources
    }

    protected boolean isDebug() {
        return false;
    }

    @Override
    public void render() {

        screenSwitch.updateSwitch();
        screenSwitch.updateScreen();

        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        screenSwitch.renderScreen();

        if (isDebug()) {
            fpsOverlay.render(batch);
        }

        if (!init && resourceHandler.update()) {
            screenSwitch.setActive(createScreens());
            init = true;
        }

        batch.end();
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
        batch.dispose();
        resourceHandler.dispose();
    }

    @Override
    public void handleEvent(Event event, Object... attachedObjects) {
        //use annotations instead
    }
}
