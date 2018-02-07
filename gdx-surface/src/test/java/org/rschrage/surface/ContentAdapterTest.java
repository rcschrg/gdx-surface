package org.rschrage.surface;

import org.junit.Test;
import org.rschrage.surface.screen.base.ContentAdapter;

/**
 * @author Rico Schrage
 */
public class ContentAdapterTest {

    private ContentAdapter contentAdapter = new ContentAdapter();

    @Test
    public void testOnBind() throws Exception {
        contentAdapter.onBind(null);
    }

    @Test
    public void testOnActivate() throws Exception {
        contentAdapter.onActivate(null, null);
    }

    @Test
    public void testOnDeactivate() throws Exception {
        contentAdapter.onDeactivate(null, null);
    }

    @Test
    public void testOnUpdate() throws Exception {
        contentAdapter.onUpdate();
    }

    @Test
    public void testOnRender() throws Exception {
        contentAdapter.onRender();
    }

    @Test
    public void testOnPause() throws Exception {
        contentAdapter.onPause();
    }

    @Test
    public void testOnResume() throws Exception {
        contentAdapter.onResume();
    }

    @Test
    public void testOnResize() throws Exception {
        contentAdapter.onResize(1, 1);
    }

    @Test
    public void testDispose() throws Exception {
        contentAdapter.dispose();
    }
}