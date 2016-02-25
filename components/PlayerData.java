package de.verygame.square.core.components;

import com.artemis.Component;

/**
 * @author Marco Deneke
 *
 * Contains
 *
 * Created by Marco Deneke on 06.01.2016.
 */
public class PlayerData extends Component {


    private boolean hasShield;

    public boolean hasShield() {
        return hasShield;
    }

    public void setHasShield(boolean hasShield) {
        this.hasShield = hasShield;
    }
}
