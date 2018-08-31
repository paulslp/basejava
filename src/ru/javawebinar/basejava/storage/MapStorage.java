package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

/**
 * Map based storage for Resumes
 */
public class MapStorage extends AbstractStorage {


    protected Map<String, Resume> storage = new HashMap<>();


    public int size() {
        return storage.size();
    }

    public void clear() {
        storage.clear();
    }

    public Resume[] getAll() {
        return storage.values().toArray(new Resume[storage.size()]);
    }


    @Override
    protected void deleteElement(String uuid, Object searchKey) {
        storage.remove(uuid);
    }

    @Override
    protected void insertElement(Resume r, Object searchKey) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void updateElement(Resume r, Object searchKey) {
        insertElement(r, searchKey);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected Resume findElement(Object uuid) {
        return storage.get((String) uuid);
    }

    @Override
    protected boolean checkExistingElement(Object searchKey) {
        return storage.containsKey(searchKey);
    }
}