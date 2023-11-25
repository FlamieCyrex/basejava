package com.javaops.webapp.storage;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    void saveIfOverflowStorage() {
        while (storage.size() < AbstractArrayStorage.STORAGE_LIMIT) {
            storage.save(new Resume("name5"));
        }
        assertSize(AbstractArrayStorage.STORAGE_LIMIT);
        Assertions.assertThrows(StorageException.class, () -> storage.save(RESUME_4));

    }

}
