package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * List based storage for Resumes
 */
public class ListStorage extends AbstractStorage {
    protected List<Resume> storage = new ArrayList<Resume>();

    public void clear() {
        storage.clear();
    }

    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);

    }

    public int size() {
        return storage.size();
    }

    @Override
    protected void deleteElement(String uuid, Object searchKey) {
        storage.remove((int) searchKey);
    }

    @Override
    protected void insertElement(Resume r, Object searchKey) {
        storage.add(r);
    }

    @Override
    protected void updateElement(Resume r, Object searchKey) {
        storage.set((int) searchKey, r);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        int i = 0;
        for (Resume r : storage) {
            if (Objects.equals(r.getUuid(), uuid)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override
    protected Resume findElement(Object searchKey) {
        return storage.get((int) searchKey);
    }

    @Override
    protected boolean checkExistingElement(Object searchKey) {
        return (int) searchKey >= 0;
    }
}