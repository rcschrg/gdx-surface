package de.verygame.surface;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.verygame.surface.event.EventRouteListener;
import de.verygame.surface.resource.Resource;
import de.verygame.surface.resource.ResourceHandler;
import de.verygame.surface.resource.ResourceUtils;
import de.verygame.surface.screen.base.Screen;
import de.verygame.surface.screen.base.ScreenId;
import de.verygame.surface.screen.base.ScreenSwitch;

/**
 * @author Rico Schrage
 *
 * Provides an ordered, convenient entry point for game applications. Creates and handles batch and resourceHandler.
 * It also offers screen management.
 *
 * @see de.verygame.surface.screen.base.ScreenSwitch
 */
public abstract class BaseApplication implements ApplicationListener, EventRouteListener {

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
    /** Settings object */
    protected Settings settings;
    /** True if all res loaded */
    private boolean init = false;

    /**
     * Convenience method for {@link de.verygame.surface.screen.base.ScreenSwitch#addScreen(de.verygame.surface.screen.base.ScreenId, de.verygame.surface.screen.base.Screen)}.
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
     * In this method you should create and add all your screens (use {@link #add(de.verygame.surface.screen.base.ScreenId, Screen)}) for adding
     * screens.
     *
     * @return Id of the active screen
     */
    protected abstract ScreenId createScreens();

    /**
     * Here you can create some general settings for the application.
     *
     * @return {@link Settings}
     */
    protected abstract Settings createSettings();

    /**
     * In this method you can load all your resources with help of the resource handler. <br>
     * The given resource handler is the same as {@link #resourceHandler}.
     *
     * @param resourceHandler equals to {@link #resourceHandler}
     */
    protected abstract void loadResources(ResourceHandler resourceHandler);

    @Override
    public void create() {
        this.settings = createSettings();
        this.batch = new PolygonSpriteBatch();
        this.resourceHandler = new ResourceHandler();
        this.viewport = settings.getViewport();
        this.screenSwitch = new ScreenSwitch();
        this.fpsOverlay = new FPSOverlay();

        this.screenSwitch.setBatch(batch);
        this.screenSwitch.addDependency("batch", batch);
        this.screenSwitch.addDependency("resourceHandler", resourceHandler);
        this.screenSwitch.addDependency("viewport", viewport);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        this.screenSwitch.setInputHandler(inputMultiplexer);
        Gdx.input.setInputProcessor(inputMultiplexer);
        ResourceUtils.addResourceImplementation();

        this.preLoadResources(resourceHandler);
        this.screenSwitch.setActive(createLoadingScreen());

        this.loadResources(resourceHandler);
        this.init();
    }


    protected abstract ScreenId createLoadingScreen();

    protected void init() {}

    protected void postLoadResources(ResourceHandler res) {}

    /**
     * It's important to note, that the amount of resources loaded here have to be very small and you have to load it using
     * blocking methods like {@link ResourceHandler#waitFor(Resource)}.
     * @param resourceHandler Handler of the resources
     */
    protected void preLoadResources(ResourceHandler resourceHandler) {
        //can be used for loading screen resources
    }

    @Override
    public void render() {

        screenSwitch.updateSwitch();
        screenSwitch.updateScreen();

        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        screenSwitch.renderScreen();

        if (settings.isDebug()) {
            fpsOverlay.render(batch);
        }

        if (!init && resourceHandler.update()) {
            postLoadResources(resourceHandler);
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

}
