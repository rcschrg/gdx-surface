package de.verygame.surface.scene2d.xue.element;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.List;

import de.verygame.surface.resource.ResourceHandler;
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
        List<Attribute<? super Image, ?>> att = buildAttributeList(new de.verygame.surface.scene2d.xue.element.attribute.ImageSrcAttribute(resourceHandler));
        att.addAll(super.defineAttributes()); // attributes of the parent should be used as well
        return att;
    }

    @Override
    protected List<AttributeGroup<? super Image>> defineAttributeGroups() {
        return buildAttributeGroupList();
    }
}
