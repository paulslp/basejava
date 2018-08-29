package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;

/**
 * List based storage for Resumes
 */
public class ListStorage extends AbstractStorage {
    protected ArrayList<Resume> storage = new ArrayList<Resume>();

    public void clear() {
        storage.clear();
    }

    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);

    }

    public int size() {
        return storage.size();
    }


    protected Resume getElement(String uuid, int index) {
        return storage.get(index);
    }

    @Override
    protected void deleteElement(String uuid, int index) {
        storage.remove(index);
    }

    @Override
    protected void insertElement(Resume r, int index) {
        storage.add(r);
    }

    @Override
    protected void updateElement(Resume r, int index) {
        storage.set(index, r);
    }

    protected int getIndex(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

}