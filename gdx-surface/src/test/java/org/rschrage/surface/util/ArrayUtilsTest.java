package org.rschrage.surface.util;

import com.badlogic.gdx.math.Vector2;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 23.03.2016.
 */
public class ArrayUtilsTest {

    @Test
    public void testCreateAndFill() throws Exception {

        float[] array = ArrayUtils.createAndFill(5, 1);

        float[] other = {1,1,1,1,1};

        assertArrayEquals(other, array, 0.001f);
    }

    @Test
    public void testBuildVertexArray() throws Exception {

        Vector2[] array = {new Vector2(0,0), new Vector2(0,5), new Vector2(5,0)};

        float[] result = {0,0,0,5,5,0};

        assertArrayEquals(result, ArrayUtils.buildVertexArray(array), 0.001f);

    }

}