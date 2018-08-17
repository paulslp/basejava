package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    protected void saveResume(Resume r, int index) {
        storage[size] = r;
    }

    protected void deleteResume(String uuid, int index) {
        storage[index] = storage[size - 1];
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}