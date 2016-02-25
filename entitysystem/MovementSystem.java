package de.verygame.square.core.entitysystem;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;

import de.verygame.square.core.components.CollisionData;
import de.verygame.square.core.components.Movement;
import de.verygame.square.core.components.RectTransform;

/**
 * @author Marco Deneke
 *         <p>
 *         Created by Marco Deneke on 04.01.2016.
 *         <p>
 *         Proccesses all movements of entitys.
 */
public class MovementSystem extends EntityProcessingSystem {


    ComponentMapper<RectTransform> transformMapper;
    ComponentMapper<Movement> movementMapper;

    ComponentMapper<CollisionData> collisionDataMapper;

    CollisionSystem collisionSystem;
    /**
     * Creates a new EntityProcessingSystem.
     */
    @SuppressWarnings("unchecked")
    public MovementSystem() {
        super(Aspect.all(RectTransform.class, Movement.class));
    }

    @Override
    public void initialize() {
        transformMapper = world.getMapper(RectTransform.class);
        movementMapper = world.getMapper(Movement.class);

        collisionDataMapper = world.getMapper(CollisionData.class);
        collisionSystem = world.getSystem(CollisionSystem.class);
    }

    @Override
    protected void process(Entity e) {
        //Get mapped components
        RectTransform t = transformMapper.get(e);
        Movement m = movementMapper.get(e);
        CollisionData c = collisionDataMapper.get(e);

        //process movement
        t.setX(t.getX() + m.getxVelocity());
        t.setY(t.getY() + m.getyVelocity());

        if (c != null) {
            collisionSystem.move(e, m.getxVelocity(), m.getyVelocity());
        }
    }
}
