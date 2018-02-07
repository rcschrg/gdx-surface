package org.rschrage.surface.scene2d.glmenu;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.rschrage.surface.resource.ResourceHandler;
import org.rschrage.surface.scene2d.xue.Scene2DMapping;
import org.rschrage.surface.util.test.LibGdxTest;
import de.verygame.util.math.CoordinateType;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Rico Schrage
 */
@RunWith(MockitoJUnitRunner.class)
public class Scene2DMappingTest extends LibGdxTest {

    private Scene2DMapping mapping;

    @Mock
    private ResourceHandler resourceHandler;

    @Before
    public void setup() {
        mapping = new Scene2DMapping(mock(ResourceHandler.class));
        Gdx.app = mock(Application.class);
    }

    @Test
    public void testCalcRelativeValue() {
        //given
        Stage s = mock(Stage.class);
        when(s.getWidth()).thenReturn(100f);
        when(s.getHeight()).thenReturn(100f);
        final Actor testActor = mock(Actor.class);
        when(testActor.getStage()).thenReturn(s);
        final Actor testChild = new Actor();
        final Group testGroup = new Group();
        testGroup.setWidth(100f);
        testGroup.setHeight(10f);
        testGroup.addActor(testChild);

        //when
        final float relativeValueWidth = mapping.calcFromRelativeValue(testChild, 25f, CoordinateType.X);
        final float relativeValueHeight = mapping.calcFromRelativeValue(testChild, 25f, CoordinateType.Y);

        //then
        assertEquals("Calculated:" + relativeValueWidth + ", expected: 25", relativeValueWidth, 25f, 0.1f);
        assertEquals("Calculated:" + relativeValueHeight + ", expected: 2.5", relativeValueHeight, 2.5f, 0.1f);
    }

}