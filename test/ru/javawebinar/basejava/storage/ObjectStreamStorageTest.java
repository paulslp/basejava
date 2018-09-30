package ru.javawebinar.basejava.storage;

import java.io.File;

public class ObjectStreamStorageTest extends AbstractStorageTest {

    public ObjectStreamStorageTest() {
        super(new FileStorage(new File(STORAGE_DIR)));
    }
}