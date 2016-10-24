package de.verygame.square.core.scene2d.glmenu.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.InputStream;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import de.verygame.square.core.resource.ResourceHandler;
import de.verygame.square.core.scene2d.Scene2DMapping;
import de.verygame.xue.GuiXue;
import de.verygame.xue.handler.ElementsTagGroupHandler;
import de.verygame.xue.input.XueInputEvent;
import de.verygame.xue.mapping.TagMapping;
import de.verygame.xue.util.action.ActionSequence;

/**
 * @author Rico Schrage
 *
 * Stage-Wrapper of GuiXue.
 */
public class XueStage extends Stage {

    private final GuiXue<Actor> xue;

    /**
     * Constructs scene2d stage using a xml file, which describes the setup.
     *
     * @param batch contains all strings in various languages
     * @param resourceFile xml file, which describes the setup of the menu
     * @param resourceHandler contains language bundles and skins.
     */
    public XueStage(Batch batch, InputStream resourceFile, ResourceHandler resourceHandler) {
        this(batch, new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), resourceFile, resourceHandler);
    }

    public XueStage(Batch batch, Viewport viewport, InputStream resourceFile, ResourceHandler resourceHandler) {
        super(viewport, batch);

        xue = new GuiXue<>(resourceFile, new Scene2DMapping(resourceHandler));

        xue.addLoadTask(new GuiXue.LoadTask() {

            @Override
            public void postLoad() {
                Set<Map.Entry<String, Actor>> entries = xue.getElementMap().entrySet();
                for (final Map.Entry<String, Actor> entry : entries) {
                    Actor currentElement = xue.getElementMap().get(entry.getKey());

                    if (currentElement.getParent() != null) {
                        continue;
                    }

                    addActor(currentElement);
                }
                getActors().sort(new Comparator<Actor>() {
                    @Override
                    public int compare(Actor o1, Actor o2) {
                        return o1.getZIndex() - o2.getZIndex();
                    }
                });

            }

            @Override
            public void preLoad() {
                //nothing to do
            }
        });
    }

    public void addElementMapping(TagMapping<Actor> mapping) {
        xue.addMappingUnsafe(ElementsTagGroupHandler.class, mapping);
    }

    /**
     * Get the number of elements. Must not be called before <code>xue.load()</code>}.
     *
     * @return number of elements
     */
    public int getElementSize() {
        return xue.getElementSize();
    }

    public float calcActionSequenceDeactivationDelay() {
        Map<String, ActionSequence> aMap = xue.getActionSequenceMap();
        float maxDelay = 0f;
        for (Map.Entry<String, ActionSequence> entry : aMap.entrySet()) {
            if (entry.getValue().getStartEvent() == XueInputEvent.DEACTIVATE) {
                float duration = entry.getValue().getDuration();
                if (duration > maxDelay) {
                    maxDelay = duration;
                }
            }
        }
        return maxDelay;
    }

    public void onInputEvent(XueInputEvent inputEvent) {
        xue.onInputEvent(inputEvent);
    }

    public void bind(Object bindTarget) {
        xue.bind(bindTarget);
    }

    public Actor getElementByName(final String name) {
        return xue.getElementByName(name);
    }
    public Map<String, Actor> getElementMap() {
        return xue.getElementMap();
    }

    public void load() {
        xue.load();
    }

    @Override
    public void act() {
        super.act();

        xue.onUpdate(Gdx.graphics.getDeltaTime());
    }

    /**
     * The behaviour of {@link #draw()} has changed in this implementation. In this stage you have to
     * call {@link Batch#begin()} before and {@link Batch#end()} after calling this method.
     *
     * {@inheritDoc}
     */
    @Override
    public void draw() {
        Camera camera = getViewport().getCamera();
        camera.update();

        if (!getRoot().isVisible()) {
            return;
        }

        Batch batch = getBatch();
        if (batch != null) {
            getRoot().draw(batch, 1);
        }
    }

}
