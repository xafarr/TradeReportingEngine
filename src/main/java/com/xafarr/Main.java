package com.xafarr;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        URL url = ParseRequestConfirmation.class.getClassLoader().getResource("events");
        Path path = Paths.get(Objects.requireNonNull(url).toURI());
        List<Path> filesToParse = Files.list(path).collect(Collectors.toList());

        Parser<Event> parser = new XPathXmlEventParser();
        Filter<Event> filter = new EventFilter();
        ParseRequestConfirmation<Event> parseRequestConfirmation = new ParseRequestConfirmation<>(parser, filter);
        List<Event> events = parseRequestConfirmation.parse(filesToParse);
        EventToCsvWriter eventToCsvWriter = new EventToCsvWriter();
        eventToCsvWriter.writeEventsToCsv(events, Paths.get("events.csv"));
    }
}
