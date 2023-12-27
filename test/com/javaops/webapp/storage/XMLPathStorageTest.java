package com.javaops.webapp.storage;

import com.javaops.webapp.storage.serialization.XMLStreamSerializer;

public class XMLPathStorageTest extends AbstractStorageTest{
    public XMLPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new XMLStreamSerializer()));
    }
}
