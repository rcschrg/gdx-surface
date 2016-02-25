package de.verygame.square.core.entitysystem;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySubscription;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.IntBag;

import org.jbox2d.callbacks.TreeCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.collision.broadphase.DynamicTree;
import org.jbox2d.common.Vec2;

import java.awt.Component;
import java.util.HashMap;

import de.verygame.square.core.components.BoxCollider;
import de.verygame.square.core.components.CircleCollider;
import de.verygame.square.core.components.CollisionData;
import de.verygame.square.core.components.PlayerData;
import de.verygame.square.core.components.PolygonCollider;
import de.verygame.square.core.components.RectTransform;
import de.verygame.square.util.collision.CollisionUtils;

/**
 * @author Marco Deneke
 *         <p>
 *         Created by Marco Deneke on 06.01.2016.
 *         <p>
 *         Handles the broadphase and collision events
 */
public class CollisionSystem extends EntityProcessingSystem {


    ComponentMapper<PlayerData> playerDataMapper;
    ComponentMapper<CollisionData> collisionDataMapper;
    ComponentMapper<BoxCollider> boxColliderMapper;
    ComponentMapper<CircleCollider> circleColliderMapper;
    ComponentMapper<PolygonCollider> polygonColliderMapper;
    ComponentMapper<RectTransform> rectTransformMapper;

    HashMap<Integer, Integer> proxyMap = new HashMap<>();

    AspectSubscriptionManager asm;
    EntitySubscription subscription;

    DynamicTree broadTree = new DynamicTree();

    private Entity current = null;

    protected TreeCallback callback = new TreeCallback() {
        @Override
        public boolean treeCallback(int proxyId) {

            Entity other = world.getEntity(proxyMap.get(proxyId));

            if (checkCollision(current, other)) {
                collisionDataMapper.get(other).callback.collideWith(current);
                collisionDataMapper.get(current).callback.collideWith(other);
            } else {
                return true;
            }

            return false;
        }
    };

    /**
     * Creates a new EntityProcessingSystem.
     */
    @SuppressWarnings("unchecked")
    public CollisionSystem() {
        super(Aspect.all(PlayerData.class, CollisionData.class));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initialize() {
        asm = world.getAspectSubscriptionManager();
        subscription = asm.get(Aspect.all(CollisionData.class).one(BoxCollider.class, CircleCollider.class, PolygonCollider.class));

        playerDataMapper = world.getMapper(PlayerData.class);
        collisionDataMapper = world.getMapper(CollisionData.class);
        boxColliderMapper = world.getMapper(BoxCollider.class);
        circleColliderMapper = world.getMapper(CircleCollider.class);
        polygonColliderMapper = world.getMapper(PolygonCollider.class);
        rectTransformMapper = world.getMapper(RectTransform.class);

        subscription.addSubscriptionListener(new EntitySubscription.SubscriptionListener() {

            @Override
            public void inserted(IntBag entities) {
                for (int i = 0; i < entities.size(); i++) {
                    int proxy = broadTree.createProxy(getGlobalAABB(entities.get(i)), entities.get(i));

                    proxyMap.put(entities.get(i), proxy);
                }
            }

            @Override
            public void removed(IntBag entities) {
                for (int i = 0; i < entities.size(); i++) {
                    broadTree.destroyProxy(proxyMap.get(entities.get(i)));

                    proxyMap.remove(entities.get(i));
                }
            }
        });
    }

    @Override
    protected void process(Entity e) {
        current = e;

        if (collisionDataMapper.get(e) != null && collisionDataMapper.get(e).isActive()) {
            broadTree.query(callback, getGlobalAABB(e));
        }
    }

    /**
     * Moves the AABB inside the DynamicTree
     *
     * @param e  Entity
     * @param dx x offset
     * @param dy y offset
     */
    public void move(Entity e, float dx, float dy) {
        Vec2 displacement = new Vec2(dx, dy);
        AABB aabb = getGlobalAABB(e);

        broadTree.moveProxy(proxyMap.get(e.getId()), aabb, displacement);
    }

    /**
     * Returns the global AABB {@link AABB} of an Entity.
     *
     * @param e Entity
     * @return global AABB
     */
    protected AABB getGlobalAABB(Entity e) {

        RectTransform transform = rectTransformMapper.get(e);
        CollisionData collisionData = collisionDataMapper.get(e);

        float x = transform.getX();
        float y = transform.getY();

        AABB aabb = new AABB(collisionData.getAABB());

        aabb.lowerBound.addLocal(x, y);
        aabb.upperBound.addLocal(x, y);

        return aabb;
    }

    private boolean checkCollision(Entity e1, Entity e2) {

        CollisionData cd2 = collisionDataMapper.get(e2);

        if (!cd2.isActive()) {
            return false;
        }

        RectTransform rt1 = rectTransformMapper.get(e1);
        RectTransform rt2 = rectTransformMapper.get(e2);

        BoxCollider b1 = boxColliderMapper.getSafe(e1);
        BoxCollider b2 = boxColliderMapper.getSafe(e2);

        CircleCollider c1 = circleColliderMapper.getSafe(e1);
        CircleCollider c2 = circleColliderMapper.getSafe(e2);

        PolygonCollider p1 = polygonColliderMapper.getSafe(e1);
        PolygonCollider p2 = polygonColliderMapper.getSafe(e2);

        if(b1 != null){
            if(b2 != null){
                return CollisionUtils.checkRectangleRectangleCollision(rt1, b1, rt2, b2);
            }else if(c2 != null){
                return CollisionUtils.checkRectangleCircleCollision(rt1, b1, rt2, c2);
            }else{
                return CollisionUtils.checkRectanglePolygonCollision(rt1, b1, rt2, p2);
            }
        }else if(c1 != null){
            if(b2 != null){
                return CollisionUtils.checkRectangleCircleCollision(rt2, b2, rt1, c1);
            }else if(c2 != null){
                return CollisionUtils.checkCircleCircleCollision(rt1, c1, rt2, c2);
            }else{
                return CollisionUtils.checkCirclePolygonCollision(rt1, c1, rt2, p2);
            }
        }else {
            if(b2 != null){
                return CollisionUtils.checkRectanglePolygonCollision(rt2, b2, rt1, p1);
            }else if(c2 != null){
                return CollisionUtils.checkCirclePolygonCollision(rt2, c2, rt1, p1);
            }else{
                return CollisionUtils.checkPolygonPolygonCollision(rt1, p1, rt2, p2);
            }
        }
    }

    private AABB getGlobalAABB(int id) {
        return getGlobalAABB(world.getEntity(id));
    }
}
