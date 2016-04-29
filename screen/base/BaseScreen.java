package de.verygame.square.core.screen.base;

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

    /** Index of the deactivate transition in the transition array */
    private static final int T_OUT_INDEX = 0;
    /** Index of the activate transition in the transition array */
    private static final int T_IN_INDEX = 1;

    /** content, which is displayed by the screen */
    protected final Content content;
    /** All not content related information about the screen */
    protected final SubScreenContext context;
    /** De|Activate transition*/
    protected Transition[] transition;

    /**
     * Constructs a basic screen.
     *
     * @param viewport viewport, which should be used to manage glViewport
     * @param content content of the screen
     */
    public BaseScreen(Viewport viewport, Content content) {
        this.context = new SubScreenContext(viewport);
        this.content = content;
        this.transition = new Transition[2];
    }

    public void setOutTransition(Transition out) {
        this.transition[T_OUT_INDEX] = out;
    }

    public void setInTransition(Transition in) {
        this.transition[T_IN_INDEX] = in;
    }

    @Override
    public void onActivate(ScreenId predecessor) {
        context.applyViewport();
        context.updateViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        context.onActivate(predecessor);
        content.onActivate(predecessor, context.getInputHandler());

        if (transition[T_IN_INDEX] != null) {
            transition[T_IN_INDEX].reset(context);
        }
    }

    @Override
    public float onDeactivate(ScreenId successor) {
        float delay = context.onDeactivate(successor);
        content.onDeactivate(successor, context.getInputHandler());

        if (transition[T_OUT_INDEX] != null) {
            transition[T_OUT_INDEX].reset(context);
            return Math.max(transition[T_OUT_INDEX].getDuration(), delay);
        }
        return 0;
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
        for (final Transition t : transition) {
            if (t != null) {
                t.update();
                t.preRender(context.getBatch());
            }
        }

        content.onRender();
        context.renderScreen();

        for (final Transition t : transition) {
            if (t != null) {
                t.postRender(context.getBatch());
            }
        }
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
