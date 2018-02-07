package org.rschrage.surface.resource;

/**
 * @author Rico Schrage
 */
public enum TestUnit implements ResourceUnit {
    TEST_BITMAP("idso", TestResource.TEST_FONT, ResourceUnitType.BITMAP_FONT),
    TEST_REGION("white", TestResource.TEST_ATLAS, ResourceUnitType.TEXTURE_REGION);

    private String id;
    private Resource parent;
    private ResourceUnitType type;

    private TestUnit(String id, Resource parent, ResourceUnitType type) {
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
