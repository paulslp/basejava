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


    protected Resume getElement(String uuid, int index) {
        return storage.get(uuid);
    }

    @Override
    protected void deleteElement(String uuid, int index) {
        storage.remove(uuid);
    }

    @Override
    protected void insertElement(Resume r, int index) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void updateElement(Resume r, int index) {
        insertElement(r, index);
    }

    protected int getIndex(String uuid) {
        int index = -1;
        if (storage.containsKey(uuid)) {
            return 0;
        } else {
            return -1;
        }
    }
}