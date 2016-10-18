package de.verygame.square.core.scene2d.glmenu.impl.element;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.List;

import de.verygame.square.core.scene2d.glmenu.impl.element.attribute.ActorAlignmentAttribute;
import de.verygame.square.core.scene2d.glmenu.impl.element.attribute.AlphaAttribute;
import de.verygame.square.core.scene2d.glmenu.impl.element.attribute.ColorAttribute;
import de.verygame.square.core.scene2d.glmenu.impl.element.attribute.SimpleGenericAttribute;
import de.verygame.xue.mapping.tag.XueAbstractElementTag;
import de.verygame.xue.mapping.tag.attribute.Attribute;
import de.verygame.xue.mapping.tag.attribute.AttributeGroup;

/**
 * @author Rico Schrage
 */
public class ElementTag<T extends Actor> extends XueAbstractElementTag<T> {

    public ElementTag(T element) {
        super(element);
    }

    @Override
    protected List<Attribute<? super T, ?>> defineAttributes() {
        return buildAttributeList(new AlphaAttribute(), new ColorAttribute(), new ActorAlignmentAttribute(), new SimpleGenericAttribute<Actor, Float>("x"),
                new SimpleGenericAttribute<Actor, Float>("y"), new SimpleGenericAttribute<Actor, Float>("width"), new SimpleGenericAttribute<Actor, Float>("height"));
    }

    @Override
    protected List<AttributeGroup<? super T>> defineAttributeGroups() {
        return buildAttributeGroupList();
    }

}
