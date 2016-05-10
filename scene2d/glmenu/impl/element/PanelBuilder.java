package de.verygame.square.core.scene2d.glmenu.impl.element;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import de.verygame.square.core.resource.ResourceHandler;
import de.verygame.square.core.resource.ResourceUnit;
import de.verygame.square.core.resource.ResourceUnitType;
import de.verygame.square.core.resource.ResourceUtils;
import de.verygame.square.core.scene2d.widget.Panel;
import de.verygame.square.util.glmenu.exception.AttributeUnknownException;

/**
 * @author Rico Schrage
 */
public class PanelBuilder extends GenericContainerBuilder<Panel> {

    private static final String ATTRIBUTE_BACKGROUND = "background";

    private ResourceHandler res;

    public PanelBuilder(Panel instance, ResourceHandler res) {
        super(instance);

        this.res = res;
    }

    @Override
    protected void applyStringSpecial(String key, String value) throws AttributeUnknownException {
        if (ATTRIBUTE_BACKGROUND.equals(key)) {
            ResourceUnit unit = ResourceUtils.lookUp(value, ResourceUnit.class);
            if (unit == null) {
                throw new IllegalArgumentException(value);
            }
            de.verygame.square.core.scene2d.widget.Panel panel = (de.verygame.square.core.scene2d.widget.Panel) element;
            if (unit.getUnitType() == ResourceUnitType.TEXTURE_REGION) {
                panel.setBackground(new TextureRegionDrawable(res.getRegion(unit)));
            }
        }
        super.applyStringSpecial(key, value);
    }

}
