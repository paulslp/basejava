package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.serialization.SerializationStrategyObjectStream;

import java.nio.file.Paths;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    private static PathStorage pathStorage = new PathStorage(Paths.get(STORAGE_DIR));

    static {
        pathStorage.setStrategy(new SerializationStrategyObjectStream());
    }

    public ObjectStreamPathStorageTest() {
        super(pathStorage);

    }
}