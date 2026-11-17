package com.lev2.helper;

import com.lev2.common.exception.NotFoundException;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ReflectionBuilder {
    public static<T> List<String> getAllPropertyNames(Class<T> entityClass) {
        return getAllProperties(entityClass).stream().map(x->x.getName()).toList();
    }

    public static<T> List<Field>  getAllProperties(Class<T> entityClass) {
        Class<?> currentClass = entityClass;
        List<Field> fields = new ArrayList<>();
        while (currentClass != null && currentClass != Object.class) {
            fields.addAll(Arrays.stream(currentClass.getDeclaredFields()).toList());
            currentClass=currentClass.getSuperclass();
        }
        return fields;
    }

    public static <T> Object getPropertyValue(Field field , T item){
        try {
            field.setAccessible(true);
            return field.get(item);
        }catch (IllegalAccessException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }
    public static <T> void getPropertyValue(Field field , T item, Object value){
        try {
            field.setAccessible(true);
            field.set(item , value);
        }catch (IllegalAccessException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static <T> Field getPrimaryProperty(Class<T> entityClass) throws NotFoundException{
        Class<?> currentClass = entityClass;
        Optional<Field> primaryProperty = Optional.empty();
        while (currentClass != null){
            primaryProperty = Arrays.stream(
                    currentClass.getDeclaredFields())
                    .filter(f->isPrimaryKey(f))
                    .findFirst();
            if(primaryProperty.isPresent())
                return primaryProperty.get();

            currentClass = currentClass.getSuperclass();

        }

        throw new NotFoundException("Could not found primary key");
    }

    public static boolean isPrimaryKey(Field field) {
        return field.isAnnotationPresent(Id.class)
                || field.isAnnotationPresent(EmbeddedId.class);
    }
    public static <T> boolean hasProperty(Class<T> entityClass, String attributeName) {

        return getPropertyName(entityClass , attributeName) != null;
    }
    public static <T> String getPropertyName(Class<T> entityClass, String attributeName) {

        Class<?> currentClass = entityClass;

        while (currentClass != null && currentClass != Object.class) {

            Optional<Field> property = Arrays.stream(currentClass.getDeclaredFields())
                    .filter(field -> field.getName().equalsIgnoreCase(attributeName))
                    .findFirst();

            if (property.isPresent()) {
                return property.get().getName();
            }

            currentClass = currentClass.getSuperclass();
        }

        return null;
    }

}
