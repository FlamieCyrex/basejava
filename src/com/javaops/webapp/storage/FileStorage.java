package com.javaops.webapp.storage;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;
import com.javaops.webapp.storage.serialization.SerializationStorage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final SerializationStorage serializationStorage;

    public FileStorage(File directory, SerializationStorage serializationStorage) {
        Objects.requireNonNull(directory, "directory can`t be null");
        this.serializationStorage = serializationStorage;
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        }
        if (!directory.canRead() | !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable or writable");
        }
        this.directory = directory;
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doUpdate(Resume r, File file) {
        try {
            serializationStorage.doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File with error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("No such file", file.getName());
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn`t create file " + file.getAbsolutePath(), file.getName(), e);
        }
        doUpdate(r, file);
    }


    @Override
    protected Resume doGet(File file) {
        try {
            return serializationStorage.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read Error", file.getName(), e);
        }
    }

    @Override
    protected List<Resume> doGetAll() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory is empty", null);
        }
        ArrayList<Resume> list = new ArrayList<>(size());
        for (File file : files) {
            list.add(doGet(file));
        }
        return list;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory is empty", null);
        }
        for (File file : files) {
            doDelete(file);
        }
    }

    @Override
    public int size() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory is empty", null);
        }
        return files.length;

    }

}
