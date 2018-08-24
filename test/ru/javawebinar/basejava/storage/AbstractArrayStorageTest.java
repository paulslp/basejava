package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest {

    private static final int SIZE_FOR_TEST = 3;
    private static final int STORAGE_LIMIT = 10000;
    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final String UUID_new = "uuid4";
    private static final Resume RESUME_new = new Resume(UUID_new);
    private static final Resume RESUME_0 = new Resume("uuid0");
    private Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage = new ArrayStorage();
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }


    @Test
    public void size() {
        Assert.assertEquals(SIZE_FOR_TEST, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        storage.update(RESUME_2);
        Assert.assertEquals(SIZE_FOR_TEST, storage.size());
        Assert.assertTrue(RESUME_2 == storage.get(UUID_2));
    }

    @Test
    public void getAll() {
        Resume[] arrayResume = storage.getAll();
        Assert.assertEquals(arrayResume[0], RESUME_1);
        Assert.assertEquals(arrayResume[1], RESUME_2);
        Assert.assertEquals(arrayResume[2], RESUME_3);
    }

    @Test(expected = ExistStorageException.class)
    public void save() {
        try {
            storage.save(RESUME_new);
            Assert.assertEquals(SIZE_FOR_TEST + 1, storage.size());
            Assert.assertEquals(storage.get(UUID_new), RESUME_new);
        } catch (Exception e) {
            Assert.fail("Error saving element..." + e.getMessage());
        }
        storage.save(RESUME_new);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        try {
            storage.delete(UUID_2);
            Assert.assertEquals(SIZE_FOR_TEST - 1, storage.size());
        } catch (Exception e) {
            Assert.fail("Error deleting element..." + e.getMessage());
        }
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void get() {
        try {
            Assert.assertEquals(RESUME_1, storage.get(UUID_1));
            Assert.assertEquals(RESUME_2, storage.get(UUID_2));
            Assert.assertEquals(RESUME_3, storage.get(UUID_3));
        } catch (Exception e) {
            Assert.fail("Error getting element..." + e.getMessage());
        }
        storage.get(UUID_new);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(UUID_new);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(RESUME_1);
    }

    @Test(expected = StorageException.class)
    public void checkOverflow() throws Exception {
        try {
            for (int i = 3; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume("UUID" + String.valueOf(i + 1)));
            }
        } catch (Exception e) {
            Assert.fail("Error filling array..." + e.getMessage());
        }
        storage.save(RESUME_0);
    }
}