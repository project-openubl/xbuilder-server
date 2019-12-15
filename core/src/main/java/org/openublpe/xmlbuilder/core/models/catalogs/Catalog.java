package org.openublpe.xmlbuilder.core.models.catalogs;

import java.util.Optional;
import java.util.stream.Stream;

public interface Catalog {
    String getCode();

    /**
     * @return an instance of Catalog which is equal to ValueOf or contains the same code
     */
    static <T extends Catalog> Optional<T> valueOfCode(Class<T> enumType, String code) {
        return Stream.of(enumType.getEnumConstants())
                .filter(p -> p.toString().equalsIgnoreCase(code) || p.getCode().equals(code))
                .findFirst();
    }
}

