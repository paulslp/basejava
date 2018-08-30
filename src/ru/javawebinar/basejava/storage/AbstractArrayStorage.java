package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);

    }

    protected int getIndex(Object searchKey) {
        return (int) searchKey;
    }

    @Override
    protected Resume findElement(Object searchKey) {
        return storage[getIndex(searchKey)];
    }

    protected boolean checkExistingElement(Object searchKey) {
        return getIndex(searchKey) >= 0;
    }

}