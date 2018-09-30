package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.Serialization.SerializationStrategyContext;
import ru.javawebinar.basejava.Serialization.SerializationStrategyObjectStream;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.Math.toIntExact;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;
    private String uuid;

    protected PathStorage(Path directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(directory.getFileName().toString() + " is not directory or is not writable");
        }
        this.directory = directory;
    }

    protected void doWrite(Resume r, OutputStream os) throws IOException {
        SerializationStrategyContext strategyContext = new SerializationStrategyContext();
        strategyContext.setStrategy(new SerializationStrategyObjectStream());
        strategyContext.executeDoWrite(r, os);
    }

    protected Resume doRead(InputStream is) throws IOException {
        SerializationStrategyContext strategyContext = new SerializationStrategyContext();
        strategyContext.setStrategy(new SerializationStrategyObjectStream());
        return strategyContext.executeDoRead(is);
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        try {
            return toIntExact(Files.list(directory).count());
        } catch (IOException e) {
            throw new StorageException("Directory size error", null);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        this.uuid = uuid;
        return Paths.get(directory + "\\" + uuid);
    }

    @Override
    protected void doUpdate(Resume r, Path path) {
        try {
            doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path write error", r.getUuid(), e);
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void doSave(Resume r, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create Path " + path.toAbsolutePath(), path.getFileName().toString(), e);
        }
        doUpdate(r, path);
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {

        try {
            if (!Files.deleteIfExists(path)) {
                throw new StorageException("Path delete error", path.getFileName().toString());
            }
        } catch (IOException e) {
            throw new StorageException("Path delete error IO", path.getFileName().toString());
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> list = new ArrayList<>();
        try {
            Files.list(directory).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Directory io error", null);
        }
        return list;
    }
}