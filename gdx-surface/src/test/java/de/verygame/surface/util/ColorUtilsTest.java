package de.verygame.surface.util;

import com.badlogic.gdx.graphics.Color;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Rico Schrage
 */
public class ColorUtilsTest {

    @Test
    public void testFromHex() throws Exception {
        //when
        Color color = ColorUtils.fromHex("#ffffff");
        Color colorBlack = ColorUtils.fromHex("#000000");

        //then
        assertEquals(1f, color.r);
        assertEquals(1f, color.g);
        assertEquals(1f, color.b);

        assertEquals(0f, colorBlack.r);
        assertEquals(0f, colorBlack.g);
        assertEquals(0f, colorBlack.b);
    }
}