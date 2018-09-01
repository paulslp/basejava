package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {


    @Override
    protected void deleteResume(String uuid, Object searchKey) {
        storage[(int) searchKey] = storage[size - 1];
        storage[size - 1] = null;
    }

    @Override
    protected void insertResume(Resume r, Object searchKey) {
        storage[size] = r;
    }



    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }


}