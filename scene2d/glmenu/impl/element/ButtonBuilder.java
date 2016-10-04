package de.verygame.square.core.scene2d.glmenu.impl.element;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import de.verygame.square.core.resource.ResourceHandler;
import de.verygame.xue.exception.AttributeUnknownException;

/**
 * @author Rico Schrage
 */
public class ButtonBuilder extends GenericContainerBuilder<TextButton> {

    private final LabelBuilder labelBuilder;

    public ButtonBuilder(Skin skin, ResourceHandler res) {
        this(res, new TextButton("", skin));
    }
    
    public ButtonBuilder(ResourceHandler res, TextButton element) {
        super(element);
        
        this.labelBuilder = new LabelBuilder(res, element.getLabel());
    }

    @Override
    protected void applyIntSpecial(String attribute, int value) throws AttributeUnknownException {
        labelBuilder.applyIntSpecial(attribute, value);

        super.applyIntSpecial(attribute, value);
    }

    @Override
    protected void applyFloatSpecial(String attr, float value) throws AttributeUnknownException {
        labelBuilder.applyFloatSpecial(attr, value);

        super.applyFloatSpecial(attr, value);
    }

    @Override
    protected void applyStringSpecial(String attr, String value) throws AttributeUnknownException {
        labelBuilder.applyStringSpecial(attr, value);

        super.applyStringSpecial(attr, value);
    }
    
    @Override
    public void preBuild() {
        labelBuilder.preBuild();

        super.preBuild();
    }

    @Override
    public void postBuild() {
        labelBuilder.postBuild();

        super.postBuild();
    }

    @Override
    public Actor getElement() {
        labelBuilder.getElement();

        return super.getElement();
    }
}
