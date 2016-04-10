package de.verygame.square.core.resource.loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author Rico Schrage
 *
 * Loads a resource as string.
 */
public class StringLoader extends AsynchronousAssetLoader<String, StringLoader.StringParameter> {

    /**
     * Result of the asynchronous task.
     */
    private String result;

    /**
     * Constructs loader with the given resolver.
     *
     * @param resolver resolver, most likely obtained via an asset manager.
     */
    public StringLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, StringParameter parameter) {
        try {
            StringParameter para;
            if (parameter == null) {
                para = new StringParameter();
            }
            else {
                para = parameter;
            }
            final BufferedReader bufferedReader = file.reader(para.bufferSize, para.charset);
            StringBuilder builder = new StringBuilder();
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                builder.append(line);
            }
            result = builder.toString();
        }
        catch (IOException e) {
            Gdx.app.error("IOError", "A error occurred caused while reading:" + fileName, e);
        }
    }

    @Override
    public String loadSync(AssetManager manager, String fileName, FileHandle file, StringParameter parameter) {
        return result;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, StringParameter parameter) {
        return null;
    }

    /**
     * Parameter of the resource. Contains information about the buffer size and the charset.
     */
    public static class StringParameter extends AssetLoaderParameters<String> {
        /** Standard buffer size */
        private static final int STD_BUFFER = 1024;
        /** Standard charset */
        private static final String STD_CHARSET = "UTF-8";

        /** Charset of the text-file */
        public String charset = STD_CHARSET;
        /** Buffer size */
        public int bufferSize = STD_BUFFER;
    }

}
