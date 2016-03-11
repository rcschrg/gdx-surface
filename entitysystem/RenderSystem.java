package de.verygame.square.core.entitysystem;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;

import java.util.Arrays;

import de.verygame.square.core.components.RenderData;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 10.03.2016.
 */
public class RenderSystem extends BaseEntitySystem {
    ComponentMapper<RenderData> renderDataMapper;


    private final int ARRAY_GROWTH = 20;

    int[] layeredEntities;
    int[] dirtyEntities;

    int entityCount = 0;
    int dirtyCount = 0;

    /**
     * Creates a new EntityProcessingSystem.
     */
    public RenderSystem(int size) {
        super(Aspect.all(RenderData.class));
        layeredEntities = new int[size];
        //fill with dummies which can't be entities
        Arrays.fill(layeredEntities, Integer.MIN_VALUE);
        dirtyEntities = new int[size / 2];
    }

    @Override
    public void processSystem() {

        //layeredEntities start at index 1 for performance reasons
        for (int i = 1; i < entityCount; i++) {
            if (renderDataMapper.get(layeredEntities[i]).dirty) {
                queueReinsert(layeredEntities[i]);
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
        for (int i = 0; i < entityCount; i++){

            if(layeredEntities[i] == e){
                //shift array back together
                for(int j = i; j < entityCount; i++){
                    layeredEntities[j-1] = layeredEntities[j];
                }
                layeredEntities[entityCount] = Integer.MIN_VALUE;
                entityCount--;
            }

        }
    }

    private void insert(int e) {
        if (entityCount == layeredEntities.length - 1){
            resize(layeredEntities);
        }

        RenderData data = renderDataMapper.get(e);
        RenderData temp;

        for (int i = 0; i < entityCount; i++){
            temp = renderDataMapper.get(e);

            if(temp == null){
                layeredEntities[i] = e;
            }
            else if(temp.layerIndex > data.layerIndex){
                //shift everything after current index
                for(int j = entityCount-1; j >= i; i--){
                    layeredEntities[j+1] = layeredEntities[j];
                }
                layeredEntities[i] = e;
                break;
            }

        }

        entityCount++;
    }

    private void reinsert(){
        for (int i = 0; i < dirtyCount; i++){
            insert(dirtyEntities[i]);
        }
        dirtyCount = 0;
    }

    private void queueReinsert(int e) {
        if (dirtyCount == dirtyEntities.length - 1) {
            resize(dirtyEntities);
        }
        dirtyEntities[dirtyCount] = e;
        dirtyCount++;
    }

    /**
     * Increases the array size by the defined ARRAY_GROWTH
     * @param array array to enlarge
     */
    private void resize(int[] array) {
        array = Arrays.copyOf(array, array.length + ARRAY_GROWTH);
    }
}
