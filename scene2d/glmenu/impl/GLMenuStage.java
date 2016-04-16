package de.verygame.square.core.scene2d.glmenu.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import org.kxml2.io.KXmlParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import de.verygame.square.core.resource.Resource;
import de.verygame.square.core.resource.ResourceHandler;
import de.verygame.square.core.scene2d.Scene2DMapping;
import de.verygame.square.util.glmenu.BuilderMapping;
import de.verygame.square.util.glmenu.GLMenu;
import de.verygame.square.util.glmenu.GLMenuCore;
import de.verygame.square.util.glmenu.exception.AttributeUnknownException;
import de.verygame.square.util.glmenu.exception.GLMenuSyntaxException;
import de.verygame.square.util.glmenu.exception.TagUnknownException;

/**
 * Implementation of {@link GLMenu} for the scene2d package.
 *
 * @author Rico Schrage
 */
public class GLMenuStage extends Stage implements GLMenu<Actor> {

    /** Resource of the menu, all elements of the menu are described in this file */
    private final InputStream resource;

    /** Core of the menu, parses the given resource and creates the described elements. */
    private GLMenuCore<Actor> menuCore;

    /** Contains all elements, which have the attribute <code>name</code> */
    private Map<String, Actor> elementMap;

    /** Contains all constants */
    private Map<String, Object> constMap;

    /** Maps tags to builders */
    private Scene2DMapping mapping;

    /** see {@link #bind(Object)} */
    private Object bindTarget;

    /**
     * Constructs scene2d stage using a xml file, which describes the setup. This will use the standard implementation of ITagMapping and
     * IAttributeMapping.
     *
     * @param batch contains all strings in various languages
     * @param resourceFile xml file, which describes the setup of the menu
     * @param resourceHandler contains language bundles and skins.
     */
    public GLMenuStage(Batch batch, InputStream resourceFile, ResourceHandler resourceHandler, Resource skinResource) {
        super(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);

        this.resource = resourceFile;
        this.mapping = new Scene2DMapping(resourceHandler, skinResource);
        this.menuCore = new GLMenuCore<>(mapping);
    }

    /**
     * Sets the general mapping.
     *
     * @param mapping {@link de.verygame.square.util.glmenu.Mappings}
     */
    public void setMapping(Scene2DMapping mapping) {
        this.mapping = mapping;
        this.menuCore.setMappings(mapping);
    }

    /**
     * Get the number of elements. Must not be called before {@link #loadMenu()}.
     *
     * @return number of elements
     */
    public int getElementSize() {
        if (elementMap == null) {
            throw new IllegalStateException("You have to load the gl-menu-file first!");
        }

        return elementMap.size();
    }

    /**
     * Will be called directly before {@link GLMenuCore#load(XmlPullParser)}.
     */
    protected void preLoad() {
        //nothing to do
    }

    /**
     * Will be called directly after {@link GLMenuCore#load(XmlPullParser)}.
     */
    protected void postLoad() {
        Set<Map.Entry<String, Actor>> entries = elementMap.entrySet();
        for (final Map.Entry<String, Actor> entry : entries) {
            Actor currentElement = elementMap.get(entry.getKey());

            if (currentElement.getParent() != null) {
                continue;
            }

            addActor(currentElement);
        }

        if (bindTarget == null) {
            return;
        }
        for (final Field field : bindTarget.getClass().getDeclaredFields()) {
            for (final Map.Entry<String, Actor> entry : entries) {
                if (field.getName().equals(entry.getKey())) {
                    try {
                        field.setAccessible(true);
                        field.set(bindTarget, entry.getValue());
                    }
                    catch (IllegalAccessException e) {
                        Gdx.app.error("Reflection", "It was not possible to access: " + field, e);
                    }
                }
            }
        }
    }

    /**
     * The behaviour of {@link #draw()} has changed in this implementation. In this stage you have to
     * call {@link Batch#begin()} before and {@link Batch#end()} after calling this method.
     *
     * {@inheritDoc}
     */
    @Override
    public void draw() {
        Camera camera = getViewport().getCamera();
        camera.update();

        if (!getRoot().isVisible()) {
            return;
        }

        Batch batch = getBatch();
        if (batch != null) {
            getRoot().draw(batch, 1);
        }
    }

    @Override
    public Actor getElementByName(final String name) {
        if (elementMap == null) {
            throw new IllegalStateException("You have to load the gl-menu-file first!");
        }

        return elementMap.get(name);
    }

    @Override
    public void loadMenu() {
        try {
            this.preLoad();
            final KXmlParser parser = new KXmlParser();
            parser.setInput(resource, "UTF-8");
            this.menuCore.load(parser);
            this.elementMap = menuCore.getElementMap();
            this.constMap = menuCore.getConstMap();
            this.postLoad();
        }
        catch (XmlPullParserException | GLMenuSyntaxException e) {
            Gdx.app.debug("Parser", "Error occurred while parsing:" + resource + ".", e);
            Gdx.app.debug("Parser", e.getMessage(), e);
        }
        catch (IOException e) {
            Gdx.app.debug("IO", resource + " could not be opened!", e);
            Gdx.app.debug("IO", e.getMessage(), e);
        }
        catch (TagUnknownException | AttributeUnknownException  e) {
            Gdx.app.debug("GLMenu", e.getMessage(), e);
        }
    }

    @Override
    public void bind(Object bindTarget) {
        this.bindTarget = bindTarget;
    }

    @Override
    public void addMappingExtension(BuilderMapping<Actor> extension) {
        this.mapping.addMappingExtension(extension);
    }

    @Override
    public Object getConstByName(String name) {
        if (constMap == null) {
            throw new IllegalStateException("You have to load the gl-menu-file first!");
        }

        return constMap.get(name);
    }

}
