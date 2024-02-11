package com.javaops.webapp;

import com.javaops.webapp.storage.SqlStorage;
import com.javaops.webapp.storage.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPS = new File("config\\resume.properties");
    private static final Config INSTANCE = new Config();
    private final Properties properties = new Properties();
    private final File storageDir;
    private Storage storage;

    public static Config get() {
        return INSTANCE;
    }

    public Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            properties.load(is);
            storageDir = new File(properties.getProperty("storage.dir"));
            storage = new SqlStorage(properties.getProperty("db.url"), properties.getProperty("db.user"), properties.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file" + PROPS.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public Storage getStorage() {
        return storage;
    }
}
