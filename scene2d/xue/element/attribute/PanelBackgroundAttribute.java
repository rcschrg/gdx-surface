package de.verygame.square.core.scene2d.xue.element.attribute;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import de.verygame.square.core.resource.ResourceHandler;
import de.verygame.square.core.resource.ResourceUnit;
import de.verygame.square.core.resource.ResourceUnitType;
import de.verygame.square.core.resource.ResourceUtils;
import de.verygame.square.core.scene2d.widget.Panel;
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
