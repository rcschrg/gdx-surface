package de.verygame.square.core.scene2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import java.util.ArrayList;
import java.util.List;

import de.verygame.square.core.resource.Resource;
import de.verygame.square.core.resource.ResourceHandler;
import de.verygame.square.core.scene2d.glmenu.impl.element.ButtonBuilder;
import de.verygame.square.core.scene2d.glmenu.impl.element.ContainerActorBuilder;
import de.verygame.square.core.scene2d.glmenu.impl.element.GenericContainerBuilder;
import de.verygame.square.core.scene2d.glmenu.impl.element.GenericElementBuilder;
import de.verygame.square.core.scene2d.glmenu.impl.element.GroupBuilder;
import de.verygame.square.core.scene2d.glmenu.impl.element.PanelBuilder;
import de.verygame.square.core.scene2d.widget.Panel;
import de.verygame.square.core.scene2d.widget.Switch;
import de.verygame.square.util.glmenu.BuilderMapping;
import de.verygame.square.util.glmenu.Mappings;
import de.verygame.square.util.glmenu.element.ContainerBuilder;
import de.verygame.square.util.glmenu.element.ElementBuilder;
import de.verygame.square.util.glmenu.element.ObjectBuilder;

/**
 * Implementation of {@link Mappings} for scene 2d.
 *
 * @author Rico Schrage
 */
public class Scene2DMapping implements Mappings<Actor> {

    private final ResourceHandler resourceHandler;
    private final Resource skinResource;
    private final List<BuilderMapping<Actor>> extensionList;

    /**
     * Constructs a mapping for scene2d.
     */
    public Scene2DMapping(ResourceHandler resourceHandler, Resource skinResource) {
        this.resourceHandler = resourceHandler;
        this.skinResource = skinResource;
        this.extensionList = new ArrayList<>();
    }

    @Override
    public float calcFromRelativeValue(Actor target, float value, CoordinateType coordFlag) {
        if (coordFlag == CoordinateType.X) {
            if (target.getParent() == null) {
                return 0.01f * Gdx.graphics.getWidth() * value;
            }
            else {
                return 0.01f * target.getParent().getWidth() * value;
            }
        }
        else if (coordFlag == CoordinateType.Y) {
            if (target.getParent() == null) {
                return 0.01f * Gdx.graphics.getHeight() * value;
            }
            else {
                return 0.01f * target.getParent().getHeight() * value;
            }
        }
        return -1;
    }

    @Override
    public String getString(String key) {
        return resourceHandler.getString(key);
    }

    @Override
    public void addMappingExtension(BuilderMapping<Actor> extension) {
        this.extensionList.add(extension);
    }

    @Override
    public ObjectBuilder getObjectBuilderByTag(String tagIdentifier)  {
        for (BuilderMapping<Actor> extension : extensionList) {
            ObjectBuilder builder = extension.getObjectBuilderByTag(tagIdentifier);
            if (builder != null) {
                return builder;
            }
        }
        return null;
    }

    @Override
    public ElementBuilder<Actor> createElementBuilderByTag(String tagIdentifier) {
        Skin skin = resourceHandler.get(skinResource, Skin.class);
        switch (tagIdentifier) {
            case "label":
                return new de.verygame.square.core.scene2d.glmenu.impl.element.LabelBuilder(skin, resourceHandler);
            case "image":
                return new GenericElementBuilder<>(new Image());
            case "progressBar":
                return new GenericElementBuilder<>(new ProgressBar(0, 100, 1, true ,skin));
            case "select":
                return new GenericElementBuilder<>(new SelectBox<String>(skin));
            case "slider":
                return new GenericElementBuilder<>(new Slider(0, 100, 1, true, skin));
            case "textArea":
                return new GenericElementBuilder<>(new TextArea("", skin));
            case "textField":
                return new GenericElementBuilder<>(new TextField("", skin));
            default:
                for (BuilderMapping<Actor> extension : extensionList) {
                    ElementBuilder<Actor> builder = extension.createElementBuilderByTag(tagIdentifier);
                    if (builder != null) {
                        return builder;
                    }
                }
        }
        return null;
    }

    @Override
    public ContainerBuilder<Actor> createContainerBuilderByTag(String tagIdentifier) {
        Skin skin = resourceHandler.get(skinResource, Skin.class);
        switch (tagIdentifier) {
            case "group":
                return new GroupBuilder();
            case "button":
                return new ButtonBuilder(skin, resourceHandler);
            case "checkbox":
                return new GenericContainerBuilder<>(new CheckBox("", skin));
            case "dialog":
                return new GenericContainerBuilder<>(new Dialog("", skin));
            case "imageButton":
                return new GenericContainerBuilder<>(new ImageButton(skin));
            case "scrollPane":
                return new GenericContainerBuilder<>(new ScrollPane(null, skin));
            case "splitPane":
                return new GenericContainerBuilder<>(new SplitPane(null, null, true, skin));
            case "table":
                return new GenericContainerBuilder<>(new Table(skin));
            case "container":
                return new ContainerActorBuilder();
            case "panel":
                return new PanelBuilder(new Panel(), resourceHandler);
            case "switch":
                return new GenericContainerBuilder<>(new Switch(skin));
            default:
                for (BuilderMapping<Actor> extension : extensionList) {
                    ContainerBuilder<Actor> builder = extension.createContainerBuilderByTag(tagIdentifier);
                    if (builder != null) {
                        return builder;
                    }
                }
        }
        return null;
    }
}