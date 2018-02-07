package org.rschrage.surface.scene2d.xue.element;

import java.util.List;

import org.rschrage.surface.resource.ResourceHandler;
import org.rschrage.surface.scene2d.xue.element.attribute.PanelBackgroundAttribute;
import org.rschrage.surface.scene2d.widget.Panel;
import de.verygame.xue.mapping.tag.attribute.Attribute;

/**
 * @author Rico Schrage
 */
public class PanelTag extends ContainerTag<Panel> {
    private ResourceHandler res;

    public PanelTag(Panel instance, ResourceHandler res) {
        super(instance);

        this.res = res;
    }

    @Override
    protected List<Attribute<? super Panel, ?>> defineAttributes() {
        List<Attribute<? super Panel, ?>> att = buildAttributeList(new PanelBackgroundAttribute(res));
        att.addAll(super.defineAttributes());
        return att;
    }
}
