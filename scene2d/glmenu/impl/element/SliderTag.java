package de.verygame.square.core.scene2d.glmenu.impl.element;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;

import java.util.List;

import de.verygame.square.core.scene2d.glmenu.impl.element.attribute.SliderAttributes;
import de.verygame.xue.mapping.tag.attribute.Attribute;

/**
 * @author Rico Schrage
 */
public class SliderTag extends ElementTag<Slider> {

    public SliderTag(Skin skin) {
        super(new Slider(0, 100, 1, false, skin));
    }

    @Override
    protected List<Attribute<? super Slider, ?>> defineAttributes() {
        List<Attribute<? super Slider, ?>> att =  buildAttributeList(new SliderAttributes.MaxValue(), new SliderAttributes.MinValue(),
                new SliderAttributes.StepSize(), new SliderAttributes.Value());
        att.addAll(super.defineAttributes());
        return att;
    }
}
