package com.javaops.webapp.storage;


import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapResumeStorageTest.class,
        MapUuidStorageTest.class,
        ObjectFileStorageTest.class,
        ObjectPathStorageTest.class,
        XMLPathStorageTest.class,
        JsonPathStorageTest.class,
        DataPathStorageTest.class,
        SqlStorageTest.class})


public class AllStorageTest {

}