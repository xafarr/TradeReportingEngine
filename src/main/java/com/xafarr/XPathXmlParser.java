package com.xafarr;

import org.w3c.dom.Document;

public interface XPathXmlParser<T> extends Parser<T> {
    T parseDocument(Document document);
}
