package com.xafarr;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XPathXmlEventParserTest {

    private XPathXmlEventParser xPathXmlEventParser;
    private Path path;
    private Path invalid_path;
    private Path non_event_path;

    @Before
    public void setup() throws URISyntaxException {
        xPathXmlEventParser = new XPathXmlEventParser();
        ClassLoader classLoader = getClass().getClassLoader();
        path = Paths.get(classLoader.getResource("event.xml").toURI());
        invalid_path = Paths.get(classLoader.getResource("invalid_event.xml").toURI());
        non_event_path = Paths.get(classLoader.getResource("non_event.xml").toURI());
    }

    @Test
    public void parseDocument_whenValid_returnEvent() throws ParserConfigurationException, IOException, SAXException {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(path.toFile());
        Event event = xPathXmlEventParser.parseDocument(document);
        assertThat(event).isNotNull();
    }

    @Test
    public void parse_whenValid_returnEvent() {
        Event event = xPathXmlEventParser.parse(path);
        assertThat(event).isNotNull();
    }

    @Test
    public void parse_whenInvalid_throwsException() {
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> xPathXmlEventParser.parse(invalid_path))
                .withMessage("Invalid xml document");
    }

    @Test
    public void parseDocument_whenInvalid_throwsException() throws ParserConfigurationException, IOException, SAXException {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(non_event_path.toFile());
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> xPathXmlEventParser.parseDocument(document))
                .withMessage("Invalid xpath result");
    }
}