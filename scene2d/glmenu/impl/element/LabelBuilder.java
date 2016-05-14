package de.verygame.square.core.scene2d.glmenu.impl.element;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import de.verygame.square.core.resource.Resource;
import de.verygame.square.core.resource.ResourceHandler;
import de.verygame.square.core.resource.ResourceUtils;
import de.verygame.square.util.FontUtils;
import de.verygame.square.util.glmenu.exception.AttributeUnknownException;

/**
 * @author Rico Schrage
 */
public class LabelBuilder extends GenericElementBuilder<Label> {

    private static final String ATTRIBUTE_TEXT = "text";
    private static final String ATTRIBUTE_FONT = "font";
    private static final String ATTRIBUTE_FONT_SIZE = "font-size";

    private final ResourceHandler res;
    private Resource font;
    private int fontSize = -1;
    private boolean dirty;

    public LabelBuilder(Skin skin, ResourceHandler resourceHandler) {
       this(resourceHandler, new Label("", skin));
    }

    public LabelBuilder(ResourceHandler resourceHandler, Label label) {
        super(label);

        this.res = resourceHandler;
    }

    @Override
    protected void applyStringSpecial(String attr, String value) throws AttributeUnknownException {
        Label label = (Label) element;
        switch (attr) {
            case ATTRIBUTE_FONT:
                font = ResourceUtils.lookUp(value, Resource.class);
                dirty = true;
                return;
            case ATTRIBUTE_TEXT:
                label.setText(value);
                return;
            default:
                break;
        }

        super.applyStringSpecial(attr, value);
    }

    @Override
    protected void applyIntSpecial(String attr, int value) throws AttributeUnknownException {
        if (ATTRIBUTE_FONT_SIZE.equals(attr)) {
            fontSize = value;
            dirty = true;
            return;
        }

        super.applyIntSpecial(attr, value);
    }

    @Override
    protected void applyFloatSpecial(String attribute, float value) throws AttributeUnknownException {
        if (ATTRIBUTE_FONT_SIZE.equals(attribute)) {
            applyIntSpecial(attribute, (int) value);
            return;
        }

        super.applyFloatSpecial(attribute, value);
    }

    @Override
    public void postBuild() {
        if (dirty && font != null && fontSize != -1) {
            Label label = (Label) element;
            FreeTypeFontParameter para = FontUtils.obtainParameterBuilder().size(fontSize).build();
            label.getStyle().font = res.createCachedFont(font, para);
            label.setStyle(label.getStyle());
        }
    }

    @Override
    public Actor getElement() {
        return element;
    }

}
