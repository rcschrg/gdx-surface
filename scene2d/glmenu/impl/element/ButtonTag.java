package de.verygame.square.core.scene2d.glmenu.impl.element;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import de.verygame.square.core.resource.ResourceHandler;

/**
 * @author Rico Schrage
 */
public class ButtonTag extends ElementTag<TextButton> {

    public ButtonTag(Skin skin, ResourceHandler res) {
        this(new TextButton("", skin), res);
    }
    
    public ButtonTag( TextButton element, ResourceHandler res) {
        super(element);

        linkXueTag(new LabelTag(res, element.getLabel()));
    }
}
