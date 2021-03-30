package com.xafarr;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class ParseRequestConfirmation<T> {
    private final Parser<T> parser;
    private final Filter<T> filter;

    public ParseRequestConfirmation(Parser<T> parser, Filter<T> filter) {
        this.parser = parser;
        this.filter = filter;
    }

    public List<T> parse(List<Path> files) {
        List<T> eventList = files.parallelStream()
                .map(this.parser::parse)
                .collect(Collectors.toList());
        return this.filter.filter(eventList);
    }
}
