package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {


    protected void saveResume(Resume r, int index) {
        int low = -index - 1;
        for (int i = size - 1; i >= low; i--) {
            storage[i + 1] = storage[i];
        }
        storage[low] = r;
    }

    protected void deleteResume(String uuid, int index) {
        for (int i = index; i < size; i++) {
            storage[i] = storage[i + 1];
        }
    }



    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}