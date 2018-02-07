package org.rschrage.surface.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 * @author Rico Schrage
 */
public class FontUtils {

    private static final FontParameterBuilder builder = new FontParameterBuilder();

    private FontUtils() {
        //utility class
    }

    public static FontParameterBuilder obtainParameterBuilder() {
        return builder;
    }

    public static class FontParameterBuilder {

        FreeTypeFontParameter para;

        public FontParameterBuilder() {
            this.para = new FreeTypeFontParameter();
        }

        public FontParameterBuilder size(int size) {
            this.para.size = size;
            return this;
        }

        public FontParameterBuilder borderColor(float r, float g, float b, float a) {
            return borderColor(new Color(r, g, b, a));
        }

        public FontParameterBuilder borderColor(Color color) {
            this.para.borderColor = color;
            return this;
        }

        public FontParameterBuilder borderWidth(float width) {
            this.para.borderWidth = width;
            return this;
        }

        public FontParameterBuilder character(String chars) {
            this.para.characters = chars;
            return this;
        }

        public FontParameterBuilder minFilter(Texture.TextureFilter filter) {
            this.para.minFilter = filter;
            return this;
        }

        public FontParameterBuilder magFilter(Texture.TextureFilter filter) {
            this.para.magFilter = filter;
            return this;
        }

        public FreeTypeFontParameter build() {
            FreeTypeFontParameter parameter = new FreeTypeFontParameter();
            parameter.magFilter = para.magFilter;
            parameter.minFilter = para.minFilter;
            parameter.characters = para.characters;
            parameter.borderWidth = para.borderWidth;
            parameter.borderColor = para.borderColor;
            parameter.size = para.size;
            return parameter;
        }

    }
}
