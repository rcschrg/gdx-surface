package org.rschrage.surface.scene2d.xue.element.attribute;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import org.rschrage.surface.resource.ResourceHandler;
import org.rschrage.surface.resource.ResourceUnit;
import org.rschrage.surface.resource.ResourceUtils;
import de.verygame.xue.mapping.tag.attribute.AbstractAttribute;

/**
 * @author Rico Schrage
 */
public class ImageSrcAttribute extends AbstractAttribute<Image, String> {
    private static final String NAME = "image";

    private final ResourceHandler res;

    public ImageSrcAttribute(ResourceHandler resourceHandler) {
        this.res = resourceHandler;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void apply(Image element, String value) {
        ResourceUnit unit = ResourceUtils.lookUp(value, ResourceUnit.class);
        element.setDrawable(new TextureRegionDrawable(res.getRegion(unit)));
    }
}
