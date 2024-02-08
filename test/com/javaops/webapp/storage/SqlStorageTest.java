package com.javaops.webapp.storage;

import com.javaops.webapp.Config;


class SqlStorageTest extends AbstractStorageTest {
    protected static final String STORAGE_URL = Config.get().getUrl();
    protected static final String STORAGE_USERNAME = Config.get().getUserName();
    protected static final String STORAGE_PASSWORD = Config.get().getPassword();

    public SqlStorageTest() {
        super(new SqlStorage(STORAGE_URL, STORAGE_USERNAME, STORAGE_PASSWORD));
    }
}