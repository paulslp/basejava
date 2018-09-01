package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
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

    @Override
    protected void deleteElement(String uuid, Object searchKey) {
        deleteResume(uuid, searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void insertElement(Resume r, Object searchKey) {
        checkOverflow(r);
        insertResume(r, searchKey);
        size++;
    }


    @Override
    protected Resume findElement(Object searchKey) {
        return storage[(int) searchKey];
    }

    protected boolean checkExistingElement(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    protected void updateElement(Resume r, Object searchKey) {
        storage[(int) searchKey] = r;
    }

    protected void checkOverflow(Resume r) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
    }

    protected abstract void deleteResume(String uuid, Object searchKey);

    protected abstract void insertResume(Resume r, Object searchKey);

}