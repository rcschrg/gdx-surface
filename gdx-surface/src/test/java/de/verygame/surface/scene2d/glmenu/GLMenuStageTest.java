package de.verygame.surface.scene2d.glmenu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import de.verygame.surface.resource.ResourceHandler;
import de.verygame.surface.scene2d.xue.XueStage;
import de.verygame.surface.scene2d.xue.element.ElementTag;
import de.verygame.surface.util.test.LibGdxTest;
import de.verygame.xue.annotation.Bind;
import de.verygame.xue.mapping.TagMapping;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Rico Schrage
 */
public class GLMenuStageTest extends LibGdxTest {

    public static final String exampleXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<menu>\n" +
            "    <constants>\n" +
            "        <const name=\"ELEMENT_LAYER\" value=\"2\" />\n" +
            "        <const name=\"PANEL_LAYER\" value=\"1\" />\n" +
            "        <const name=\"BACKGROUND_LAYER\" value=\"0\" />\n" +
            "    </constants>\n" +
            "    <elements>\n" +
            "        <text name=\"title\" x=\"50r\" y=\"90r\" text=\"@string/app_name\" font=\"typeoneTitle\" zIndex=\"$ELEMENT_LAYER\" />\n" +
            "        <text name=\"subTitle\" x=\"30r\" y=\"84r\" text=\"@string/options_title\" font=\"typeoneSubTitle\" zIndex=\"$ELEMENT_LAYER\" />\n" +
            "\n" +
            "        <panel name=\"mainPanel\" x=\"50r\" y=\"45r\" width=\"78r\" height=\"60r\" color=\"$MAIN_PANEL_COLOR\" >\n" +
            "\n" +
            "            <text name=\"soundText\" x=\"75r\" y=\"85r\" text=\"@string/options_sound\" font=\"quicksand\" zIndex=\"$ELEMENT_LAYER\" />\n" +
            "            <switch name=\"soundSwitch\" x=\"25r\" y=\"85r\" id=\"0\" zIndex=\"$ELEMENT_LAYER\" />\n" +
            "\n" +
            "            <text name=\"musicText\" x=\"25r\" y=\"70r\" text=\"@string/options_music\" font=\"quicksand\" zIndex=\"$ELEMENT_LAYER\" />\n" +
            "            <switch name=\"musicSwitch\" x=\"75r\" y=\"70r\" id=\"1\" zIndex=\"$ELEMENT_LAYER\" />\n" +
            "\n" +
            "            <text name=\"lowText\" x=\"75r\" y=\"55r\" text=\"@string/options_low\" font=\"quicksand\" zIndex=\"$ELEMENT_LAYER\" />\n" +
            "            <switch name=\"lowSwitch\" x=\"25r\" y=\"55r\" id=\"2\" zIndex=\"$ELEMENT_LAYER\" />\n" +
            "\n" +
            "            <text name=\"sensitivText\" x=\"50r\" y=\"30r\" text=\"@string/options_sensitivity\" font=\"quicksand\" zIndex=\"$ELEMENT_LAYER\" />\n" +
            "            <toucharea name=\"sensitivBarTouchArea\" x=\"50r\" y=\"40r\" width=\"70r\" height=\"10r\">\n" +
            "                <seekbar name=\"sensitivBar\" x=\"50r\" y=\"50r\" width=\"100r\" height=\"10r\" id=\"3\" zIndex=\"$ELEMENT_LAYER\" />\n" +
            "            </toucharea>\n" +
            "\n" +
            "        </panel>\n" +
            "    </elements>\n" +
            "</menu>";

    private XueStage testSubject;

    @Bind
    private Object mainPanel = new Object();

    @Before
    public void prepareTest() throws Exception {
        testSubject = new XueStage(mock(SpriteBatch.class), new ByteArrayInputStream(exampleXML.getBytes()), mock(ResourceHandler.class));
        TagMapping mockTagMapping = mock(TagMapping.class);

        when(mockTagMapping.createTag(anyString())).thenReturn(new ElementTag(new Actor()));

        testSubject.addElementMapping(mockTagMapping);
    }

    @Test
    public void testGetElementByName() throws Exception {
        testSubject.load();

        assertEquals(12, testSubject.getElementSize());
    }

    @Test(expected = IllegalStateException.class)
    public void testLoadMenu() throws Exception {
        testSubject.getElementSize();
    }

    @Test
    public void testBind() throws Exception {
        testSubject.bind(this);
        testSubject.load();

        assertEquals(Actor.class, mainPanel.getClass());
    }

}