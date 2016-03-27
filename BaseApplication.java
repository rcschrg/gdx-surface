package de.verygame.square.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.verygame.square.core.event.Event;
import de.verygame.square.core.event.EventListener;
import de.verygame.square.resource.ResourceHandler;

/**
 * @author Rico Schrage
 *
 * Provides an ordered, convenient entry point for game applications. Creates and handles batch and resourceHandler.
 * It also offers screen management.
 *
 * @see ScreenSwitch
 */
public abstract class BaseApplication implements ApplicationListener, EventListener {

    /** Main resource handler of the application */
    protected ResourceHandler resourceHandler;
    /** Main batch of the game */
    protected PolygonSpriteBatch batch;
    /** Main viewport of the application, can be overwritten per screen */
    protected Viewport viewport;
    /** True if the resources has been loaded */
    private boolean init = false;
    /** Responsible for screen switching */
    private ScreenSwitch screenSwitch = new ScreenSwitch();

    /**
     * Convenience method for {@link ScreenSwitch#addScreen(ScreenId, Screen)}.
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
     * In this method you should create and add all your screens (use {@link #add(ScreenId, Screen)}) for adding
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

    /**
     * Will be called while the resource handler is loading the specified resources.
     */
    protected abstract void renderLoadingScreen();

    @Override
    public void create() {
        this.batch = new PolygonSpriteBatch();
        this.resourceHandler = new ResourceHandler();
        this.viewport = createViewport();

        this.screenSwitch.setBatch(batch);
        this.screenSwitch.addDependency("batch", batch);
        this.screenSwitch.addDependency("resourceHandler", resourceHandler);
        this.screenSwitch.addDependency("viewport", viewport);

        this.loadResources(resourceHandler);
    }

    @Override
    public void render() {

        screenSwitch.updateSwitch();
        screenSwitch.updateScreen();

        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        if (!resourceHandler.update()) {
            this.renderLoadingScreen();
        }
        else if (init) {
            screenSwitch.renderScreen();
        }
        else {
            this.init = true;
            screenSwitch.setActive(createScreens());
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
