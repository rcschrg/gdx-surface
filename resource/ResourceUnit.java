package de.verygame.square.core.resource;

/**
 * @author Rico Schrage
 *
 * Describes what an abstract resource-unit have to provide for the resource-handler. The difference between resource units and resources is that
 * resource units are not real resources, mostly they are part of a resource or were created with their help.
 * <br>
 * In general this interface will be implemented by enums.
 */
public interface ResourceUnit extends ResourceDescriptor {

    /**
     * @return name of the file
     */
    String getIdentifier();

    /**
     * @return type of the unit
     */
    ResourceUnitType getUnitType();

    /**
     * @return parent
     */
    Resource getParent();
}
