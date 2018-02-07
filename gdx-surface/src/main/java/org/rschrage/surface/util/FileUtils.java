package org.rschrage.surface.util;

import com.badlogic.gdx.files.FileHandle;

import java.io.File;

/**
 * @author Rico Schrage
 *
 * Utility methods regarding {@link File} and {@link FileHandle}.
 */
public class FileUtils {

    /** AssetManager uses always this separator */
    public static final String SEPARATOR = "/";

    private FileUtils() {
        // utility class
    }

    /**
     * Convenience method to determine if a given path (have to exist!) is directory.
     *
     * @param path path as string
     * @return true if it's describes a directory.
     */
    public static boolean isDirectory(String path) {
        return new FileHandle(path).isDirectory();
    }

    /**
     * Provides an easy and efficient way to create a system independent path string.
     * <br>
     * Will only work for relative paths as absolute path are highly system dependent,
     * for absolute paths you should consider to use {@link File#getAbsolutePath()}.
     *
     * @param folders folders
     * @return system independent relative path using given folders
     */
    public static String toPath(String... folders) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < folders.length; ++i) {
            final String folder = folders[i];
            builder.append(folder);
            if (i != folders.length - 1) {
                builder.append(FileUtils.SEPARATOR);
            }
        }
        return builder.toString();
    }

}
