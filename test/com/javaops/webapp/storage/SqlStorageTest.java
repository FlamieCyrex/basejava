package com.javaops.webapp.storage;

import com.javaops.webapp.Config;


class SqlStorageTest extends AbstractStorageTest {
    private static final Storage storage = Config.get().getStorage();

    public SqlStorageTest() {
        super(storage);
    }
}