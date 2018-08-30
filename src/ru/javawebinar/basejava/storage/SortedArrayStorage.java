package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void deleteElement(String uuid, Object searchKey) {
        int index = getIndex(searchKey);
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(storage, index + 1, storage, index, numMoved);
            size--;
        }
    }

    @Override
    protected void insertElement(Resume r, Object searchKey) {
//      http://codereview.stackexchange.com/questions/36221/binary-search-for-inserting-in-array#answer-36239
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            int index = getIndex(searchKey);
            int insertIdx = -index - 1;
            System.arraycopy(storage, insertIdx, storage, insertIdx + 1, size - insertIdx);
            storage[insertIdx] = r;
            size++;
        }
    }

    @Override
    protected void updateElement(Resume r, Object searchKey) {
        storage[(Integer) searchKey] = r;
    }

    @Override
    protected Object getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }


}