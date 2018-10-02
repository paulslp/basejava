package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.serialization.SerializationStrategyObjectStream;

import java.nio.file.Paths;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    public ObjectStreamPathStorageTest() {
        super(new PathStorage(Paths.get(STORAGE_DIR), new SerializationStrategyObjectStream()));

    }
}