package de.verygame.square.core.screen;

import com.badlogic.gdx.InputMultiplexer;

import de.verygame.square.core.screen.base.Content;
import de.verygame.square.core.screen.base.ScreenContext;
import de.verygame.square.core.screen.base.ScreenId;
import de.verygame.square.core.annotation.Dependency;
import de.verygame.square.core.event.EventHandler;
import de.verygame.square.core.resource.Resource;
import de.verygame.square.core.resource.ResourceHandler;
import de.verygame.square.core.scene2d.glmenu.impl.GLMenuStage;

/**
 * @author Rico Schrage
 */
public abstract class StageContent extends EventHandler implements Content {

    /** Resource handler which contains all ui resources */
    @Dependency
    protected ResourceHandler resourceHandler;

    /** Stage, which loads the xml */
    protected GLMenuStage stage;

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

        this.stage = new GLMenuStage(context.getBatch(), context.getViewport(), resourceHandler.getXMLAsStream(defineXML()), resourceHandler, defineSkin());
        this.stage.bind(this);

        this.preLoad();

        this.stage.loadMenu();

        this.postLoad();
    }

    @Override
    public void onActivate(ScreenId predecessor, InputMultiplexer inputMultiplexer) {
        inputMultiplexer.addProcessor(stage);
    }

    @Override
    public void onDeactivate(ScreenId successor, InputMultiplexer inputMultiplexer) {
        inputMultiplexer.removeProcessor(stage);
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
        //default: do nothing
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
