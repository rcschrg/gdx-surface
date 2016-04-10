package de.verygame.square.core.resource;

/**
 * @author Rico Schrage
 */
public enum CommonResourceUnit implements ResourceUnit {
    DEFAULT_BITMAP("default", de.verygame.square.core.resource.CommonResource.DEFAULT_FONT, ResourceUnitType.BITMAP_FONT),
    HUD_BITMAP("hudFont", de.verygame.square.core.resource.CommonResource.DEFAULT_FONT, ResourceUnitType.BITMAP_FONT);

    private String commonResourceId;
    private transient Resource commonResourceParent;
    private ResourceUnitType commonResourceType;

    private CommonResourceUnit(String id, Resource parent, ResourceUnitType type) {
        this.commonResourceId = id;
        this.commonResourceParent = parent;
        this.commonResourceType = type;
    }

    @Override
    public String getIdentifier() {
        return commonResourceId;
    }

    @Override
    public ResourceUnitType getUnitType() {
        return commonResourceType;
    }

    @Override
    public Resource getParent() {
        return commonResourceParent;
    }

}
