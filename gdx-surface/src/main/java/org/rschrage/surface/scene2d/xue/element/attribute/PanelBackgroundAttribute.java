package org.rschrage.surface.scene2d.xue.element.attribute;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import org.rschrage.surface.resource.ResourceHandler;
import org.rschrage.surface.resource.ResourceUnit;
import org.rschrage.surface.resource.ResourceUnitType;
import org.rschrage.surface.resource.ResourceUtils;
import org.rschrage.surface.scene2d.widget.Panel;
import de.verygame.xue.mapping.tag.attribute.AbstractAttribute;

/**
 * @author Rico Schrage
 */

public class PanelBackgroundAttribute extends AbstractAttribute<Panel, String> {
    private static final String NAME = "background";

    private ResourceHandler res;

    public PanelBackgroundAttribute(ResourceHandler res) {
        this.res = res;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void apply(Panel element, String value) {
        ResourceUnit unit = ResourceUtils.lookUp(value, ResourceUnit.class);
        if (unit == null) {
            throw new IllegalArgumentException(value);
        }
        if (unit.getUnitType() == ResourceUnitType.TEXTURE_REGION) {
            element.setBackground(new TextureRegionDrawable(res.getRegion(unit)));
        }
    }
}
