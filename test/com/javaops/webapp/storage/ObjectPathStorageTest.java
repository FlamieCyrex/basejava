package com.javaops.webapp.storage;

import com.javaops.webapp.storage.serialization.ObjectSerializationStorage;

class ObjectPathStorageTest extends AbstractStorageTest {
    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new ObjectSerializationStorage()));
    }
}