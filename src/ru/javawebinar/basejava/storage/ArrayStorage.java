package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {


    @Override
    protected void deleteElement(String uuid, Object searchKey) {
        storage[getIndex(searchKey)] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void insertElement(Resume r, Object searchKey) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            storage[size] = r;
            size++;
        }
    }

    @Override
    protected void updateElement(Resume r, Object searchKey) {
        storage[getIndex(searchKey)] = r;
    }

    @Override
    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }


}