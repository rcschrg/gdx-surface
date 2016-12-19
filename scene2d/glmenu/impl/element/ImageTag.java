package de.verygame.square.core.scene2d.glmenu.impl.element;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.List;

import de.verygame.square.core.resource.ResourceHandler;
import de.verygame.square.core.scene2d.glmenu.impl.element.attribute.ImageSrcAttribute;
import de.verygame.xue.mapping.tag.attribute.Attribute;
import de.verygame.xue.mapping.tag.attribute.AttributeGroup;

/**
 * @author Rico Schrage
 */
public class ImageTag extends ElementTag<Image> {

    private final ResourceHandler resourceHandler;

    public ImageTag(Image element, ResourceHandler resourceHandler) {
        super(element);

        this.resourceHandler = resourceHandler;
    }

    @Override
    protected List<Attribute<? super Image, ?>> defineAttributes() {
        List<Attribute<? super Image, ?>> att = buildAttributeList(new ImageSrcAttribute(resourceHandler));
        att.addAll(super.defineAttributes()); // attributes of the parent should be used as well
        return att;
    }

    @Override
    protected List<AttributeGroup<? super Image>> defineAttributeGroups() {
        return buildAttributeGroupList();
    }
}
