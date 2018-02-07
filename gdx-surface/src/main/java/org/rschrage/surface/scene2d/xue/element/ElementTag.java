package org.rschrage.surface.scene2d.xue.element;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.List;

import de.verygame.xue.mapping.tag.XueAbstractElementTag;
import de.verygame.xue.mapping.tag.attribute.Attribute;
import de.verygame.xue.mapping.tag.attribute.AttributeGroup;
import org.rschrage.surface.scene2d.xue.element.attribute.ActorAlignmentAttribute;
import org.rschrage.surface.scene2d.xue.element.attribute.AlphaAttribute;
import org.rschrage.surface.scene2d.xue.element.attribute.ColorAttribute;
import org.rschrage.surface.scene2d.xue.element.attribute.SimpleGenericAttribute;

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
        return buildAttributeList(new AlphaAttribute(), new ColorAttribute(), new ActorAlignmentAttribute(), new SimpleGenericAttribute<Actor, Float>("x"),
                new SimpleGenericAttribute<Actor, Float>("y"), new SimpleGenericAttribute<Actor, Float>("width"), new SimpleGenericAttribute<Actor, Float>("height"));
    }

    @Override
    protected List<AttributeGroup<? super T>> defineAttributeGroups() {
        return buildAttributeGroupList();
    }

}
