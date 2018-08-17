package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    protected void saveResume(Resume r, int index) {
        int low = -index - 1;
        System.arraycopy(storage, low, storage, low + 1, size - low);
        storage[low] = r;
    }

    protected void deleteResume(String uuid, int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }

    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}