package org.rschrage.surface.util.task;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * @author Rico Schrage
 */
public class DelayedTaskTest {

    @Test
    public void testUpdate() {
        final List<Integer> testList = new ArrayList<>();
        final DelayedTask delayedTask = new DelayedTask(2, new Task() {
            @Override
            public void work() {
                testList.add(0);
            }
        });

        delayedTask.update(1);
        assertFalse(delayedTask.hasFinished());
        assertEquals(testList.size(), 0);

        delayedTask.update(1);
        assertTrue(delayedTask.hasFinished());
        assertEquals(testList.size(), 1);
    }

}