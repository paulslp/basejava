package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;


public abstract class AbstractStorage implements Storage {


    public void update(Resume r) {
        Object key = getSearchKey(r.getUuid());
        if (!checkExistingElement(key)) {
            throw new NotExistStorageException(r.getUuid());
        } else {
            updateElement(r, key);
        }
    }


    public void save(Resume r) {
        Object key = getSearchKey(r.getUuid());
        if (checkExistingElement(key)) {
            throw new ExistStorageException(r.getUuid());
        } else {
            insertElement(r, key);
        }
    }

    public void delete(String uuid) {
        Object key = getSearchKey(uuid);
        if (!checkExistingElement(key)) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteElement(uuid, key);
        }
    }

    public Resume get(String uuid) {
        Object key = getSearchKey(uuid);
        if (!checkExistingElement(key)) {
            throw new NotExistStorageException(uuid);
        }
        return findElement(key);
    }

    protected abstract void deleteElement(String uuid, Object key);

    protected abstract void insertElement(Resume r, Object key);

    protected abstract void updateElement(Resume r, Object key);

    protected abstract Object getSearchKey(String uuid);

    protected abstract Resume findElement(Object searchKey);

    protected abstract boolean checkExistingElement(Object searchKey);
}