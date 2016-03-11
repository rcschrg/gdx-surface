package de.verygame.square.core.entitysystem;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.utils.IntArray;

import java.util.Arrays;

import de.verygame.square.core.components.RenderData;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 10.03.2016.
 */
public class RenderSystem extends BaseEntitySystem {
    ComponentMapper<RenderData> renderDataMapper;


    private final int ARRAY_GROWTH = 20;

    IntArray layeredEntities;
    IntArray dirtyEntities;

    int entityCount = 0;
    int dirtyCount = 0;

    /**
     * Creates a new EntityProcessingSystem.
     */
    public RenderSystem(int size) {
        super(Aspect.all(RenderData.class));
        layeredEntities = new IntArray(size);
        //fill with dummies which can't be entities
        Arrays.fill(layeredEntities.items, Integer.MIN_VALUE);
        dirtyEntities = new IntArray(size/2);
    }

    @Override
    public void processSystem() {

        //layeredEntities start at index 1 for performance reasons
        for (int i = 1; i < entityCount; i++) {
            if (renderDataMapper.get(layeredEntities.get(i)).dirty) {
                queueReinsert(layeredEntities.get(i));
            }

            //draw on batch
        }

        reinsert();
    }

    @Override
    protected void inserted(int e) {
        insert(e);
    }

    @Override
    protected void removed(int e) {
        remove(e);
    }

    @Override
    protected void begin() {

    }

    @Override
    protected void end() {

    }

    private void remove(int e){

        layeredEntities.removeValue(e);
        layeredEntities.set(entityCount, Integer.MIN_VALUE);
        entityCount--;
    }

    private void insert(int e) {
        if (entityCount == layeredEntities.size - 1){
            layeredEntities.ensureCapacity(layeredEntities.size + ARRAY_GROWTH);
        }

        RenderData data = renderDataMapper.get(e);
        RenderData temp;

        for (int i = 0; i < entityCount; i++){
            temp = renderDataMapper.get(e);

            if(temp == null){
                layeredEntities.set(i, e);
            }
            else if(temp.layerIndex > data.layerIndex){
                layeredEntities.insert(i, e);
                break;
            }

        }

        entityCount++;
    }

    private void reinsert(){
        for (int i = 0; i < dirtyCount; i++){
            insert(dirtyEntities.get(i));
        }
        dirtyCount = 0;
    }

    private void queueReinsert(int e) {
        if (dirtyCount == dirtyEntities.size - 1) {
            dirtyEntities.ensureCapacity(layeredEntities.size + ARRAY_GROWTH);
        }
        dirtyEntities.set(dirtyCount, e);
        dirtyCount++;
    }

}
