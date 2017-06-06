package de.verygame.surface.resource;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.I18NBundleLoader;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;
import de.verygame.surface.event.Event;
import de.verygame.surface.event.EventHandler;
import de.verygame.surface.resource.loader.StringLoader;
import de.verygame.surface.util.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author Rico Schrage
 *
 * Handles all types of resources (defined here {@link de.verygame.surface.resource.ResourceType}). Provides many convienent methods
 * to obtain resource objects.
 */
public class ResourceHandler extends EventHandler implements Disposable {

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
     * Cache for anonymous fonts.
     */
    private final Map<Resource, IntMap<BitmapFont>> anonFontCache;

    /**
     * Stores number of references of a specific font
     */
    private final Map<BitmapFont, Integer> anonFontReferenceCounter;

    /**
     * Creates a resource handler.
     */
    public ResourceHandler() {
        this.pathMap = new EnumMap<>(ResourceType.class);
        this.regionMap = new HashMap<>();
        this.fontMap = new HashMap<>();
        this.anonFontCache = new HashMap<>();
        this.anonFontReferenceCounter = new HashMap<>();

        final FileHandleResolver resolver = new InternalFileHandleResolver();
        this.assetManager = new AssetManager(resolver);
        this.initAssetManager(resolver);
        this.initMap();
    }

    /**
     * Initializes the path map.
     */
    private void initMap() {
        de.verygame.surface.resource.ResourceType[] types = ResourceType.values();
        for (final de.verygame.surface.resource.ResourceType type : types) {
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
     * @param particleEffect have to be of type PARTICLE_EFFECT
     * @param atlas have to be of type ATLAS
     */
    public void loadParticleEffect(Resource particleEffect, Resource atlas) {
        if (particleEffect.getType() != ResourceType.PARTICLE_EFFECT) {
            throw new IllegalArgumentException("You are only allowed to load particle-effects with this method!");
        }
        String path = FileUtils.toPath(particleEffect.getType().toString(), particleEffect.getFilePath());
        String pathToAtlas = FileUtils.toPath(atlas.getType().toString(), atlas.getFilePath());
        ParticleEffectLoader.ParticleEffectParameter para = new ParticleEffectLoader.ParticleEffectParameter();
        para.atlasFile = pathToAtlas;
        assetManager.load(path, ParticleEffect.class, para);
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
        if (pathMap.get(de.verygame.surface.resource.ResourceType.LANG).size() == 1) {
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
     * Create a font with the help of font parameters. This will map the given resource unit to the generated font.
     * If you generate a new font with the same resource unit, the old one will get disposed.
     *
     * @param freeTypeFont unit the created font should get mapped to
     * @param parameter {@link FreeTypeFontGenerator.FreeTypeFontParameter}
     * @return created font
     */
    public BitmapFont createFont(ResourceUnit freeTypeFont, FreeTypeFontGenerator.FreeTypeFontParameter parameter) {
        if (freeTypeFont.getUnitType() == ResourceUnitType.BITMAP_FONT) {
            if (fontMap.containsKey(freeTypeFont)) {
                fontMap.get(freeTypeFont).dispose();
            }
            FreeTypeFontGenerator font = get(freeTypeFont.getParent(), FreeTypeFontGenerator.class);
            BitmapFont bFont = font.generateFont(parameter);
            fontMap.put(freeTypeFont, bFont);
            return bFont;
        }
        throw new IllegalArgumentException("The resource have to be a font.");
    }

    /**
     * Increase the reference count of the given cached font or does nothing if the given font is not cached.
     *
     * @param font cached font, created by {@link #createCachedFont(Resource, FreeTypeFontGenerator.FreeTypeFontParameter)}
     */
    public void increaseCachedFontReferenceCount(BitmapFont font) {
        if (!anonFontReferenceCounter.containsKey(font)) {
            return;
        }
        int references = anonFontReferenceCounter.get(font);
        anonFontReferenceCounter.put(font, references + 1);
    }

    /**
     * Destroy a cached font. It might happen that the font won't get destroyed if there are
     * more references to the font.
     * You can just use the method with fonts generated with {@link #createCachedFont(Resource, FreeTypeFontGenerator.FreeTypeFontParameter)}.
     *
     * @param font font generated by createCachedFont
     * @return false if the given font is not cached
     */
    public boolean destroyCachedFont(BitmapFont font) {
        if (!anonFontReferenceCounter.containsKey(font)) {
            return false;
        }
        int references = anonFontReferenceCounter.get(font);
        if (references == 1) {
            anonFontReferenceCounter.remove(font);
            for (Map.Entry<Resource, IntMap<BitmapFont>> entry : anonFontCache.entrySet()) {
                int key = entry.getValue().findKey(font, true, -1);
                if (key != -1) {
                    entry.getValue().remove(key);
                }
            }
            font.dispose();
        }
        else {
            anonFontReferenceCounter.put(font, references - 1);
        }
        return true;
    }

    /**
     * Create or simply get a cached font. Please always use this method to obtain a reference to
     * the a cached font, otherwise it might end up in a native exception.
     *
     * @param freeTypeFont font generator resource
     * @param parameter parameter to use for generation
     * @return font
     */
    public BitmapFont createCachedFont(Resource freeTypeFont, FreeTypeFontGenerator.FreeTypeFontParameter parameter) {
        if (freeTypeFont.getType() == ResourceType.FONT) {
            if (!anonFontCache.containsKey(freeTypeFont)) {
                anonFontCache.put(freeTypeFont, new IntMap<BitmapFont>());
            }
            IntMap<BitmapFont> resMap = anonFontCache.get(freeTypeFont);
            if (resMap.containsKey(parameter.size)) {
                BitmapFont font = resMap.get(parameter.size);
                anonFontReferenceCounter.put(font, anonFontReferenceCounter.get(font) + 1);
                return resMap.get(parameter.size);
            }
            BitmapFont font = get(freeTypeFont, FreeTypeFontGenerator.class).generateFont(parameter);
            anonFontReferenceCounter.put(font, 1);
            resMap.put(parameter.size, font);
            return font;
        }
        throw new IllegalArgumentException("The resource have to be a font generator!");
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
        if (res.getType() != ResourceType.SKIN || atlas.getType() != ResourceType.TEX_ATLAS) {
            throw new IllegalArgumentException("Wrong type!");
        }
        String atlasPath = FileUtils.toPath(atlas.getType().getRoot(), atlas.getFilePath());
        assetManager.load(FileUtils.toPath(res.getType().toString(), res.getFilePath()),
                Skin.class, new SkinLoader.SkinParameter(atlasPath, oMap));
        pathMap.get(atlas.getType()).add(atlasPath);
    }


    /**
     * Scales all ninpatches of the given skin by <code>scale</code>.
     * @param skinRes skin
     * @param scale scale-factor
     */
    public void scaleNinePatchesIn(Resource skinRes, float scale) {
        Skin ta = get(skinRes, Skin.class);
        ObjectMap<String, NinePatch> patches = ta.getAll(NinePatch.class);
        for (ObjectMap.Entry<String, NinePatch> entry : patches) {
            entry.value.scale(scale, scale);
        }
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
     * @return progress of loading resources in percent (0 to 1)
     */
    public float getProgress() {
        return assetManager.getProgress();
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
