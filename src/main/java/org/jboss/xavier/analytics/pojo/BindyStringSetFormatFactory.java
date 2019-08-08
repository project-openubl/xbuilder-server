package org.jboss.xavier.analytics.pojo;

import org.apache.camel.dataformat.bindy.Format;
import org.apache.camel.dataformat.bindy.FormattingOptions;
import org.apache.camel.dataformat.bindy.format.factories.AbstractFormatFactory;

import java.util.*;
import java.util.stream.Collectors;

public class BindyStringSetFormatFactory extends AbstractFormatFactory {

    private final collectionFormat collectionFormat = new collectionFormat();

    {
        supportedClasses.add(Set.class);
    }

    @Override
    public Format<?> build(FormattingOptions formattingOptions) {
        return collectionFormat;
    }

    private static class collectionFormat implements Format<Set<String>> {

        @Override
        public String format(Set<String> collection) {
            return collection.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining("/"));
        }

        public Set<String> parse(String string) {
            List<String> strings = Arrays.asList(string.split("/"));
            return new HashSet<>(strings);
        }

    }

}
