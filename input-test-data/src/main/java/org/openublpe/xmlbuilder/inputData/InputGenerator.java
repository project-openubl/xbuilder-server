package org.openublpe.xmlbuilder.inputData;

import java.util.Optional;

public interface InputGenerator<T> {

    T getInput();

    default Optional<String> getSnapshot() {
        return Optional.empty();
    }

}
