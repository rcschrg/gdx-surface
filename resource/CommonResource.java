package de.verygame.square.core.resource;

/**
 * @author Rico Schrage
 */
public enum CommonResource implements Resource {
    DEFAULT_FONT("default-font", "Quicksand-Regular.ttf", ResourceType.FONT);

    private String resourceName;
    private String path;
    private ResourceType resourceType;

    CommonResource(String name, String filePath, ResourceType type) {
        this.resourceName = name;
        this.path = filePath;
        this.resourceType = type;
    }

    @Override
    public String getFileName() {
        return resourceName;
    }

    @Override
    public String getFilePath() {
        return path;
    }

    @Override
    public ResourceType getType() {
        return resourceType;
    }
}
