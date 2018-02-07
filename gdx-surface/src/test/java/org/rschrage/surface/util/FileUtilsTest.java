package org.rschrage.surface.util;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * @author Rico Schrage
 */
public class FileUtilsTest {

    @Test
    public void testToPath() throws Exception {
        //when
        String testPath = FileUtils.toPath("first", "second", "third");

        //then
        assertEquals("first/second/third", testPath);
    }
}