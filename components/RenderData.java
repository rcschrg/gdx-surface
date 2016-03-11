package de.verygame.square.core.components;

import com.artemis.Component;

import de.verygame.square.util.rendering.Drawable;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 10.03.2016.
 */
public class RenderData extends Component {

    //True when layerIndex is changed at runtime
    public boolean dirty = false;
    //Default Layer Index
    public int layerIndex = 1;

    public Drawable drawable;

}
