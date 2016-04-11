package de.verygame.square.core.resource;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.I18NBundleLoader;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.ObjectMap;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.verygame.square.core.event.Event;
import de.verygame.square.core.event.EventEmitter;
import de.verygame.square.core.resource.loader.StringLoader;
import de.verygame.square.util.FileUtils;

/**
 * @author Rico Schrage
 *
 * Handles all types of resources (defined here {@link de.verygame.square.core.resource.ResourceType}). Provides many convienent methods
 * to obtain resource objects.
 */
public class ResourceHandler extends EventEmitter implements Disposable {

    /**
     * Used to load and store the assets
     */
    private final AssetManager assetManager;

    /**
     * Contains all path to specific resource types.
     */
    private final Map<ResourceType, List<String>> pathMap;

    /**
     * Maps resource units to available regions.
     */
    private final Map<ResourceUnit, TextureRegion> regionMap;

    /**
     * Maps resource units to available bitmap fonts.
     */
    private final Map<ResourceUnit, BitmapFont> fontMap;

    /**
     * Creates a resource handler.
     */
    public ResourceHandler() {
        this.pathMap = new EnumMap<>(ResourceType.class);
        this.regionMap = new HashMap<>();
        this.fontMap = new HashMap<>();

        final FileHandleResolver resolver = new InternalFileHandleResolver();
        this.assetManager = new AssetManager(resolver);
        this.initAssetManager(resolver);
        this.initMap();
    }

    /**
     * Initializes the path map.
     */
    private void initMap() {
        de.verygame.square.core.resource.ResourceType[] types = ResourceType.values();
        for (final de.verygame.square.core.resource.ResourceType type : types) {
            pathMap.put(type, new ArrayList<String>());
        }
    }

    /**
     * Initializes the asset manager.
     *
     * @param resolver Resolver of the asset manager
     */
    private void initAssetManager(FileHandleResolver resolver) {
        assetManager.setLoader(String.class, new StringLoader(resolver));
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
    }

    /**
     * Load given resources.
     *
     * @param resources resources to be loaded
     */
    public void loadResource(Resource... resources) {
        for (Resource res : resources) {
            String path = FileUtils.toPath(res.getType().toString(), res.getFilePath());
            assetManager.load(path, res.getType().getTarget());
            pathMap.get(res.getType()).add(path);
        }
    }

    /**
     * Load given resources except the defined exception. (Made for the use of Enum.values()).
     *
     * @param resources resources to be loaded
     * @param excludeResources exceptions
     */
    public void loadResourceExcept(Resource[] resources, Resource... excludeResources) {
        for (Resource res : resources) {
            boolean skip = false;
            for (Resource ex : excludeResources) {
                if (ex == res) {
                    skip = true;
                }
            }
            if (skip) {
                continue;
            }
            String path = FileUtils.toPath(res.getType().toString(), res.getFilePath());
            assetManager.load(path, res.getType().getTarget());
            pathMap.get(res.getType()).add(path);
        }
    }

    /**
     * Add a whole folder, which contains resources of the given type.
     *
     * @param resourceType type of the resources in the folder
     * @param pathToFile path to the folder
     */
    public void addResource(ResourceType resourceType, String pathToFile) {
        pathMap.get(resourceType).add(pathToFile);
        assetManager.load(pathToFile, resourceType.getTarget());
    }

    /**
     * Load available language. Resource handler emits an event after calling this method successfully.
     *
     * @param lang new language
     */
    public void loadLanguage(Locale lang) {
        if (pathMap.get(de.verygame.square.core.resource.ResourceType.LANG).size() == 1) {
            String langPath = pathMap.get(ResourceType.LANG).get(0);
            assetManager.unload(langPath);
            assetManager.load(langPath, I18NBundle.class, new I18NBundleLoader.I18NBundleParameter(lang));
            emitEvent(Event.OPTION_CHANGED);
        }
        else {
            throw new IllegalStateException("You have to load exactly one language bundle first!");
        }
    }

    /**
     * Retrieves string mapped to the given id. String will obtained via current language bundle. To change the language
     * call {@link #loadLanguage(Locale)}.
     *
     * @param stringId id of the string
     * @return mapped string
     */
    public String getString(String stringId) {
        if (pathMap.get(ResourceType.LANG).size() == 1) {
            return assetManager.get(pathMap.get(ResourceType.LANG).get(0), I18NBundle.class).get(stringId);
        }
        else {
            throw new IllegalStateException("You have to load exactly one language bundle first!");
        }
    }

    /**
     * Returns a whole xml file as string.
     *
     * @param xmlName name of the xml file
     * @return content of the file
     */
    public String getXML(String xmlName) {
        final List<String> paths = pathMap.get(ResourceType.XML);
        for (final String path : paths) {
            String[] splitted = path.split("/");
            if (splitted[splitted.length-1].equals(xmlName)) {
                return assetManager.get(path, String.class);
            }
        }
        return null;
    }

