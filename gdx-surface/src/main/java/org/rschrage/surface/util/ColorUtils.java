package org.rschrage.surface.util;

import com.badlogic.gdx.graphics.Color;

/**
 * @author Rico Schrage
 */
public class ColorUtils {

    private static final int HEX_START_INDEX = 1;
    private static final int HEX_FIRST_DIVIDER = 3;
    private static final int HEX_SECOND_DIVIDER = 5;
    private static final int HEX_END_INDEX = 7;

    private static final int HEX_RADIX = 16;

    private static final float RGB_RANGE = 255f;

    private ColorUtils() {
        //utility class
    }

    public static Color fromHex(String hex) {
        if (hex.length() != HEX_END_INDEX || !hex.startsWith("#")) {
            throw new IllegalArgumentException("The argument have to a valid hex color code!");
        }
        int r = Integer.parseInt(hex.substring(HEX_START_INDEX, HEX_FIRST_DIVIDER), HEX_RADIX);
        int g = Integer.parseInt(hex.substring(HEX_FIRST_DIVIDER, HEX_SECOND_DIVIDER), HEX_RADIX);
        int b = Integer.parseInt(hex.substring(HEX_SECOND_DIVIDER, HEX_END_INDEX), HEX_RADIX);

        return new Color(r / RGB_RANGE, g / RGB_RANGE, b / RGB_RANGE, 1f);
    }

}
