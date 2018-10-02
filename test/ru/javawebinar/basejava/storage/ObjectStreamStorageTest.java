package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.serialization.SerializationStrategyObjectStream;

import java.io.File;

public class ObjectStreamStorageTest extends AbstractStorageTest {


    public ObjectStreamStorageTest() {
        super(new FileStorage(new File(STORAGE_DIR), new SerializationStrategyObjectStream()));
    }
}