    /**
     * Returns region identified by the given resource unit.
     *
     * @param region region mapped to the given unit
     * @return region or null if the region does not exist.
     */
    public TextureRegion getRegion(ResourceUnit region) {
        if (regionMap.containsKey(region)) {
            return regionMap.get(region);
        }
        if (region.getUnitType() != ResourceUnitType.TEXTURE_REGION) {
            throw new IllegalArgumentException("The given unit have to identify a texture region!");
        }
        if (pathMap.containsKey(region.getParent().getType())) {
            List<String> paths = pathMap.get(region.getParent().getType());
            for (int i = 0; i < paths.size(); ++i) {
                String path = paths.get(i);

                TextureAtlas atlas = assetManager.get(path, TextureAtlas.class);
                TextureRegion tex = atlas.findRegion(region.getIdentifier());
                if (tex != null) {
                    regionMap.put(region, tex);
                    return tex;
                }
            }
        }
        return null;
    }

    /**
     * Returns loaded xml as string identified by a {@link Resource}.
     *
     * @param xmlResource identifier
     * @return content of the file as string
     */
    public String getXML(Resource xmlResource) {
        if (xmlResource.getType() != ResourceType.XML) {
            throw new IllegalArgumentException("Wrong type!");
        }
        return get(xmlResource, String.class);
    }

    public InputStream getXMLAsStream(Resource xmlResource) {
        return new ByteArrayInputStream(getXML(xmlResource).getBytes(Charset.forName("UTF-8")));
    }

    /**
     * Create a font with the help of font parameters.
     *
     * @param freeTypeFont unit the created font should get mapped to
     * @param parameter {@link FreeTypeFontGenerator.FreeTypeFontParameter}
     * @return created font
     */
    public BitmapFont createFont(ResourceUnit freeTypeFont, FreeTypeFontGenerator.FreeTypeFontParameter parameter) {
        if (freeTypeFont.getUnitType() == ResourceUnitType.BITMAP_FONT) {
            FreeTypeFontGenerator font = get(freeTypeFont.getParent(), FreeTypeFontGenerator.class);
            BitmapFont bFont = font.generateFont(parameter);
            fontMap.put(freeTypeFont, bFont);
            return bFont;
        }
        throw new IllegalArgumentException("The resource have to be a font.");
    }

    /**
     * Returns font mapped to the given resource unit.
     *
     * @param fontUnit identifier
     * @return retrieved font
     */
    public BitmapFont getFont(ResourceUnit fontUnit) {
        if (fontMap.containsKey(fontUnit)) {
            return fontMap.get(fontUnit);
        }
        throw new IllegalStateException("You have to create the fontUnit first!");
    }

    /**
     * Loads akin with the specified fontUnit as attached object.
     *
     * @param res resource
     * @param atlas atlas of the skin
     * @param fontUnit font unit
     */
    public void loadSkin(Resource res, Resource atlas, ResourceUnit fontUnit) {
        ObjectMap<String, Object> oMap = new ObjectMap<>();
        oMap.put("default-font", getFont(fontUnit));
        loadSkin(res, atlas, oMap);
    }

    /**
     * Loads a skin with the specified oMap
     *
     * @param res resources
     * @param atlas atlas of the skin
     * @param oMap map
     */
    public void loadSkin(Resource res, Resource atlas, ObjectMap<String, Object> oMap) {
        if (res.getType() != de.verygame.square.core.resource.ResourceType.SKIN || atlas.getType() != de.verygame.square.core.resource.ResourceType.TEX_ATLAS) {
            throw new IllegalArgumentException("Wrong type!");
        }
        String atlasPath = FileUtils.toPath(atlas.getType().getRoot(), atlas.getFilePath());
        assetManager.load(FileUtils.toPath(res.getType().toString(), res.getFilePath()),
                Skin.class, new SkinLoader.SkinParameter(atlasPath ,oMap));
        pathMap.get(atlas.getType()).add(atlasPath);
    }

    /**
     * Generic method to retrieve a previously loaded resource.
     *
     * @param res resource identifier
     * @param type type of the resource
     * @param <T> for generic usage
     * @return resource or null
     */
    public <T> T get(Resource res, Class<T> type) {
        return assetManager.get(FileUtils.toPath(res.getType().toString(), res.getFilePath()), type);
    }

    /**
     * Generic method to retrieve a previously loaded resource.
     *
     * @param res path to the resource
     * @param type type of the resource
     * @param <T> for generic usage
     * @return resource or null
     */
    public <T> T get(String res, Class<T> type) {
        return assetManager.get(res, type);
    }

    /**
     * Makes the resource handler update his internal state.
     *
     * @return true when the handler has finished
     */
    public boolean update() {
        return assetManager.update();
    }

    /**
     * Waits for all loaded assets.
     */
    public void waitForAssets() {
        assetManager.finishLoading();
    }

    /**
     * Waits for the given resource to be loaded.
     *
     * @param resource resource to wait for
     */
    public void waitFor(Resource resource) {
        assetManager.finishLoadingAsset(FileUtils.toPath(resource.getType().toString(), resource.getFilePath()));
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
