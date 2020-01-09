package org.openublpe.xmlbuilder.api.resources.utils;

public class ResourceUtils {

    private ResourceUtils() {
        // Just static methods
    }

    public static String getAttachmentFileName(String fileName) {
        return "attachment; filename=\"" + fileName + "\"";
    }

}
