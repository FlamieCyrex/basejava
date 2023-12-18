package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.javaops.webapp.storage.ResumeTestData.fillResume;

class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("C:\\Users\\flami\\OneDrive\\Рабочий стол\\Code\\storage");

    protected final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String FULLNAME_1 = "name1";
    private static final Resume RESUME_1 = fillResume(UUID_1, FULLNAME_1);
    private static final String UUID_2 = "uuid2";
    private static final String FULLNAME_2 = "name2";
    private static final Resume RESUME_2 = fillResume(UUID_2, FULLNAME_2);
    private static final String UUID_3 = "uuid3";
    private static final String FULLNAME_3 = "name3";
    private static final Resume RESUME_3 = fillResume(UUID_3, FULLNAME_3);
    private static final String UUID_4 = "uuid4";
    private static final String FULLNAME_4 = "name4";
    protected static final Resume RESUME_4 = fillResume(UUID_4, FULLNAME_4);
    private static final String UUID_NOT_EXIST = "notexist";
    private static final String FULLNAME_EMPTY = "empty";

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test
    void saveIfExist() {
        Assertions.assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1));
        assertSize(3);
    }

    @Test
    void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test
    void getIfNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(UUID_NOT_EXIST));
    }


    @Test
    void delete() {
        storage.delete(UUID_1);
        assertSize(2);
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(UUID_1));
    }

    @Test
    void deleteIfNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_NOT_EXIST));
        assertSize(3);
    }


    @Test
    void update() {
        Resume r = new Resume(UUID_1, "new_name");
        storage.update(r);
        Assertions.assertEquals(storage.get(UUID_1), r);
    }

    @Test
    void updateIfNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            Resume r = new Resume(UUID_NOT_EXIST, FULLNAME_EMPTY);
            storage.update(r);
        });
    }

    @Test
    void size() {
        assertSize(3);
    }


    @Test
    void getAllSorted() {
        List<Resume> expected = new ArrayList<>();
        expected.add(RESUME_1);
        expected.add(RESUME_2);
        expected.add(RESUME_3);
        Assertions.assertArrayEquals(expected.toArray(), storage.getAllSorted().toArray());
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
        List<Resume> allResume = storage.getAllSorted();
        Assertions.assertArrayEquals(allResume.toArray(), new Resume[0]);
    }

    void assertGet(Resume resume) {
        Assertions.assertEquals(storage.get(resume.getUuid()), resume);
    }

    void assertSize(int size) {
        Assertions.assertEquals(size, storage.size());
    }
}