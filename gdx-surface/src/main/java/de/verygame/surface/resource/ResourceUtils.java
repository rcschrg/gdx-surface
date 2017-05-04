package de.verygame.surface.resource;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.verygame.surface.util.ReflectionUtils;

/**
 * @author Rico Schrage
 *
 * Contains methods to handle resource descriptors.
 */
public class ResourceUtils {

    /**
     * Name of the static enum method <code>values()</code>.
     */
    private static final String ENUM_METHOD_NAME = "values";

    private static final List<Class<? extends Resource>> resourceClasses = new ArrayList<>();
    private static final List<Class<? extends ResourceUnit>> resourceUnitClasses = new ArrayList<>();

    private ResourceUtils() {
        //utility class
    }

    @SafeVarargs
    public static void addResourceImplementation(Class<? extends Resource>... resourceImplementations) {
        Collections.addAll(resourceClasses, resourceImplementations);
    }

    @SafeVarargs
    public static void addResourceUnitImplementation(Class<? extends ResourceUnit>... resourceUnitImplementations) {
        Collections.addAll(resourceUnitClasses, resourceUnitImplementations);
    }

    /**
     * Looks for all implementations of the specified class (have to be a {@link ResourceDescriptor}), checks if they are
     * enums and eventually retrieves all existing enums (respective {@link ResourceDescriptor}'s) and searches for the enum with the given name.
     * <br>
     * This will do nothing in implementations which are not enums.
     * <br>
     * Hint: If you have two enums with the same names in different enum implementations it's not possible to safely retrieve the right one. In this case this
     * method will return the first resource it finds.
     *
     * @param resourceName name of the enum (Resource)
     * @param yourClass class you want to look up
     * @param <T> your resource superclass
     * @return Resource with the specified name
     */
    @SuppressWarnings("unchecked")
    public static <T extends ResourceDescriptor> T lookUp(String resourceName, Class<T> yourClass) {
        List<Class<T>> resourceSet = (List<Class<T>>) (yourClass == Resource.class ? resourceClasses : resourceUnitClasses);
        for (Class<? extends T> resourceClass : resourceSet) {
            if (resourceClass.isEnum()) {
                Class<? extends Enum> enumResource = (Class<? extends Enum>) resourceClass;
                Method valuesMethod = ReflectionUtils.retrieveMethod(enumResource, ENUM_METHOD_NAME);
                Enum<?>[] enums = (Enum<?>[]) ReflectionUtils.invokeMethod(valuesMethod, null);
                if (enums == null) {
                    continue;
                }
                for (Enum<?> en : enums) {
                    if (en.name().equals(resourceName)) {
                        return (T) en;
                    }
                }
            }
        }
        return null;
    }

}
