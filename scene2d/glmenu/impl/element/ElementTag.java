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
