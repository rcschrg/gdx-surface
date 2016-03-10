package de.verygame.square.core.components;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.annotations.PooledWeaver;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;

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

    private boolean isActive = true;

    public CollisionCallback callback = new CollisionCallback() {
        @Override
        public void collideWith(int eid) {

        }
    };

    public AABB getAABB() {
        return aabb;
    }

    public void setAABB(AABB aabb) {
        this.aabb = aabb;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
