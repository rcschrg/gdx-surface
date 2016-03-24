package de.verygame.square.core.exception;

/**
 * @author Rico Schrage
 */
public class DependencyMissingException extends Exception {

    public DependencyMissingException(String dependencyKey, Class<?> dependencyType) {
        super(String.format("The dependency %s with the type %s is missing.", dependencyKey, dependencyType.getName()));
    }

}
