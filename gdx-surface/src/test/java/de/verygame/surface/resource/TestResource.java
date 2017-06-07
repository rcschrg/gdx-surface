package de.verygame.surface.resource;

/**
 * @author Rico Schrage
 */
public enum TestResource implements Resource {
    P_E("5", "5.p", ResourceType.PARTICLE_EFFECT),
    TEST_RESOURCE("test", "circle.png", ResourceType.TEX),
    TEST_FONT("font", "Quicksand-Light.ttf", ResourceType.FONT),
    TEST_LANG("lang", "strings", ResourceType.LANG),
    TEST_XML("xml", "background.xml", ResourceType.XML),
    TEST_ATLAS("atlas", "ui.atlas", ResourceType.TEX_ATLAS);

    private String name;
    private String path;
    private de.verygame.surface.resource.ResourceType type;

    private TestResource(String name, String path, ResourceType type) {
        this.name = name;
        this.path = path;
        this.type = type;
    }

    @Override
    public String getFileName() {
        return name;
    }

    @Override
    public String getFilePath() {
        return path;
    }

    @Override
    public ResourceType getType() {
        return type;
    }
}
