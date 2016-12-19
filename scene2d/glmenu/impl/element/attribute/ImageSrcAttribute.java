package de.verygame.square.core.scene2d.glmenu.impl.element.attribute;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import de.verygame.square.core.resource.ResourceHandler;
import de.verygame.square.core.resource.ResourceUnit;
import de.verygame.square.core.resource.ResourceUtils;
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
