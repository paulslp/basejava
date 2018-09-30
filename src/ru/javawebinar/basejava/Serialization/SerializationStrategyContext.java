package ru.javawebinar.basejava.Serialization;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SerializationStrategyContext {
    private SerializationStrategy strategy;

    public void setStrategy(SerializationStrategy strategy) {
        this.strategy = strategy;
    }

    public void executeDoWrite(Resume r, OutputStream os) {
        try {
            strategy.doWrite(r, os);
        } catch (IOException e) {
            throw new StorageException("Error writing to objectstream ", r.getUuid(), e);
        }
    }

    public Resume executeDoRead(InputStream is) {
        try {
            return strategy.doRead(is);
        } catch (IOException e) {
            throw new StorageException("Error reading from objectstream ", "");
        }
    }

}
