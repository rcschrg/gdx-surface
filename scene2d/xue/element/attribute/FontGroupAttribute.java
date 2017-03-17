package de.verygame.square.core.scene2d.xue.element.attribute;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.List;

import de.verygame.square.core.resource.Resource;
import de.verygame.square.core.resource.ResourceHandler;
import de.verygame.square.core.resource.ResourceUtils;
import de.verygame.square.util.FontUtils;
import de.verygame.xue.annotation.AttributeHandler;
import de.verygame.xue.mapping.tag.attribute.AbstractAttributeGroup;
import de.verygame.xue.mapping.tag.attribute.AttributeGroupElementMeta;
import de.verygame.xue.util.GroupMetaUtils;

/**
 * @author Rico Schrage
 */

public class FontGroupAttribute extends AbstractAttributeGroup<Label> {

    private final ResourceHandler res;
    private Resource font = null;
    private int fontSize = -1;
    private boolean dirty = false;
    private boolean first = true;

    public FontGroupAttribute(ResourceHandler resourceHandler) {
        res = resourceHandler;
    }

    @Override
    public List<AttributeGroupElementMeta> getGroupMeta() {
        return GroupMetaUtils.buildMetaList(new String[]{"font", "fontSize"},
                                            new Class<?>[]{String.class, Float.class});
    }

    @AttributeHandler
    public void applyFont(Label element, String value) {
        Resource newFont = ResourceUtils.lookUp(value, Resource.class);
        if (font != newFont) {
            font = newFont;
            dirty = true;
        }
    }

    @AttributeHandler
    public void applyFontSize(Label element, Float value) {
        if (fontSize != value) {
            fontSize = (int)(float)value;
            dirty = true;
        }
    }

    @Override
    public void end(Label element) {
        if (first) {
            // first font will be assigned via skin, so the resource handler have to know that these references exists
            res.increaseCachedFontReferenceCount(element.getStyle().font);
            first = false;
        }
        if (dirty && font != null && fontSize != -1) {
            FreeTypeFontGenerator.FreeTypeFontParameter para = FontUtils.obtainParameterBuilder().size(fontSize).build();
            BitmapFont old = element.getStyle().font;
            element.getStyle().font = res.createCachedFont(font, para);
            element.setStyle(element.getStyle());
            res.destroyCachedFont(old);
            dirty = false;
        }
    }
}
