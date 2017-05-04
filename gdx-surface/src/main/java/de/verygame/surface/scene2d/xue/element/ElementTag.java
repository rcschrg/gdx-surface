package de.verygame.surface.scene2d.xue.element;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.List;

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

    public void removeAttributeByClass(Class<? extends Attribute<?, ?>> c) {
        preBuild();
        for (int i = attributes.size()-1; i >= 0; --i) {
            if (attributes.get(i).getClass() == c) {
                attributes.remove(i);
            }
        }
    }

    public void removeAttributeByName(String... s) {
        preBuild();
        for (String string : s) {
            removeAttributeByName(string);
        }
    }

    public void removeAttributeByName(String s) {
        for (int i = attributes.size()-1; i >= 0; --i) {
            if (attributes.get(i).getName().equals(s)) {
                attributes.remove(i);
            }
        }
    }

    @Override
    protected List<Attribute<? super T, ?>> defineAttributes() {
        return buildAttributeList(new de.verygame.surface.scene2d.xue.element.attribute.AlphaAttribute(), new de.verygame.surface.scene2d.xue.element.attribute.ColorAttribute(), new de.verygame.surface.scene2d.xue.element.attribute.ActorAlignmentAttribute(), new de.verygame.surface.scene2d.xue.element.attribute.SimpleGenericAttribute<Actor, Float>("x"),
                new de.verygame.surface.scene2d.xue.element.attribute.SimpleGenericAttribute<Actor, Float>("y"), new de.verygame.surface.scene2d.xue.element.attribute.SimpleGenericAttribute<Actor, Float>("width"), new de.verygame.surface.scene2d.xue.element.attribute.SimpleGenericAttribute<Actor, Float>("height"));
    }

    @Override
    protected List<AttributeGroup<? super T>> defineAttributeGroups() {
        return buildAttributeGroupList();
    }

}
