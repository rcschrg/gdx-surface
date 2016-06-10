package de.verygame.square.core.scene2d.glmenu.impl.element;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import de.verygame.square.core.resource.Resource;
import de.verygame.square.core.resource.ResourceHandler;
import de.verygame.square.core.resource.ResourceUtils;
import de.verygame.square.util.FontUtils;
import de.verygame.xue.exception.AttributeUnknownException;

/**
 * @author Rico Schrage
 */
public class LabelBuilder extends GenericElementBuilder<Label> {

    private static final String ATTRIBUTE_TEXT = "text";
    private static final String ATTRIBUTE_FONT = "font";
    private static final String ATTRIBUTE_FONT_SIZE = "font-size";
    private static final String ATTRIBUTE_FONT_ALIGN = "align";

    private static final String VALUE_ALIGN_CENTER = "center";
    private static final String VALUE_ALIGN_LEFT = "left";
    private static final String VALUE_ALIGN_RIGHT = "right";

    private final ResourceHandler res;
    private Resource font = null;
    private int fontSize = -1;
    private boolean dirty = false;
    private String align = null;

    public LabelBuilder(Skin skin, ResourceHandler resourceHandler) {
       this(resourceHandler, new Label("", new Label.LabelStyle(skin.get(Label.LabelStyle.class))));
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
                Resource newFont = ResourceUtils.lookUp(value, Resource.class);
                if (font != newFont) {
                    font = newFont;
                    dirty = true;
                }
                return;
            case ATTRIBUTE_TEXT:
                label.setText(value);
                return;
            case ATTRIBUTE_FONT_ALIGN:
                align = value;
                return;
            default:
                break;
        }

        super.applyStringSpecial(attr, value);
    }

    private void applyAlignment(String alignment) {
        Label label = (Label) element;
        label.layout();
        switch (alignment) {
            case VALUE_ALIGN_CENTER:
                element.setX(Gdx.graphics.getWidth()/2f - label.getGlyphLayout().width/2f);
                break;

            case VALUE_ALIGN_LEFT:
                element.setX(0);
                break;

            case VALUE_ALIGN_RIGHT:
                element.setX(Gdx.graphics.getWidth() - label.getGlyphLayout().width);
                break;

            default:
                break;
        }
    }

    @Override
    protected void applyIntSpecial(String attr, int value) throws AttributeUnknownException {
        if (ATTRIBUTE_FONT_SIZE.equals(attr)) {
            if (fontSize != value) {
                fontSize = value;
                dirty = true;
            }
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
            BitmapFont old = label.getStyle().font;
            label.getStyle().font = res.createCachedFont(font, para);
            label.setStyle(label.getStyle());
            res.destroyCachedFont(old);
            dirty = false;
        }
        if (align != null) {
            applyAlignment(align);
        }
    }

    @Override
    public Actor getElement() {
        return element;
    }

}
