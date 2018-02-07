package org.rschrage.surface.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Rico Schrage
 */
public class FontUtilsTest {

    @Test
    public void testObtainParameterBuilder() throws Exception {
        //when
        FontUtils.FontParameterBuilder builder = FontUtils.obtainParameterBuilder();
        FreeTypeFontGenerator.FreeTypeFontParameter para = builder.borderColor(1, 1, 1, 1).size(1).borderWidth(1).character("").build();

        //then
        assertTrue(para.characters.equals(""));
        assertTrue(para.borderColor.equals(new Color(1, 1, 1, 1)));
        assertTrue(para.size == 1);
        assertTrue(para.borderWidth == 1);
    }
}