package de.verygame.square.core.resource;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;

import de.verygame.square.util.FileUtils;

/**
 * @author Rico Schrage
 *
 * Each enum describes groups of resources.
 */
public enum ResourceType {
    TEX(FileUtils.toPath("tex"), Texture.class),
    TEX_ATLAS(FileUtils.toPath("atlas"), TextureAtlas.class),
    XML(FileUtils.toPath("xml"), String.class),
    LANG(FileUtils.toPath("lang"), I18NBundle.class),
    FONT(FileUtils.toPath("font"), FreeTypeFontGenerator.class),
    SKIN(FileUtils.toPath("skin"), Skin.class);

    /** Java representation of the resource */
    private Class<?> targetClass;
    /** Root path to the resource group */
    private String basePath;

    /**
     * Creates a resource type.
     *
     * @param resourceRootPath root path of the resource
     * @param targetClass representation of the loaded resource
     */
    private ResourceType(String resourceRootPath, Class<?> targetClass) {
        this.targetClass = targetClass;
        this.basePath = resourceRootPath;
    }

    /**
     * @return class the resource should be represented by
     */
    public Class<?> getTarget() {
        return targetClass;
    }

    /**
     * Define root path of the resource.
     *
     */
    public String getRoot() {
        return this.basePath;
    }

    /**
     * {@inheritDoc }
     *
     * @return root path of the resource
     */
    @Override
    public String toString() {
        return basePath;
    }
}