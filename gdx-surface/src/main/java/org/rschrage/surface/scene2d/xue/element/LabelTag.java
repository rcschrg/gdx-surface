package org.rschrage.surface.scene2d.xue.element;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.List;

import org.rschrage.surface.resource.ResourceHandler;
import org.rschrage.surface.scene2d.xue.element.attribute.FontGroupAttribute;
import org.rschrage.surface.scene2d.xue.element.attribute.LabelAlignmentAttribute;
import org.rschrage.surface.scene2d.xue.element.attribute.LabelTextAttribute;
import de.verygame.xue.mapping.tag.attribute.Attribute;
import de.verygame.xue.mapping.tag.attribute.AttributeGroup;

/**
 * @author Rico Schrage
 */
public class LabelTag extends ElementTag<Label> {
    private final ResourceHandler res;

    public LabelTag(Skin skin, ResourceHandler resourceHandler) {
       this(resourceHandler, new Label("", new Label.LabelStyle(skin.get(Label.LabelStyle.class))));
    }

    public LabelTag(ResourceHandler resourceHandler, Label label) {
        super(label);

        this.res = resourceHandler;
    }

    @Override
    protected List<Attribute<? super Label, ?>> defineAttributes() {
        List<Attribute<? super Label, ?>> att = buildAttributeList(new LabelAlignmentAttribute(), new LabelTextAttribute());
        att.addAll(super.defineAttributes()); // attributes of the parent should be used as well
        return att;
    }

    @Override
    protected List<AttributeGroup<? super Label>> defineAttributeGroups() {
        return buildAttributeGroupList(new FontGroupAttribute(res));
    }
}
