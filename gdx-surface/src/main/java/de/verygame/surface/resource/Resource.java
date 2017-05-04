package de.verygame.surface.resource;

/**
 * @author Rico Schrage
 *
 * Describes what an resource represationtation have to provide for the resource-handler.
 * <br>
 * In general this interface will be implemented by enums.
 */
public interface Resource extends ResourceDescriptor {

    /**
     * @return name of the file
     */
    String getFileName();

    /**
     * @return path to the file
     */
    String getFilePath();

    /**
     * @return type of the resource
     */
    ResourceType getType();

}
