package de.verygame.surface.util.math.function;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Rico Schrage
 */
public class LinearEaseFunctionTest {

    @Test
    public void testGetInstance() throws Exception {
        //when
        LinearEaseFunction function = LinearEaseFunction.getInstance();

        //then
        assertTrue(function != null);
    }

    @Test
    public void testGetPercentage() throws Exception {
        //when
        float result = LinearEaseFunction.getInstance().getPercentage(1, 2);

        //then
        assertEquals(1f/2f, result);
    }
}