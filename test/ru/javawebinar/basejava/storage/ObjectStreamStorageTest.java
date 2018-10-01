package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.serialization.SerializationStrategyObjectStream;

import java.io.File;

public class ObjectStreamStorageTest extends AbstractStorageTest {

    private static FileStorage fileStorage = new FileStorage(new File(STORAGE_DIR));

    static {
        fileStorage.setStrategy(new SerializationStrategyObjectStream());
    }

    public ObjectStreamStorageTest() {
        super(fileStorage);
    }
}