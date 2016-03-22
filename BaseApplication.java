package de.verygame.square.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.verygame.square.resource.ResourceHandler;

/**
 * @author Rico Schrage
 *
 * Provides an ordered, convenient entry point for game applications. Creates and handles batch and resourceHandler.
 * It also offers screen management.
 *
 * @see ScreenSwitch
 */
public abstract class BaseApplication extends ScreenSwitch implements ApplicationListener {

    /** Main resource handler of the application */
    protected ResourceHandler resourceHandler;
    /** Main batch of the game */
    protected PolygonSpriteBatch batch;
    /** Main viewport of the application, can be overwritten per screen */
    protected Viewport viewport;
    /** True if the resources has been loaded */
    private boolean init = false;

    /**
     * Convenience method for {@link #addScreen(ScreenId, Screen, PolygonSpriteBatch)}.
     *
     * @param id id of the screen
     * @param screen screen
     */
    protected void add(ScreenId id, Screen screen) {
        if (batch == null) {
            throw new IllegalStateException("You must not call this method before create() has been called!");
        }
        this.addScreen(id, screen, batch);
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

        this.loadResources(resourceHandler);
    }

    @Override
    public void render() {

        this.updateSwitch();
        this.updateScreen();

        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        if (!resourceHandler.update()) {
            this.renderLoadingScreen();
        }
        else if (init) {
            this.renderScreen();
        }
        else {
            this.init = true;
            this.setActive(createScreens());
        }

        batch.end();
    }


}
