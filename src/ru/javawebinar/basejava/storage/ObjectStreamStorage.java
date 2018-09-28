package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ObjectStreamStorage extends AbstractFileStorage {
    protected ObjectStreamStorage(File directory) {
        super(directory);
    }

    @Override
    protected void doWrite(Resume r, OutputStream os) throws IOException {
        SerializationStrategy strategy = new SerializationStrategyObjectStream();
        strategy.doWrite(r, os);
    }

    @Override
    protected Resume doRead(InputStream is) throws IOException {
        SerializationStrategy strategy = new SerializationStrategyObjectStream();
        return strategy.doRead(is);
    }
}