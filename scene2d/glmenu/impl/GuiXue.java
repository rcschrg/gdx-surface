package de.verygame.square.core.scene2d.glmenu.impl;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.verygame.util.ReflectionUtils;
import de.verygame.xue.BasicXue;
import de.verygame.xue.annotation.Bind;
import de.verygame.xue.constants.Constant;
import de.verygame.xue.exception.XueException;
import de.verygame.xue.handler.ActionSequenceTagGroupHandler;
import de.verygame.xue.mapping.DummyGlobalMappings;
import de.verygame.xue.mapping.GlobalMappings;
import de.verygame.xue.util.action.ActionSequence;

/**
 * @author Rico Schrage
 */

public class GuiXue<T> extends BasicXue<T> {
    private final ActionSequenceTagGroupHandler actionSequenceTagGroupHandler;
    private final List<LoadTask> taskList;

    public GuiXue(InputStream xml) {
        this(xml, new DummyGlobalMappings<T>());
    }

    public GuiXue(InputStream xml, GlobalMappings<T> globalMappings) {
        super(xml, globalMappings);

        taskList = new ArrayList<>();
        actionSequenceTagGroupHandler = new ActionSequenceTagGroupHandler(Constant.obtainDefaultMap());

        core.addHandler(actionSequenceTagGroupHandler);
    }

    public Map<String, ActionSequence> getActionSequenceMap() {
        return actionSequenceTagGroupHandler.getActionSequenceMap();
    }

    public Map<String, T> getElementMap() {
        return elementMap;
    }

    public void addLoadTask(LoadTask task) {
        taskList.add(task);
    }

    @Override
    protected void postLoad() {
        //noinspection unchecked
        this.elementMap = (Map<String, T>) core.getResultUnsafe(elementsTagGroupHandler.getClass(), Object.class);

        Set<Map.Entry<String, T>> entries = elementMap.entrySet();
        if (bindTarget == null) {
            return;
        }
        for (final Map.Entry<String, T> entry : entries) {
            List<Field> fields = ReflectionUtils.getAllFields(bindTarget.getClass());
            for (final Field field : fields) {
                if (field.isAnnotationPresent(Bind.class) && field.getName().equals(entry.getKey())) {
                    try {
                        field.setAccessible(true);
                        field.set(bindTarget, entry.getValue());
                    }
                    catch (IllegalAccessException e) {
                        throw new XueException(e);
                    }
                }
            }
        }
        for (final LoadTask task : taskList) {
            task.postLoad();
        }
    }

    @Override
    protected void preLoad() {
        super.postLoad();

        for (final LoadTask task : taskList) {
            task.postLoad();
        }
    }

    public static interface LoadTask {
        void postLoad();
        void preLoad();
    }
}
