package de.verygame.square.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Map;

/**
 * @author Rico Schrage
 *
 * Represents a single screen. It's intendet to add screens to a {@link ScreenSwitch}, so it's possible to switch the screen calling just one method.
 * Normally you want to inherit from this class. This allows you to define the behaviour of the screen when it gets activated/deactivated. Additionally a screen manages it's own viewport, so
 * a childclass should always create a viewport by itself.
 * <br>
 * The content of the screen have to be created seperatly (implement {@link Content}).
 */
public abstract class BaseScreen implements Screen {

    /** content, which is displayed by the screen */
    protected final Content content;

    /** All not content related information about the screen */
    protected final SubScreenContext context;

    /**
     * Constructs a basic screen.
     *
     * @param viewport viewport, which should be used to manage glViewport
     * @param content content of the screen
     */
    public BaseScreen(Viewport viewport, Content content) {
        this.context = new SubScreenContext(viewport);
        this.content = content;
    }

    /**
     * Will be called when the screen gets set inactive.
     *
     * @param predecessor predecessor of the screen
     * @return delay time to perform an animation
     */
    protected abstract float onSetInactive(ScreenId predecessor);

    /**
     * Will be called when screen gets set active.
     *
     * @param successor succesor of the screen
     */
    protected abstract void onSetActive(ScreenId successor);

    @Override
    public void onActivate(ScreenId predecessor) {
        context.applyViewport();
        context.updateViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        context.onActivate(predecessor);
        content.onActivate(predecessor, context.getInputHandler());

        onSetActive(predecessor);
    }

    @Override
    public float onDeactivate(ScreenId successor) {
        context.onDeactivate(successor);
        content.onDeactivate(successor, context.getInputHandler());

        return onSetInactive(successor);
    }

    @Override
    public void onAdd(PolygonSpriteBatch batch, InputMultiplexer inputMultiplexer, Map<String, Object> dependencyMap) {
        context.setBatch(batch);
        context.setInputHandler(inputMultiplexer);
        context.setDependencies(dependencyMap);

        content.onBind(context);
    }

    @Override
    public void onResize(int width, int height) {
        content.onResize(width, height);

        context.updateViewport(width, height);
        context.resizeSubScreen(width, height);
    }

    @Override
    public void onUpdate() {
        content.onUpdate();

        context.update();
        context.getBatch().setProjectionMatrix(context.getViewport().getCamera().combined);
    }

    @Override
    public void onRender() {
        content.onRender();

        context.renderScreen();
    }

    @Override
    public void onPause() {
        content.onPause();

        context.pauseSubScreen();
    }

    @Override
    public void onResume() {
        content.onResume();

        context.resumeSubScreen();
    }

    @Override
    public void dispose() {
        content.dispose();

        context.dispose();
    }

    @Override
    public Content getContent() {
        return content;
    }
}
