package org.jboss.xavier.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConversionUtils {

    private ConversionUtils(){
        // TODO declared private constructor because this should contains just static methods
    }

    public static Integer toInteger(Object value) {
        Integer result;
        if (value == null) {
            result = null;
        } else if (value instanceof String) {
            result = Integer.parseInt((String) value);
        } else if (value instanceof Integer) {
            result = (Integer) value;
        } else {
            throw new IllegalStateException("Value can not convert to Integer");
        }
        return result;
    }

    public static Boolean toBoolean(Object value) {
        Boolean result;
        if (value == null) {
            result = null;
        } else if (value instanceof String) {
            result = Boolean.parseBoolean((String) value);
        } else if (value instanceof Boolean) {
            result = (Boolean) value;
        } else {
            throw new IllegalStateException("Value can not convert to Boolean");
        }
        return result;
    }

    public static List<String> toList(Object value) {
        List<String> result = new ArrayList<>();
        if (value == null) {
            result = null;
        } else if (value instanceof String) {
            result.add((String) value);
        } else if (value instanceof Collection) {
            Collection<String> a = (Collection<String>) value;
            result.addAll(a);
        } else {
            throw new IllegalStateException("Value must be String or List");
        }
        return result;
    }
}
