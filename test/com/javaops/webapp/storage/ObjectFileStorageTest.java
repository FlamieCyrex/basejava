package com.javaops.webapp.storage;

import com.javaops.webapp.storage.serialization.ObjectSerializationStorage;

public class ObjectFileStorageTest extends AbstractStorageTest {
    public ObjectFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectSerializationStorage()));
    }
}
