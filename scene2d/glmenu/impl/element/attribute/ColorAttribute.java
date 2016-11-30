package de.verygame.square.core.scene2d.glmenu.impl.element.attribute;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;

import de.verygame.square.util.ColorUtils;
import de.verygame.xue.mapping.tag.attribute.AbstractAttribute;

/**
 * @author Rico Schrage
 */

public class ColorAttribute extends AbstractAttribute<Actor, String> {
    private static final String NAME = "color";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void apply(Actor element, String value) {
	if (value.startsWith("rgba(")) {
	    String[] n = value.substring(5, value.length()-1).split(",");
	    if (n.length != 4) {
	        throw new IllegalArgumentException("You have to provide values for r, g, b and a!"); 
            }
	    element.getColor().set(Integer.parseInt(n[0].trim())/255f, Integer.parseInt(n[1].trim())/255f, Integer.parseInt(n[2].trim())/255f, Float.parseFloat(n[3].trim()));
	}
	else if (value.startsWith("#")) {
             Color color = ColorUtils.fromHex(value);
             element.setColor(color);
	}    
    }
}
