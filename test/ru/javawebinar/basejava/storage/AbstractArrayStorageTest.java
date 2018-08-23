package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest {

    private Storage storage;

    private static final int STORAGE_LIMIT = 10000;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_new = "uuid4";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage = new ArrayStorage();
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }


    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume[] arrayResume = storage.getAll();
        storage.update(new Resume(UUID_2));
        Assert.assertEquals(3, storage.size());
        Resume[] arrayResumeNew = storage.getAll();
        Assert.assertArrayEquals(arrayResumeNew, new Resume[]{new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)});
    }

    @Test
    public void getAll() {
        Resume[] arrayResume = storage.getAll();
        Assert.assertArrayEquals(arrayResume, new Resume[]{new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)});
    }

    @Test
    public void save() {
        Resume[] arrayResume = storage.getAll();
        storage.save(new Resume(UUID_new));
        Assert.assertEquals(4, storage.size());
        Resume[] arrayResumeNew = storage.getAll();
        Assert.assertArrayEquals(arrayResumeNew, new Resume[]{new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3), new Resume(UUID_new)});
    }

    @Test
    public void delete() {
        Resume[] arrayResume = storage.getAll();
        storage.delete(UUID_2);
        Assert.assertEquals(2, storage.size());
        Resume[] arrayResumeNew = storage.getAll();
        Assert.assertArrayEquals(arrayResumeNew, new Resume[]{new Resume(UUID_1), new Resume(UUID_3)});
    }

    @Test
    public void get() {
        Resume r1 = new Resume(UUID_2);
        Assert.assertEquals(r1, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(UUID_new);
    }

    @Test(expected = ExistStorageException.class)
    public void getExist() throws Exception {
        Resume r = storage.get(UUID_1);
        throw new ExistStorageException(r.getUuid());
    }

    @Test(expected = StorageException.class)
    public void checkOverflow() throws Exception {
        int i = 0;
        try {
            for (i = 3; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume("UUID" + String.valueOf(i + 1)));
            }
        } catch (Exception e) {
            Assert.fail("Error filling array..." + e.getMessage());
        }
        storage.save(new Resume("UUID" + String.valueOf(i + 1)));
    }
}