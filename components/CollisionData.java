package de.verygame.square.core.components;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;

import org.jbox2d.collision.AABB;

import de.verygame.square.util.collision.CollisionCallback;

/**
 * @author Marco Deneke
 *
 * Created by Marco Deneke on 04.01.2016.
 *
 * Contains various data shared by different colliders
 */
@PooledWeaver
public class CollisionData extends Component {

    private AABB aabb = new AABB();

    private boolean active = true;

    public CollisionCallback callback = new CollisionCallback() {
        @Override
        public void collideWith(int eid) {
            //add collision reaction here
        }
    };

    public AABB getAABB() {
        return aabb;
    }

    public void setAABB(AABB aabb) {
        this.aabb = aabb;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean isActive) {
        this.active = isActive;
    }
}
