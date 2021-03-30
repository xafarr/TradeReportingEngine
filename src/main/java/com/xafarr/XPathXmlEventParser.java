package com.xafarr;

import static javax.xml.xpath.XPathConstants.NODESET;

import java.io.IOException;
import java.nio.file.Path;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XPathXmlEventParser implements XPathXmlParser<Event> {
    private static final String BUYER = "buyer";
    private static final String AMOUNT = "amount";
    private static final String ATTRIBUTE_NAME = "href";
    private static final String BUYER_SELLER_EXPRESSION = "//buyerPartyReference | //sellerPartyReference";
    private static final String AMOUNT_CURRENCY_EXPRESSION = "//paymentAmount/amount | //paymentAmount/currency";

    @Override
    public Event parseDocument(Document document) {
        try {
            Event event = new Event();
            NodeList buyerSellerNodelist = (NodeList) getXPathInstance()
                    .compile(BUYER_SELLER_EXPRESSION)
                    .evaluate(document, NODESET);
            parseBuyerSeller(buyerSellerNodelist, event);
            NodeList amountCurrencyNodelist = (NodeList) getXPathInstance()
                    .compile(AMOUNT_CURRENCY_EXPRESSION)
                    .evaluate(document, NODESET);
            parseAmountCurrency(amountCurrencyNodelist, event);
            return event;
        } catch (XPathExpressionException e) {
            throw new RuntimeException("XPath expression failure", e);
        }
    }

    private void parseAmountCurrency(NodeList amountCurrencyNodelist, Event event) {
        validateNodelist(amountCurrencyNodelist);
        if (amountCurrencyNodelist.item(0).getNodeName().contains(AMOUNT)) {
            event.setAmount(Double.parseDouble(amountCurrencyNodelist.item(0).getTextContent()));
            event.setCurrency(amountCurrencyNodelist.item(1).getTextContent());
        } else {
            event.setAmount(Double.parseDouble(amountCurrencyNodelist.item(1).getTextContent()));
            event.setCurrency(amountCurrencyNodelist.item(0).getTextContent());
        }
    }

    private void validateNodelist(NodeList nodeList) {
        if (nodeList.getLength() != 2) {
            throw new RuntimeException("Invalid xpath result");
        }
    }

    private void parseBuyerSeller(NodeList buyerSellerNodelist, Event event) {
        validateNodelist(buyerSellerNodelist);
        if (buyerSellerNodelist.item(0).getNodeName().contains(BUYER)) {
            event.setBuyer(buyerSellerNodelist.item(0).getAttributes().getNamedItem(ATTRIBUTE_NAME).getTextContent());
            event.setSeller(buyerSellerNodelist.item(1).getAttributes().getNamedItem(ATTRIBUTE_NAME).getTextContent());
        } else {
            event.setBuyer(buyerSellerNodelist.item(1).getTextContent());
            event.setSeller(buyerSellerNodelist.item(0).getTextContent());
        }
    }

    @Override
    public Event parse(Path file) {
        try {
            DocumentBuilder documentBuilder = getDocumentBuilder();
            Document document = documentBuilder.parse(file.toFile());
            return parseDocument(document);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new RuntimeException("Invalid xml document", e);
        }
    }

    private XPath getXPathInstance() {
        return XPathFactory.newInstance().newXPath();
    }

    private DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        return builderFactory.newDocumentBuilder();
    }
}
