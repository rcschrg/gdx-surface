package de.verygame.surface.resource.loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.verygame.surface.util.test.LibGdxTest;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Rico Schrage
 */
@RunWith(MockitoJUnitRunner.class)
public class StringLoaderTest extends LibGdxTest {

    @Mock
    private FileHandleResolver resolver;
    @Mock
    private AssetManager assetManager;

    private de.verygame.surface.resource.loader.StringLoader stringLoader;

    @Before
    public void prepare() {
        stringLoader = new de.verygame.surface.resource.loader.StringLoader(resolver);
    }

    @Test
    public void testLoad() throws Exception {
        //when
        stringLoader.loadAsync(assetManager, "background.xml", Gdx.files.internal("xml/background.xml"), null);
        String result = stringLoader.loadSync(null, null, null, null);

        //then
        assertTrue(result != null);
        assertEquals("ABC" ,result);
    }

    @Test
    public void testGetDependencies() throws Exception {
        //then
        assertTrue(stringLoader.getDependencies(null, null, null) == null);
    }
}