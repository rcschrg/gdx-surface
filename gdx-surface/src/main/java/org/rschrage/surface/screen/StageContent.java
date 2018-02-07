package org.rschrage.surface.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

import org.rschrage.surface.annotation.Dependency;
import org.rschrage.surface.event.EventHandler;
import org.rschrage.surface.resource.Resource;
import org.rschrage.surface.resource.ResourceHandler;
import org.rschrage.surface.scene2d.xue.ElementMapping;
import org.rschrage.surface.scene2d.xue.XueStage;
import org.rschrage.surface.screen.base.Content;
import org.rschrage.surface.screen.base.ScreenContext;
import org.rschrage.surface.screen.base.ScreenId;
import de.verygame.xue.input.XueInputEvent;

/**
 * @author Rico Schrage
 */
public abstract class StageContent extends EventHandler implements Content {

    /** Resource handler which contains all ui resources */
    @Dependency
    protected ResourceHandler resourceHandler;

    /** Stage, which loads the xml */
    protected XueStage stage;

    /** Context of the screen */
    protected ScreenContext context;

    /**
     * Should return the xml resource, which describes the xml of the glmenuStage.
     *
     * @return Resource of the type XML
     */
    protected abstract Resource defineXML();

    /**
     * Should return the skin which will be used by all stage elements.
     *
     * @return Resource of the type skin
     */
    protected abstract Resource defineSkin();

    protected void preLoad() {}
    protected void postLoad() {}

    @Override
    public void onBind(ScreenContext context) {
        this.context = context;

        this.stage = new XueStage(context.getBatch(), context.getViewport(), resourceHandler.getXMLAsStream(defineXML()), resourceHandler);
        this.stage.addElementMapping(new ElementMapping(resourceHandler, defineSkin()));
        this.stage.bind(this);

        this.preLoad();

        this.stage.load();

        this.postLoad();
    }
     
    @Override
    public void onActivate(ScreenId predecessor, InputMultiplexer inputMultiplexer) {
        inputMultiplexer.addProcessor(stage);

        onResize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.onInputEvent(XueInputEvent.ACTIVATE);
    }

    @Override
    public float onDeactivate(ScreenId successor, InputMultiplexer inputMultiplexer) {
        inputMultiplexer.removeProcessor(stage);

        stage.onInputEvent(XueInputEvent.DEACTIVATE);
        return stage.calcActionSequenceDeactivationDelay();
    }

    @Override
    public void onUpdate() {
        stage.act();
    }

    @Override
    public void onRender() {
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void onResize(int width, int height) {
        stage.getRoot().setBounds(0, 0, width, height);
        stage.onInputEvent(XueInputEvent.RESIZE);
    }

    @Override
    public void onPause() {
        //default: do nothing
    }

    @Override
    public void onResume() {
        //default: do nothing
    }
}
