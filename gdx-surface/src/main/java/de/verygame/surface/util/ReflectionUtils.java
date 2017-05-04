package de.verygame.surface.util;

import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Rico Schrage
 */
public class ReflectionUtils {

    private static final String BASE_PACKAGE = "de.verygame.surface";
    private static final Reflections REFLECTION_TOOLKIT = new Reflections(BASE_PACKAGE);

    private ReflectionUtils() {
        //utility class
    }

    public static Reflections obtainReflectionToolkit() {
        return REFLECTION_TOOLKIT;
    }

    public static Object invokeMethod(Method method, Object target, Object... parameter) {
        try {
            return method.invoke(target, parameter);
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            throw new ReflectionException(e.getMessage());
        }
    }

    public static Method retrieveMethod(Class<?> target, String name, Class<?>... parameter) {
        try {
            return target.getMethod(name, parameter);
        }
        catch (NoSuchMethodException e) {
            throw new ReflectionException(e.getMessage());
        }
    }

    public static Field retrieveField(Class<?> target, String name) {
        try {
            return target.getField(name);
        }
        catch (NoSuchFieldException e) {
            throw new ReflectionException(e.getMessage());
        }
    }

    public static Field searchForField(Class<?> type, String name) {
        List<Field> allFields = getAllFields(type);
        for (int i = 0; i < allFields.size(); ++i) {
            if (allFields.get(i).getName().equals(name)) {
                return allFields.get(i);
            }
        }
        throw new ReflectionException(name + " could not be found!");
    }

    public static List<Field> getAllFields(Class<?> type) {
        return getAllFields(new ArrayList<Field>(), type);
    }

    private static List<Field> getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        List<Field> newFields = fields;
        if (type.getSuperclass() != null) {
            newFields = getAllFields(fields, type.getSuperclass());
        }
        return newFields;
    }

    public static class ReflectionException extends RuntimeException {

        public ReflectionException(String messsage) {
            super(messsage);
        }

    }

}
