package com.javaops.webapp.storage.serialization;

import com.javaops.webapp.model.*;
import com.javaops.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XMLStreamSerializer implements SerializationStorage {
    private XmlParser xmlParser;

    public XMLStreamSerializer() {
        xmlParser = new XmlParser(Resume.class, Section.class, Company.class, CompanySection.class,
                ListSection.class, Period.class, TextSection.class);
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (Writer w = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(r, w);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (Reader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(r);
        }
    }
}
