package de.verygame.square.core.scene2d;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import de.verygame.square.core.resource.Resource;
import de.verygame.square.core.resource.ResourceHandler;
import de.verygame.square.core.scene2d.glmenu.impl.element.ButtonBuilder;
import de.verygame.square.core.scene2d.glmenu.impl.element.ContainerActorBuilder;
import de.verygame.square.core.scene2d.glmenu.impl.element.GenericContainerBuilder;
import de.verygame.square.core.scene2d.glmenu.impl.element.GenericElementBuilder;
import de.verygame.square.core.scene2d.glmenu.impl.element.GroupBuilder;
import de.verygame.square.core.scene2d.glmenu.impl.element.LabelBuilder;
import de.verygame.square.core.scene2d.glmenu.impl.element.PanelBuilder;
import de.verygame.square.core.scene2d.glmenu.impl.element.SliderBuilder;
import de.verygame.square.core.scene2d.widget.Panel;
import de.verygame.square.core.scene2d.widget.Switch;
import de.verygame.xue.handler.BuilderMapping;
import de.verygame.xue.mapping.builder.GLMenuBuilder;

/**
 * @author Rico Schrage
 */
public class ElementMapping implements BuilderMapping<Actor> {
    private final ResourceHandler resourceHandler;
    private final Resource skinResource;

    /**
     * Constructs a mapping for scene2d.
     */
    public ElementMapping(ResourceHandler resourceHandler, Resource skinResource) {
        this.resourceHandler = resourceHandler;
        this.skinResource = skinResource;
    }

    @Override
    public GLMenuBuilder<Actor> createBuilder(String name) {
        Skin skin = resourceHandler.get(skinResource, Skin.class);
        switch (name) {
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
            case "label":
                return new LabelBuilder(skin, resourceHandler);
            case "image":
                return new GenericElementBuilder<>(new Image());
            case "progressBar":
                return new GenericElementBuilder<>(new ProgressBar(0, 100, 1, true ,skin));
            case "select":
                return new GenericElementBuilder<>(new SelectBox<String>(skin));
            case "slider":
                return new SliderBuilder(skin);
            case "textArea":
                return new GenericElementBuilder<>(new TextArea("", skin));
            case "textField":
                return new GenericElementBuilder<>(new TextField("", skin));

            default:
        }
        return null;
    }
}
