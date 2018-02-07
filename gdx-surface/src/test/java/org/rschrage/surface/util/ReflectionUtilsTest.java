package org.rschrage.surface.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.rschrage.surface.util.test.LibGdxTest;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Rico Schrage
 */
public class ReflectionUtilsTest extends LibGdxTest {

    @Test
    public void testObtainReflectionToolkit() throws Exception {
        assertTrue(ReflectionUtils.obtainReflectionToolkit() != null);
    }

    @Test
    public void testInvokeMethod() throws Exception {
        //given
        Object object = new Object();

        //when
        String toString = (String) ReflectionUtils.invokeMethod(object.getClass().getMethod("toString"), object);

        //then
        assertEquals(object.toString(), toString);
    }

    @Test(expected = ReflectionUtils.ReflectionException.class)
    public void testInvokeMethodException() throws Exception {
        //given
        Actor object = new Actor();
        Method stringMethod = object.getClass().getDeclaredMethod("setStage", Stage.class);

        //when
        String toString = (String) ReflectionUtils.invokeMethod(stringMethod, object);
    }

    @Test
    public void testRetrieveMethod() throws Exception {
        //when
        Method method  = ReflectionUtils.retrieveMethod(Object.class, "toString");

        //then
        assertEquals(Object.class.getMethod("toString"), method);
    }

    @Test(expected = ReflectionUtils.ReflectionException.class)
    public void testRetrieveMethodException() throws Exception {
        //when
        Method method  = ReflectionUtils.retrieveMethod(Object.class, "ToString");
    }

    @Test
    public void testRetrieveField() throws Exception {
        //when
        Field field  = ReflectionUtils.retrieveField(Color.class, "r");

        //then
        assertEquals(Color.class.getField("r"), field);
    }

    @Test(expected = ReflectionUtils.ReflectionException.class)
    public void testRetrieveFieldException() throws Exception {
        //when
        Field field  = ReflectionUtils.retrieveField(Color.class, "abc");
    }

    @Test
    public void testSearchForField() throws Exception {
        //given
        Object obj = new O();

        //when
        Field field = ReflectionUtils.searchForField(obj.getClass(), "a");

        //then
        assertEquals(obj.getClass().getDeclaredField("a"), field);
    }

    private static class O {
        float a;
        float b;
        float c;
    }
}