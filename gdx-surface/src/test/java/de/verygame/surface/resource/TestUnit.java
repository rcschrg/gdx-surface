package de.verygame.surface.resource;

/**
 * @author Rico Schrage
 */
public enum TestUnit implements de.verygame.surface.resource.ResourceUnit {
    TEST_BITMAP("idso", TestResource.TEST_FONT, ResourceUnitType.BITMAP_FONT),
    TEST_REGION("white", TestResource.TEST_ATLAS, ResourceUnitType.TEXTURE_REGION);

    private String id;
    private de.verygame.surface.resource.Resource parent;
    private de.verygame.surface.resource.ResourceUnitType type;

    private TestUnit(String id, de.verygame.surface.resource.Resource parent, de.verygame.surface.resource.ResourceUnitType type) {
        this.id = id;
        this.parent = parent;
        this.type = type;
    }

    @Override
    public String getIdentifier() {
        return id;
    }

    @Override
    public ResourceUnitType getUnitType() {
        return type;
    }

    @Override
    public Resource getParent() {
        return parent;
    }
}
